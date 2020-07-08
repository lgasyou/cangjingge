package cn.edu.bit.cangjingge.fiction.controller;

import cn.edu.bit.cangjingge.common.entity.Fiction;
import cn.edu.bit.cangjingge.common.exception.BusinessException;
import cn.edu.bit.cangjingge.common.response.Response;
import cn.edu.bit.cangjingge.common.response.ResponseUtil;
import cn.edu.bit.cangjingge.common.security.RequiresAuthorization;
import cn.edu.bit.cangjingge.fiction.service.FictionServiceImpl;
import com.aliyuncs.exceptions.ClientException;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
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
    public Response<Fiction> getById(
            @PathVariable("id") final Long id
    ) {
        return fictionService.getFictionById(id);
    }

    /**
     * @return 创建小说的元数据
     */
    @ApiOperation("新建一个小说的元数据（需要认证，用户权限及以上）")
    @PostMapping("/")
    @RequiresAuthorization
    public Response<Fiction> createFiction(
            final Long authorId,
            final String title,
            final String description
    ) {
        return fictionService.createFiction2(authorId, title, description);
    }

    @ApiOperation("设置小说封面（需要认证，用户权限及以上）")
    @PutMapping("/{id}/cover")
    @RequiresAuthorization
    public Response<String> setCover(
            @PathVariable Long id,
            @RequestParam MultipartFile avatar
    ) throws IOException, ClientException {
        String avatarName = fictionService.setCover(id, avatar);
        return ResponseUtil.success(avatarName);
    }

}
