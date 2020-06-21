package cn.edu.bit.cangjingge.fictionreview.FictionReviewDao;

import cn.edu.bit.cangjingge.common.entity.FictionReview;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface FictionReviewDao {

    @Select(value = "select * from review where id=#{id}")
    FictionReview getFictionReviewById(Long id);

    @Select(value = "select * from review where fictionId=#{fictionId}")
    List<FictionReview> getFictionReviewByFictionId(Long fictionId);

    @Insert(value = "insert into review(fictionId, userId, rate, content) " +
            "values(#{review.getFictionId()},#{review.getUserId()},#{review.getRateId()},#{review.getContentId()})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    boolean putFictionReview(FictionReview review);

    @Delete(value = "delete from review where id=#{id}")
    boolean deleteFictionReviewById(Long id);
}
