package cn.edu.bit.cangjingge.fiction.controller;

import cn.edu.bit.cangjingge.common.entity.FictionChapter;
import cn.edu.bit.cangjingge.common.response.Response;
import cn.edu.bit.cangjingge.fiction.service.FictionServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class FictionChapterController {

    @Resource
    private FictionServiceImpl fictionService;

    @ApiOperation("使用小说的ID以及章节的ID获取章节")
    @GetMapping("/{fictionId}/chapters/{chapterId}")
    public Response<FictionChapter> getChapter(
            @PathVariable("fictionId") final Long fictionId,
            @PathVariable("chapterId") final Long chapterId
    ) {
        return fictionService.getFictionChapterByFictionIdAndChapterId(fictionId, chapterId);
    }

    /**
     * @return 更新后的章节
     */
    @ApiOperation("更新小说章节的标题，内容")
    @PutMapping("/{fictionId}/chapters/{chapterId}")
    public Response<FictionChapter> updateChapter(
            @PathVariable("fictionId") final Long fictionId,
            @PathVariable("chapterId") final Long chapterId,
            final String title,
            final String content
    ) {
        return fictionService.updateFictionChapter(fictionId, chapterId, title, content);
    }

    @ApiOperation("获取小说的所有章节")
    @GetMapping("/{fictionId}/chapters")
    public Response<List<FictionChapter>> getChapterList(
            @PathVariable("fictionId") final Long fictionId
    ) {
        return fictionService.getChapterInfoByFictionId(fictionId);
    }

    /**
     * @return 创建的章节
     */
    @ApiOperation("为小说ID创建小说章节")
    @PostMapping("/{fictionId}/chapters")
    public Response<FictionChapter> createChapter(
            @PathVariable("fictionId") final Long fictionId,
            final String title,
            final String content
    ) {
        return fictionService.createFictionChapter(fictionId, title, content);
    }

}
