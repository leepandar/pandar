package com.pandar.modules.sys.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.pandar.common.annotation.Log;
import com.pandar.common.base.PageResp;
import com.pandar.common.enums.BusinessTypeEnum;
import com.pandar.common.group.Add;
import com.pandar.common.group.Update;
import com.pandar.modules.sys.domain.dto.RoleDTO;
import com.pandar.modules.sys.domain.dto.RoleQueryDTO;
import com.pandar.modules.sys.domain.vo.RoleVO;
import com.pandar.modules.sys.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "角色管理")
@RequestMapping("/sys/role")
@Validated
@RestController
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/page")
    @SaCheckPermission("sys:role:query")
    @Operation(summary = "查询角色(分页) -> 角色管理使用")
    public ResponseEntity<PageResp<RoleVO>> getPageRoleList(RoleQueryDTO query) {
        return ResponseEntity.ok(roleService.getPageRoleList(query));
    }

    @GetMapping("/{roleId}")
    @SaCheckPermission("sys:role:query")
    @Operation(summary = "根据角色ID查询角色")
    public ResponseEntity<RoleVO> getRoleByRoleId(@PathVariable(value = "roleId")
                                                  @NotNull(message = "角色ID不能为空")
                                                  @Parameter(name = "roleId", description = "角色ID", required = true) Long roleId) {
        return ResponseEntity.ok(roleService.getRoleByRoleId(roleId));
    }

    @PostMapping("/add")
    @SaCheckPermission("sys:role:add")
    @Operation(summary = "添加角色")
    @Log(title = "角色管理", businessType = BusinessTypeEnum.INSERT)
    public ResponseEntity<Void> addRole(@RequestBody @Validated(Add.class) RoleDTO roleDTO) {
        roleService.addRole(roleDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/edit")
    @SaCheckPermission("sys:role:edit")
    @Operation(summary = "修改角色 -> 根据角色ID修改")
    @Log(title = "角色管理", businessType = BusinessTypeEnum.UPDATE)
    public ResponseEntity<Void> updateRoleByRoleId(@RequestBody @Validated(Update.class) RoleDTO roleDTO) {
        roleService.updateRoleByRoleId(roleDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    @SaCheckPermission("sys:role:del")
    @Operation(summary = "删除角色 -> 根据角色ID删除角色")
    @Log(title = "角色管理", businessType = BusinessTypeEnum.DELETE)
    public ResponseEntity<Void> deleteRoleByRoleId(@RequestParam("roleId")
                                                   @NotNull(message = "角色ID不能为空")
                                                   @Parameter(name = "roleId", required = true, description = "角色ID") Long roleId) {
        roleService.deleteRoleByRoleId(roleId);
        return ResponseEntity.ok().build();
    }
}
