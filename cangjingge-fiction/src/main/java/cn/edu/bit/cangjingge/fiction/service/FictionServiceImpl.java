package cn.edu.bit.cangjingge.fiction.service;

import cn.edu.bit.cangjingge.common.entity.Fiction;
import cn.edu.bit.cangjingge.common.entity.FictionChapter;
import cn.edu.bit.cangjingge.common.exception.BusinessException;
import cn.edu.bit.cangjingge.common.response.Response;
import cn.edu.bit.cangjingge.common.response.ResponseStatusEnum;
import cn.edu.bit.cangjingge.common.response.ResponseUtil;
import cn.edu.bit.cangjingge.fiction.dao.FictionDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class FictionServiceImpl {
    @Resource
    FictionDao fictionDao;

    public Integer hello() {
        throw new BusinessException(ResponseStatusEnum.USER_NOT_FOUND);
    }

    public Response<List<Fiction>> getFictionByTitle(String title) {
        return ResponseUtil.success(fictionDao.getFictionByTitle(title));
    }

    public Response<Fiction> getFictionById(Long id) {
        Fiction fiction = fictionDao.getFictionById(id);
        if (fiction == null) throw new BusinessException(ResponseStatusEnum.FICTION_NOT_FOUND);
        else return ResponseUtil.success(fiction);
    }

    public Response<Fiction> createFiction(Long authorId, String title, String description) {
        Fiction fiction = new Fiction();
        fiction.setAuthorId(authorId);
        fiction.setTitle(title);
        fiction.setDescription(description);
        fiction.setCreateTimestamp(new Date());
        fiction.setModifiedTimestamp(new Date());
        if (fictionDao.putFiction(fiction)) return ResponseUtil.success(fiction);
       throw new BusinessException(ResponseStatusEnum.FICTION_CREATION_FAILURE);
    }

    public Response<FictionChapter> createFictionChapter(Long fictionId, String title, String content) {
        List<FictionChapter> fictionChapters = fictionDao.getFictionChapterByFictionId(fictionId);
        long chapterId = fictionChapters.size() + 1;
        FictionChapter fictionChapter = new FictionChapter();
        fictionChapter.setFictionId(fictionId);
        fictionChapter.setChapterId(chapterId);
        fictionChapter.setTitle(title);
        fictionChapter.setContent(content);
        if (fictionDao.putFictionChapter(fictionChapter)) return ResponseUtil.success(fictionChapter);
        throw new BusinessException(ResponseStatusEnum.FICTION_CHAPTER_CREATION_FAILURE);
    }

    public Response<FictionChapter> getFictionChapterByFictionIdAndChapterId(Long fictionId, Long chapterId) {
        return ResponseUtil.success(fictionDao.getFictionChapterByChapterIdAndFictionId(chapterId, fictionId));
    }

    public Response<FictionChapter> updateFictionChapter(Long fictionId, Long chapterId, String title, String content) {
        FictionChapter fictionChapter = new FictionChapter();
        fictionChapter.setTitle(title);
        fictionChapter.setContent(content);
        fictionChapter.setFictionId(fictionId);
        fictionChapter.setChapterId(chapterId);
        if (fictionDao.updateFictionChapter(fictionChapter)){
            fictionDao.updateFictionById(new Date(), fictionId);
            return ResponseUtil.success(fictionChapter);
        }
        throw new BusinessException(ResponseStatusEnum.FICTION_NOT_FOUND);
    }

    public Response<List<FictionChapter>> getChapterInfoByFictionId(Long fictionId) {
        return ResponseUtil.success(fictionDao.getFictionChapterByFictionId(fictionId));
    }
}
