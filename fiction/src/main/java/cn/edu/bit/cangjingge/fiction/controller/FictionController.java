package cn.edu.bit.cangjingge.fiction.controller;

import cn.edu.bit.cangjingge.common.entity.Fiction;
import cn.edu.bit.cangjingge.common.entity.FictionChapter;
import cn.edu.bit.cangjingge.common.exception.BusinessException;
import cn.edu.bit.cangjingge.common.response.Response;
import cn.edu.bit.cangjingge.common.response.ResponseUtil;
import cn.edu.bit.cangjingge.fiction.service.FictionServiceImpl;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class FictionController {

    @Resource
    private FictionServiceImpl fictionService;

    /**
     * 使用关键字对小说进行搜索，匹配标题关键字
     * @return 搜索到的小说元数据（可能为空，也可能为多个）
     */
    @GetMapping("/")
    public Response<List<Fiction>> searchByTitle(
            final String title
    ) {
        throw new NotImplementedException();
    }

    /**
     * 使用ID对小说进行精确内容获取
     * @return 搜索到的小说元数据
     * @exception BusinessException 抛出CHAPTER_NOT_FOUND代表的异常
     */
    @GetMapping("/{id}")
    public Response<Fiction> getById(
            @PathVariable("id") final Long id
    ) {
        throw new NotImplementedException();
    }

    /**
     * 新建一个小说的元数据
     * @return 创建小说的元数据
     */
    @PostMapping("/")
    public Response<Fiction> createFiction(
            final Long authorId,
            final String title,
            final String description
    ) {
        throw new NotImplementedException();
    }

    @GetMapping("/hello-error")
    public Response<String> helloError() {
        throw new BusinessException(400, "?");
    }

    @GetMapping("/hello")
    @PreAuthorize("hasRole('ADMIN')")
    public Response<Integer> hello() {
        return ResponseUtil.success(fictionService.hello());
    }

}
