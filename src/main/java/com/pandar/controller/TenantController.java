package com.pandar.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.pandar.common.annotation.Log;
import com.pandar.common.base.PageResp;
import com.pandar.common.enums.BusinessTypeEnum;
import com.pandar.common.group.Add;
import com.pandar.common.group.Update;
import com.pandar.common.annotation.TenantIgnore;
import com.pandar.domain.dto.TenantDTO;
import com.pandar.domain.dto.TenantQueryDTO;
import com.pandar.domain.vo.TenantVO;
import com.pandar.service.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "租户管理")
@RequestMapping("/tenant")
@Validated
@RestController
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    @TenantIgnore
    @GetMapping
    @SaCheckPermission("sys:tenant:query")
    @Operation(summary = "查询租户(分页)")
    public ResponseEntity<PageResp<TenantVO>> getPageTenantList(TenantQueryDTO query) {
        return ResponseEntity.ok(tenantService.getPageTenantList(query));
    }

    @TenantIgnore
    @GetMapping("/{tenantId}")
    @SaCheckPermission("sys:tenant:query")
    @Operation(summary = "根据租户ID查询租户")
    public ResponseEntity<TenantVO> getTenantByTenantId(@PathVariable(value = "tenantId")
                                                        @NotNull(message = "租户ID不能为空")
                                                        @Parameter(name = "tenantId", description = "租户ID", required = true) Long tenantId) {
        return ResponseEntity.ok(tenantService.getTenantByTenantId(tenantId));
    }

    @TenantIgnore
    @PostMapping
    @SaCheckPermission("sys:tenant:add")
    @Operation(summary = "添加租户")
    @Log(title = "租户管理", businessType = BusinessTypeEnum.INSERT)
    public ResponseEntity<Void> addTenant(@RequestBody @Validated(Add.class) TenantDTO tenantDTO) {
        tenantService.addTenant(tenantDTO);
        return ResponseEntity.ok().build();
    }

    @TenantIgnore
    @PutMapping
    @SaCheckPermission("sys:tenant:edit")
    @Operation(summary = "修改租户 -> 根据租户ID修改")
    @Log(title = "租户管理", businessType = BusinessTypeEnum.UPDATE)
    public ResponseEntity<Void> updateTenantByTenantId(@RequestBody @Validated(Update.class) TenantDTO tenantDTO) {
        tenantService.updateTenantByTenantId(tenantDTO);
        return ResponseEntity.ok().build();
    }

    @TenantIgnore
    @DeleteMapping
    @SaCheckPermission("sys:tenant:del")
    @Operation(summary = "删除租户 -> 根据租户ID删除租户")
    @Log(title = "租户管理", businessType = BusinessTypeEnum.DELETE)
    public ResponseEntity<Void> deleteTenantByTenantId(@RequestParam("tenantId")
                                                       @NotNull(message = "租户ID不能为空")
                                                       @Parameter(name = "tenantId", required = true, description = "租户ID") Long tenantId) {
        tenantService.deleteTenantByTenantId(tenantId);
        return ResponseEntity.ok().build();
    }
}
