package com.pandar.modules.sys.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.pandar.common.annotation.Log;
import com.pandar.common.base.PageResp;
import com.pandar.common.enums.BusinessTypeEnum;
import com.pandar.modules.sys.domain.dto.FileQueryDTO;
import com.pandar.modules.sys.domain.dto.FileUploadBatchDTO;
import com.pandar.modules.sys.domain.dto.FileUploadDTO;
import com.pandar.modules.sys.domain.vo.FileUploadRespVO;
import com.pandar.modules.sys.domain.vo.FileVO;
import com.pandar.modules.sys.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "文件管理")
@RequestMapping("/sys/file")
@Validated
@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/page")
    @SaCheckPermission("sys:file:query")
    @Operation(summary = "查询文件列表(分页)")
    public ResponseEntity<PageResp<FileVO>> getPageFileList(FileQueryDTO query) {
        return ResponseEntity.ok(fileService.getPageFileList(query));
    }

    @PostMapping("/upload")
    @SaCheckPermission("sys:file:upload")
    @Operation(summary = "单文件上传")
    @Log(title = "文件管理", businessType = BusinessTypeEnum.UPLOAD)
    public ResponseEntity<FileUploadRespVO> uploadFile(@Validated FileUploadDTO fileUpload) {
        return ResponseEntity.ok(fileService.uploadFile(fileUpload));
    }

    @PostMapping("/uploadBatch")
    @SaCheckPermission("sys:file:upload")
    @Operation(summary = "批量文件上传")
    @Log(title = "文件管理", businessType = BusinessTypeEnum.UPLOAD)
    public ResponseEntity<List<FileUploadRespVO>> uploadFileBatch(@Validated FileUploadBatchDTO uploadBatch) {
        return ResponseEntity.ok(fileService.uploadFileBatch(uploadBatch));
    }

    @DeleteMapping("/delete")
    @SaCheckPermission("sys:file:del")
    @Operation(summary = "单文件删除")
    @Log(title = "文件管理", businessType = BusinessTypeEnum.DELETE)
    public ResponseEntity<Void> deleteFile(@RequestParam("fileId")
                                           @NotNull(message = "文件ID不能为空")
                                           @Parameter(name = "fileId", required = true, description = "fileId") Long fileId) {
        fileService.deleteFile(fileId);
        return ResponseEntity.ok().build();
    }
}
