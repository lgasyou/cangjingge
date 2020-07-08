package cn.edu.bit.cangjingge.fiction.service;

import cn.edu.bit.cangjingge.common.entity.Fiction;
import cn.edu.bit.cangjingge.common.entity.FictionChapter;
import cn.edu.bit.cangjingge.common.exception.BusinessException;
import cn.edu.bit.cangjingge.common.response.Response;
import cn.edu.bit.cangjingge.common.response.ResponseStatusEnum;
import cn.edu.bit.cangjingge.common.response.ResponseUtil;
import cn.edu.bit.cangjingge.fiction.dao.FictionDao;
import cn.edu.bit.cangjingge.fiction.image.ImageManager;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class FictionServiceImpl {
    @Resource
    FictionDao fictionDao;

    @Resource
    ImageManager imageManager;

    public Response<List<Fiction>> getFictionByTitle(String title) {
        return ResponseUtil.success(fictionDao.getFictionByTitle(title));
    }

    public Response<Fiction> getFictionById(Long id) {
        Fiction fiction = fictionDao.getFictionById(id);
        if (fiction == null)
            throw new BusinessException(ResponseStatusEnum.FICTION_NOT_FOUND);
        return ResponseUtil.success(fiction);
    }

    public String setCover(
            final Long id,
            final MultipartFile avatar
    ) throws IOException, ClientException {
        if (fictionDao.getFictionById(id) == null) {
            throw new BusinessException(ResponseStatusEnum.FICTION_NOT_FOUND);
        }
        final String coverUrl = imageManager.upload(avatar, id);
        fictionDao.updateCover(id, coverUrl);
        return coverUrl;
    }

    public Response<Fiction> createFiction2(Long authorId, String title, String description) {
        Date date = new Date();
        Fiction fiction = new Fiction();
        fiction.setAuthorId(authorId);
        fiction.setTitle(title);
        fiction.setDescription(description);
        fiction.setCreateTimestamp(date);
        fiction.setModifiedTimestamp(date);
        Long res = fictionDao.saveFiction2(fiction);
        if (res != -1)
            return ResponseUtil.success(fictionDao.getFictionById(fiction.getId()));
        throw new BusinessException(ResponseStatusEnum.FICTION_CREATION_FAILURE);
    }

    public Response<FictionChapter> createFictionChapter2(Long fictionId, String title, String content) {
        Fiction fiction = fictionDao.getFictionById(fictionId);
        if (fiction == null) throw new BusinessException(ResponseStatusEnum.FICTION_NOT_FOUND);
        List<FictionChapter> fictionChapters = fictionDao.getFictionChapterByFictionId(fictionId);
        long chapterId = fictionChapters.size() + 1;
        FictionChapter fictionChapter = new FictionChapter();
        fictionChapter.setFictionId(fictionId);
        fictionChapter.setChapterId(chapterId);
        fictionChapter.setTitle(title);
        fictionChapter.setContent(content);
        if (fictionDao.saveFictionChapter2(fictionChapter) != -1)
            return ResponseUtil.success(fictionDao.getFictionChapterById(fictionChapter.getId()));
        throw new BusinessException(ResponseStatusEnum.FICTION_CHAPTER_CREATION_FAILURE);
    }

    public Response<FictionChapter> getFictionChapterByFictionIdAndChapterId(Long fictionId, Long chapterId) {
        FictionChapter fictionChapter = fictionDao.getFictionChapterByChapterIdAndFictionId(chapterId, fictionId);
        if (fictionChapter != null)
            return ResponseUtil.success(fictionChapter);
        throw new BusinessException(ResponseStatusEnum.FICTION_CHAPTER_NOT_FOUND);
    }

    public Response<FictionChapter> updateFictionChapter(Long fictionId, Long chapterId, String title, String content) {
        if (fictionDao.updateFictionChapter(fictionId, chapterId, title, content)){
            fictionDao.updateFictionById(new Date(), fictionId);
            return ResponseUtil.success(getFictionChapterByFictionIdAndChapterId(fictionId, chapterId).getData());
        } else {
            throw new BusinessException(ResponseStatusEnum.FICTION_NOT_FOUND);
        }
    }

    public Response<List<FictionChapter>> getChapterInfoByFictionId(Long fictionId) {
        return ResponseUtil.success(fictionDao.getFictionChapterByFictionId(fictionId));
    }
}
