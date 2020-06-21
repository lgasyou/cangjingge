package cn.edu.bit.cangjingge.fictionreview.FictionReviewDao;

import cn.edu.bit.cangjingge.common.entity.FictionReview;
import org.apache.ibatis.annotations.*;


import java.util.List;

@Mapper
public interface FictionReviewDao {

    @Select(value = "select * from review where id=#{id}")
    FictionReview getFictionReviewById(Long id);

    @Select(value = "select * from review where fictionId=#{fictionId}")
    List<FictionReview> getFictionReviewByFictionId(Long fictionId);

    @Insert(value = "insert into review(fictionId, userId, rate, content) " +
            "values(#{fictionId},#{userId},#{rate},#{content})")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    boolean putFictionReview(
            @Param("fictionId")Long fictionId,
            @Param("userId")Long userId,
            @Param("rate")Integer rate,
            @Param("content")String content);

    @Delete(value = "delete from review where id=#{id}")
    boolean deleteFictionReviewById(Long id);
}
