package cn.edu.bit.cangjingge.fiction.controller;

import cn.edu.bit.cangjingge.common.entity.Fiction;
import cn.edu.bit.cangjingge.common.exception.BusinessException;
import cn.edu.bit.cangjingge.common.response.Response;
import cn.edu.bit.cangjingge.fiction.service.FictionServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class FictionController {

    @Resource
    private FictionServiceImpl fictionService;

    /**
     * @return 搜索到的小说元数据（可能为空，也可能为多个）
     */
    @ApiOperation("使用关键字对小说进行搜索，匹配标题关键字")
    @GetMapping("/")
    public Response<List<Fiction>> searchByTitle(
            final String title
    ) {
        return fictionService.getFictionByTitle(title);
    }

    /**
     * @return 搜索到的小说元数据
     * @exception BusinessException 抛出CHAPTER_NOT_FOUND代表的异常
     */
    @ApiOperation("使用ID对小说进行精确内容获取")
    @GetMapping("/{id}")
    public Response getById(
            @PathVariable("id") final Long id
    ) {
        return fictionService.getFictionById(id);
    }

    /**
     * @return 创建小说的元数据
     */
    @ApiOperation("新建一个小说的元数据")
    @PostMapping("/")
    public Response createFiction(
            final Long authorId,
            final String title,
            final String description
    ) {
        return fictionService.createFiction2(authorId, title, description);
    }


}
