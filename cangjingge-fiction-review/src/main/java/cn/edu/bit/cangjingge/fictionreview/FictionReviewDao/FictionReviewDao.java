package cn.edu.bit.cangjingge.fictionreview.FictionReviewDao;

import cn.edu.bit.cangjingge.common.entity.FictionReview;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FictionReviewDao {

    @Select(value = "select * from review where id=#{id}")
    FictionReview getFictionReviewById(Long id);

    @Select(value = "select * from review where fictionId=#{fictionId}")
    List<FictionReview>getFictionReviewByFictionId(Long fictionId);

    //@Insert(value = "insert into review(fictionId, userId, rate, content, time) " +
    //        "values(#{fictionId},#{userId},#{rate},#{content},#{time})")
    //@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    //Long putFictionReview(
    //        @Param("fictionId")Long fictionId,
    //        @Param("userId")Long userId,
    //        @Param("rate")Integer rate,
    //        @Param("content")String content,
    //        @Param("time") Date time);

    @Insert(value = "insert into review(fictionId, userId, rate, content, createTime) " +
            "values(#{fictionId},#{userId},#{rate},#{content},#{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long putFictionReview2(FictionReview fictionReview);

    @Delete(value = "delete from review where id=#{id}")
    void deleteFictionReviewById(Long id);
}
