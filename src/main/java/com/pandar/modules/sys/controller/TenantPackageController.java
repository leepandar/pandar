package com.pandar.modules.sys.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.pandar.common.annotation.Log;
import com.pandar.common.base.PageResp;
import com.pandar.common.enums.BusinessTypeEnum;
import com.pandar.common.group.Add;
import com.pandar.common.group.Update;
import com.pandar.common.annotation.TenantIgnore;
import com.pandar.modules.sys.domain.dto.TenantPackageDTO;
import com.pandar.modules.sys.domain.dto.TenantPackageQueryDTO;
import com.pandar.modules.sys.domain.vo.TenantPackageVO;
import com.pandar.modules.sys.service.TenantPackageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "租户套餐")
@RequestMapping("/tenant/tenantPackage")
@Validated
@RestController
@RequiredArgsConstructor
public class TenantPackageController {

    private final TenantPackageService tenantPackageService;

    @TenantIgnore
    @GetMapping
    @SaCheckPermission("sys:tenantPackage:query")
    @Operation(summary = "查询租户套餐(分页)")
    public ResponseEntity<PageResp<TenantPackageVO>> getPageTenantPackageList(TenantPackageQueryDTO query) {
        return ResponseEntity.ok(tenantPackageService.getPageTenantPackageList(query));
    }

    @TenantIgnore
    @GetMapping("/{tenantPackageId}")
    @SaCheckPermission("sys:tenantPackage:query")
    @Operation(summary = "根据租户套餐ID查询租户套餐")
    public ResponseEntity<TenantPackageVO> getTenantPackageByTenantPackageId(@PathVariable(value = "tenantPackageId")
                                                                             @NotNull(message = "租户套餐ID不能为空")
                                                                             @Parameter(name = "tenantPackageId", description = "租户套餐ID", required = true) Long tenantPackageId) {
        return ResponseEntity.ok(tenantPackageService.getTenantPackageByTenantPackageId(tenantPackageId));
    }

    @TenantIgnore
    @PostMapping
    @SaCheckPermission("sys:tenantPackage:add")
    @Operation(summary = "添加租户套餐")
    @Log(title = "租户套餐", businessType = BusinessTypeEnum.INSERT)
    public ResponseEntity<Void> addTenantPackage(@RequestBody @Validated(Add.class) TenantPackageDTO tenantPackageDTO) {
        tenantPackageService.addTenantPackage(tenantPackageDTO);
        return ResponseEntity.ok().build();
    }

    @TenantIgnore
    @PutMapping
    @SaCheckPermission("sys:tenantPackage:edit")
    @Operation(summary = "修改租户套餐 -> 根据租户套餐ID修改")
    @Log(title = "租户套餐", businessType = BusinessTypeEnum.UPDATE)
    public ResponseEntity<Void> updateTenantPackageByTenantPackageId(@RequestBody @Validated(Update.class) TenantPackageDTO tenantPackageDTO) {
        tenantPackageService.updateTenantPackageByTenantPackageId(tenantPackageDTO);
        return ResponseEntity.ok().build();
    }

    @TenantIgnore
    @DeleteMapping
    @SaCheckPermission("sys:tenantPackage:del")
    @Operation(summary = "删除租户套餐 -> 根据租户套餐ID删除租户套餐")
    @Log(title = "租户套餐", businessType = BusinessTypeEnum.DELETE)
    public ResponseEntity<Void> deleteTenantPackageByTenantPackageId(@RequestParam("tenantPackageId")
                                                                     @NotNull(message = "租户套餐ID不能为空")
                                                                     @Parameter(name = "tenantPackageId", required = true, description = "租户套餐ID") Long tenantPackageId) {
        tenantPackageService.deleteTenantPackageByTenantPackageId(tenantPackageId);
        return ResponseEntity.ok().build();
    }
}
