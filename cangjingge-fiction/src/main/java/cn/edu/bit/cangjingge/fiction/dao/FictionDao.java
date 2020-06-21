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
            "value(#{fiction.getTitle()},#{fiction.getDescription()},#{fiction.getCreateTimestamp()},#{fiction.getModifiedTimestamp()})")
    @Options(useGeneratedKeys=true, keyColumn="id")
    boolean putFiction(Fiction fiction);

    @Select(value = "select * from chapter where id=#{chapterId} and fictionId=#{fictionId}")
    FictionChapter getFictionChapterByChapterIdAndFictionId(Long chapterId, Long fictionId);

    @Update(value = "update fiction set modifiedTimestamp=#{modifiedTimestamp} where id=#{id}")
    void updateFictionById(Date modifiedTimestamp, Long id);

    @Select(value = "select chapterId, title from chapter where fictionId=#{fictionId}")
    List<FictionChapter> getFictionChapterByFictionId(Long fictionId);

    @Insert(value = "insert into chapter(chapterId, fictionId, title, content) " +
            "value(#{chapter.getChapterId()},#{chapter.getFictionId()},#{chapter.getTitle()},#{chapter.getContent()})")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    boolean putFictionChapter(FictionChapter chapter);

    @Update(value = "update chapter set title=#{chapter.title}, content=#{chapter.content} " +
            "where fictionId=#{chapter.getFictionId} and chapterId=#{chapter.getChapterId}")
    boolean updateFictionChapter(FictionChapter chapter);
}
