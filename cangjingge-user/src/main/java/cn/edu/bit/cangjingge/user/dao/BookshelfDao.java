package cn.edu.bit.cangjingge.user.dao;

import cn.edu.bit.cangjingge.common.entity.Bookshelf;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookshelfDao {

    @Select(value = "select * from bookshelf where id=#{id}")
    Bookshelf getBookshelfById(Long id);

    @Select(value = "select * from bookshelf where userId=#{userId}")
    List<Bookshelf> getBookshelfByUserId(Long userId);

    @Insert(value= "insert into bookshelf(userId,fictionId) "+
    "values(#{userId},#{fictionId})" )
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    Long createBookshelf(Bookshelf bookshelf);

    @Delete(value = "delete from bookshelf where id=#{id}")
    Long deleteBookshelfById(Long id);

    @Select(value = "select * from bookshelf where userId=#{userId} and fictionId=#{fictionId}")
    Bookshelf getBookshelfByOtherId(@Param("userId") Long userId,@Param("fictionId") Long fictionId);
}
