package cn.edu.bit.cangjingge.user.controller;

import cn.edu.bit.cangjingge.common.entity.Bookshelf;
import cn.edu.bit.cangjingge.common.response.Response;
import cn.edu.bit.cangjingge.common.response.ResponseUtil;
import cn.edu.bit.cangjingge.common.security.RequiresAuthorization;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReaderBookshelfController {

    @ApiOperation("获得用户书架中的书籍（需要认证）")
    @GetMapping("/{id}/bookshelf")
    @RequiresAuthorization
    public Response<List<Bookshelf>> getBookshelfById(
            @PathVariable("id") final Long id
    ) {
        throw new NotImplementedException();
    }

    /**
     * @return 创建的书架条目元数据
     */
    @ApiOperation("为用户添加一个书架小说（需要认证）")
    @PostMapping("/{id}/bookshelf")
    @RequiresAuthorization
    public Response<Bookshelf> createBookshelfItem(
            @PathVariable("id") final Long id,
            final Long fictionId
    ) {
        throw new NotImplementedException();
    }

    @ApiOperation("删除一个书架条目（需要认证）")
    @DeleteMapping("/{userId}/bookshelf/{bookshelfId}")
    @RequiresAuthorization
    public Response<String> deleteBookshelfItem(
            @PathVariable("userId") final Long userId,
            @PathVariable("bookshelfId") final Long bookshelfId
    ) {
        return ResponseUtil.success();
    }

}
