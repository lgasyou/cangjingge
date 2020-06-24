package cn.edu.bit.cangjingge.fictionreview.service;

import cn.edu.bit.cangjingge.common.entity.FictionReview;
import cn.edu.bit.cangjingge.common.exception.BusinessException;
import cn.edu.bit.cangjingge.common.response.Response;
import cn.edu.bit.cangjingge.common.response.ResponseStatusEnum;
import cn.edu.bit.cangjingge.common.response.ResponseUtil;
import cn.edu.bit.cangjingge.fictionreview.FictionReviewDao.FictionReviewDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class FictionReviewServiceImpl {
    @Resource
    FictionReviewDao fictionReviewDao;

    public Response<FictionReview> getFictionReviewById(Long id) {
        FictionReview fictionReview = fictionReviewDao.getFictionReviewById(id);
        if (fictionReview == null)
            throw new BusinessException(ResponseStatusEnum.FICTION_REVIEW_NOT_FOUND);
        return ResponseUtil.success(fictionReview);
    }

    public Response<List<FictionReview>> getFictionReviewByFictionId(Long fictionId) {
        List<FictionReview> fictionReviews = fictionReviewDao.getFictionReviewByFictionId(fictionId);
        return ResponseUtil.success(fictionReviews);
    }

    //public Response createFictionReview(Long fictionId, Long userId, Integer rate, String content){
    //    Date date = new Date();
    //    if (fictionReviewDao.putFictionReview(fictionId, userId, rate, content, date) != -1)
    //        return ResponseUtil.success();
    //    throw new BusinessException(ResponseStatusEnum.FICTION_REVIEW_CREATION_FAILURE);
    //}

    public Response<FictionReview> createFictionReview2(Long fictionId, Long userId, Integer rate, String content){
        FictionReview fictionReview = new FictionReview();
        fictionReview.setFictionId(fictionId);
        fictionReview.setUserId(userId);
        fictionReview.setRate(rate);
        fictionReview.setContent(content);
        fictionReview.setCreateTime(new Date());
        if (fictionReviewDao.putFictionReview2(fictionReview) != -1)
            return ResponseUtil.success(fictionReviewDao.getFictionReviewById(fictionReview.getId()));
        throw new BusinessException(ResponseStatusEnum.FICTION_REVIEW_CREATION_FAILURE);
    }

    public Response<FictionReview> deleteFictionReviewById(Long id) {
        FictionReview fictionReview = fictionReviewDao.getFictionReviewById(id);
        if (fictionReview != null) {
            fictionReviewDao.deleteFictionReviewById(id);
            return ResponseUtil.success(fictionReview);
        }
        throw new BusinessException(ResponseStatusEnum.FICTION_REVIEW_NOT_FOUND);
    }

}
