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

    @Insert(value = "insert into fiction(authorId,title,description,createTimestamp,modifiedTimestamp) " +
            "value(#{fiction.title},#{fiction.description},#{fiction.createTimestamp},#{fiction.modifiedTimestamp})")
    @Options(useGeneratedKeys=true, keyColumn="id")
    boolean putFiction(Fiction fiction);

    @Select(value = "select * from chapter where id=#{chapterId} and fictionId=#{fictionId}")
    FictionChapter getFictionChapterByChapterIdAndFictionId(Long chapterId, Long fictionId);

    @Update(value = "update fiction set modifiedTimestamp=#{modifiedTimestamp} where id=#{id}")
    boolean updateFictionById(Date modifiedTimestamp, Long id);

    @Select(value = "select chapterId, title from chapter where fictionId=#{fictionId}")
    List<FictionChapter> getFictionChapterByFictionId(Long fictionId);

    @Insert(value = "insert into chapter(chapterId, fictionId, title, content) " +
            "value(#{chapter.chapterId},#{chapter.chapterId},#{chapter.chapterId},#{chapter.chapterId},)")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    boolean putFictionChapter(FictionChapter chapter);

    @Update(value = "update chapter set title=#{chapter.title}, content=#{chapter.content} " +
            "where fictionId=#{chapter.fictionId} and chapterId=#{chapter.chapterId}")
    boolean updateFictionChapter(FictionChapter chapter);
}
