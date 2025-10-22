package com.pandar.controller.sys;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.pandar.common.annotation.Log;
import com.pandar.common.base.PageResp;
import com.pandar.common.enums.BusinessTypeEnum;
import com.pandar.common.group.Add;
import com.pandar.common.group.Update;
import com.pandar.domain.dto.sys.PostDTO;
import com.pandar.domain.dto.sys.PostQueryDTO;
import com.pandar.domain.vo.sys.PostVO;
import com.pandar.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "岗位管理")
@RequestMapping("/sys/post")
@Validated
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/page")
    @SaCheckPermission("sys:post:query")
    @Operation(summary = "查询岗位列表(分页)")
    public ResponseEntity<PageResp<PostVO>> getPagePostList(PostQueryDTO query) {
        return ResponseEntity.ok(postService.getPagePostList(query));
    }

    @GetMapping("/{postId}")
    @SaCheckPermission("sys:post:query")
    @Operation(summary = "根据岗位ID查询岗位")
    public ResponseEntity<PostVO> getPostByPostId(@PathVariable(value = "postId")
                                                  @NotNull(message = "岗位ID不能为空")
                                                  @Parameter(name = "postId", description = "岗位ID", required = true) Long postId) {
        return ResponseEntity.ok(postService.getPostByPostId(postId));
    }

    @PostMapping("/add")
    @SaCheckPermission("sys:post:add")
    @Operation(summary = "添加岗位")
    @Log(title = "岗位管理", businessType = BusinessTypeEnum.INSERT)
    public ResponseEntity<Void> addPost(@RequestBody @Validated(Add.class) PostDTO postDTO) {
        postService.addPost(postDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/edit")
    @SaCheckPermission("sys:post:edit")
    @Operation(summary = "修改岗位 -> 根据岗位ID修改")
    public ResponseEntity<Void> updatePostByPostId(@RequestBody @Validated(Update.class) PostDTO postDTO) {
        postService.updatePostByPostId(postDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    @SaCheckPermission("sys:post:del")
    @Operation(summary = "删除岗位 -> 根据岗位ID删除")
    public ResponseEntity<Void> deletePostByPostId(@RequestParam("postId")
                                                   @NotNull(message = "岗位ID不能为空")
                                                   @Parameter(name = "postId", required = true, description = "岗位ID") Long postId) {
        postService.deletePostByPostId(postId);
        return ResponseEntity.ok().build();
    }
}
