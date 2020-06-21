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

    public Response getFictionById(Long id) {
        Fiction fiction = fictionDao.getFictionById(id);
        if (fiction == null)
            throw new BusinessException(ResponseStatusEnum.FICTION_NOT_FOUND);
        return ResponseUtil.success(fiction);
    }

    public Response createFiction(Long authorId, String title, String description) {
        if (fictionDao.saveFiction(authorId, title, description, new Date(), new Date()))
            return ResponseUtil.success();
        throw new BusinessException(ResponseStatusEnum.FICTION_CREATION_FAILURE);
    }

    public Response createFictionChapter(Long fictionId, String title, String content) {
        Fiction fiction = fictionDao.getFictionById(fictionId);
        if (fiction == null) throw new BusinessException(ResponseStatusEnum.FICTION_NOT_FOUND);
        List<FictionChapter> fictionChapters = fictionDao.getFictionChapterByFictionId(fictionId);
        long chapterId = fictionChapters.size() + 1;
        if (fictionDao.saveFictionChapter(chapterId, fictionId, title, content))
            return ResponseUtil.success();
        throw new BusinessException(ResponseStatusEnum.FICTION_CHAPTER_CREATION_FAILURE);
    }

    public Response getFictionChapterByFictionIdAndChapterId(Long fictionId, Long chapterId) {
        FictionChapter fictionChapter = fictionDao.getFictionChapterByChapterIdAndFictionId(chapterId, fictionId);
        if (fictionChapter != null)
            return ResponseUtil.success(fictionChapter);
        throw new BusinessException(ResponseStatusEnum.FICTION_CHAPTER_NOT_FOUND);
    }

    public Response updateFictionChapter(Long fictionId, Long chapterId, String title, String content) {
        if (fictionDao.updateFictionChapter(fictionId, chapterId, title, content)){
            fictionDao.updateFictionById(new Date(), fictionId);
            return ResponseUtil.success();
        } else {
            throw new BusinessException(ResponseStatusEnum.FICTION_NOT_FOUND);
        }
    }

    public Response<List<FictionChapter>> getChapterInfoByFictionId(Long fictionId) {
        return ResponseUtil.success(fictionDao.getFictionChapterByFictionId(fictionId));
    }
}
