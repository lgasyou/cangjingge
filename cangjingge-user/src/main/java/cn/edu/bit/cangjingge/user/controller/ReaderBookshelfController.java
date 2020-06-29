package cn.edu.bit.cangjingge.user.controller;

import cn.edu.bit.cangjingge.common.entity.Bookshelf;
import cn.edu.bit.cangjingge.common.entity.FictionReview;
import cn.edu.bit.cangjingge.common.response.Response;
import cn.edu.bit.cangjingge.common.response.ResponseUtil;
import cn.edu.bit.cangjingge.common.security.RequiresAuthorization;
import cn.edu.bit.cangjingge.user.service.BookshelfServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ReaderBookshelfController {

    @Resource
    BookshelfServiceImpl bookshelfService;

    @ApiOperation("获得用户书架中的书籍（需要认证，用户权限及以上）")
    @GetMapping("/{userId}/bookshelf")
    @RequiresAuthorization
    public Response<List<Bookshelf>> getBookshelfById(
            @PathVariable("userId") final Long userId
    ) {
        return bookshelfService.getBookshelfByUserId(userId);
    }

//    @ApiOperation("使用ID获取书架")
//    @GetMapping("/{id}/bookshelf")
//    public Response<Bookshelf> getById(
//            @PathVariable("id") final Long id
//    ) {
//        return bookshelfService.getBookshelfById(id);
//    }

    /**
     * @return 创建的书架条目元数据
     */
    @ApiOperation("为用户添加一个书架小说（需要认证，用户权限及以上）")
    @PostMapping("/{userId}/bookshelf")
    @RequiresAuthorization
    public Response<Bookshelf> createBookshelfItem(
            @PathVariable("userId") final Long userId,
            final Long fictionId
    ) {
        return bookshelfService.createBookshelf(userId,fictionId);
    }

    @ApiOperation("删除一个书架条目（需要认证，用户权限及以上）")
    @DeleteMapping("/{userId}/bookshelf/{bookshelfId}")
    @RequiresAuthorization
    public Response<String> deleteBookshelfItem(
            @PathVariable("userId") final Long userId,
            @PathVariable("bookshelfId") final Long bookshelfId
    ) {
        return bookshelfService.deleteBookshelf(userId,bookshelfId);
    }

}
