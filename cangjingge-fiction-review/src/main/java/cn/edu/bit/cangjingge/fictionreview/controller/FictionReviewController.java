package cn.edu.bit.cangjingge.fictionreview.controller;

import cn.edu.bit.cangjingge.common.entity.FictionReview;
import cn.edu.bit.cangjingge.common.response.Response;
import cn.edu.bit.cangjingge.common.response.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FictionReviewController {

    @ApiOperation("使用ID获取书评")
    @GetMapping("/id/{id}")
    public Response<FictionReview> getById(
            @PathVariable("id") final Long id
    ) {
        throw new NotImplementedException();
    }

    @ApiOperation("获取一本书的书评")
    @GetMapping("/fiction/{fictionId}")
    public Response<List<FictionReview>> getByFictionId(
            @PathVariable("fictionId") final Long fictionId
    ) {
        throw new NotImplementedException();
    }

    @ApiOperation("新建书评")
    @PostMapping("/fiction/{fictionId}")
    public Response<FictionReview> createReview(
            @PathVariable("fictionId") final Long fictionId,
            final Integer rate,
            final String content
    ) {
        throw new NotImplementedException();
    }

    @ApiOperation("删除一个书评")
    @DeleteMapping("/id/{id}")
    public Response<String> deleteBookshelfItem(
            @PathVariable("id") final Long id
    ) {
        return ResponseUtil.success();
    }

}
