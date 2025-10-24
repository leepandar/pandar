package com.pandar.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.pandar.common.annotation.Log;
import com.pandar.common.base.PageResp;
import com.pandar.common.enums.BusinessTypeEnum;
import com.pandar.common.group.Add;
import com.pandar.common.group.Update;
import com.pandar.domain.dto.StorageDTO;
import com.pandar.domain.dto.StorageQueryDTO;
import com.pandar.domain.vo.StorageVO;
import com.pandar.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "存储管理")
@RequestMapping("/sys/storage")
@Validated
@RestController
@RequiredArgsConstructor
public class StorageController {

    @Autowired
    private StorageService storageService;

    @GetMapping("/page")
    @SaCheckPermission("sys:storage:query")
    @Operation(summary = "查询存储列表(分页)")
    public ResponseEntity<PageResp<StorageVO>> getPageStorageList(StorageQueryDTO query) {
        return ResponseEntity.ok(storageService.getPageStorageList(query));
    }

    @GetMapping("/{storageId}")
    @SaCheckPermission("sys:storage:query")
    @Operation(summary = "根据存储ID查询存储信息")
    public ResponseEntity<StorageVO> getStorageByStorageId(@PathVariable(value = "storageId")
                                                           @NotNull(message = "存储ID不能为空")
                                                           @Parameter(name = "storageId", description = "存储ID", required = true) Long storageId) {
        return ResponseEntity.ok(storageService.getStorageByStorageId(storageId));
    }

    @PostMapping("/add")
    @SaCheckPermission("sys:storage:add")
    @Operation(summary = "添加存储")
    @Log(title = "存储管理", businessType = BusinessTypeEnum.INSERT)
    public ResponseEntity<Void> addStorage(@RequestBody @Validated(Add.class) StorageDTO storageDTO) {
        storageService.addStorage(storageDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/edit")
    @SaCheckPermission("sys:storage:edit")
    @Operation(summary = "修改存储 -> 根据存储ID修改")
    @Log(title = "存储管理", businessType = BusinessTypeEnum.UPDATE)
    public ResponseEntity<Void> updateStorageByStorageId(@RequestBody @Validated(Update.class) StorageDTO storageDTO) {
        storageService.updateStorageByStorageId(storageDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    @SaCheckPermission("sys:storage:del")
    @Operation(summary = "删除存储 -> 根据存储ID删除")
    @Log(title = "存储管理", businessType = BusinessTypeEnum.DELETE)
    public ResponseEntity<Void> deleteStorageByStorageId(@RequestParam("storageId")
                                                         @NotNull(message = "存储ID不能为空")
                                                         @Parameter(name = "storageId", required = true, description = "存储ID") Long storageId) {
        storageService.deleteStorageByStorageId(storageId);
        return ResponseEntity.ok().build();
    }
}
