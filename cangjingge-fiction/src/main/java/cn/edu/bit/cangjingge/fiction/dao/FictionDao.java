package cn.edu.bit.cangjingge.fiction.dao;

import cn.edu.bit.cangjingge.common.entity.Fiction;
import cn.edu.bit.cangjingge.common.entity.FictionChapter;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface FictionDao {
    @Select(value = "select * from fiction where title like '%${title}%'")
    List<Fiction> getFictionByTitle(String title);

    @Select(value = "select * from fiction where id=#{id}")
    Fiction getFictionById(Long id);

    //@Select(value = "select * from fiction where authorId=#{authorId} and createTimestamp=#{createTimestamp}")
    //Fiction getFictionByAuthorIdAndCreateTimestamp(
    //        @Param("authorId") Long id,
    //        @Param("createTimestamp") Date createTimestamp);

    //@Insert(value = "insert into fiction(authorId,title,description,createTimestamp,modifiedTimestamp) " +
    //        "values(#{authorId},#{title},#{description},#{createTimestamp},#{modifiedTimestamp})")
    //@Options(useGeneratedKeys=true, keyColumn="id")
    //boolean saveFiction(
    //        @Param("authorId")Long authorId,
    //        @Param("title")String title,
    //        @Param("description")String description,
    //        @Param("createTimestamp")Date createTimestamp,
    //        @Param("modifiedTimestamp")Date modifiedTimestamp);

    @Insert(value = "insert into fiction(authorId,title,description,createTimestamp,modifiedTimestamp) " +
            "values(#{authorId},#{title},#{description},#{createTimestamp},#{modifiedTimestamp})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Long saveFiction2(Fiction fiction);

    @Select(value = "select * from chapter where id=#{chapterId} and fictionId=#{fictionId}")
    FictionChapter getFictionChapterByChapterIdAndFictionId(
            @Param("chapterId")Long chapterId,
            @Param("fictionId")Long fictionId);

    @Update(value = "update fiction set modifiedTimestamp=#{modifiedTimestamp} where id=#{id}")
    void updateFictionById(
            @Param("modifiedTimestamp")Date modifiedTimestamp,
            @Param("id")Long id);

    @Select(value = "select chapterId, title from chapter where fictionId=#{fictionId}")
    List<FictionChapter> getFictionChapterByFictionId(Long fictionId);

    @Select(value = "select * from chapter where id=#{id}")
    FictionChapter getFictionChapterById(Long id);

    //@Insert(value = "insert into chapter(chapterId, fictionId, title, content) " +
    //        "values(#{chapterId},#{fictionId},#{title},#{content})")
    //@Options(useGeneratedKeys = true, keyProperty="id", keyColumn = "id")
    //Long saveFictionChapter(
    //        @Param("chapterId")Long chapterId,
    //        @Param("fictionId")Long fictionId,
    //        @Param("title")String title,
    //        @Param("content")String content);

    @Insert(value = "insert into chapter(chapterId, fictionId, title, content) " +
            "values(#{chapterId},#{fictionId},#{title},#{content})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long saveFictionChapter2(FictionChapter fictionChapter);

    @Update(value = "update chapter set title=#{title}, content=#{content} " +
            "where fictionId=#{fictionId} and chapterId=#{chapterId}")
    boolean updateFictionChapter(
            @Param("fictionId")Long fictionId,
            @Param("chapterId")Long chapterId,
            @Param("title")String title,
            @Param("content")String content);
}
