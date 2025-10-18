package com.pandar.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.pandar.common.base.PageResp;
import com.pandar.common.group.Add;
import com.pandar.common.group.Update;
import com.pandar.domain.dto.sys.RoleDTO;
import com.pandar.domain.dto.sys.RoleQueryDTO;
import com.pandar.domain.vo.sys.RoleVO;
import com.pandar.service.RoleService;
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

    @PostMapping("/addRole")
    @SaCheckPermission("sys:role:add")
    @Operation(summary = "添加角色")
    public ResponseEntity<Void> addRole(@RequestBody @Validated(Add.class) RoleDTO roleDTO) {
        roleService.addRole(roleDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteRoleByRoleId")
    @SaCheckPermission("sys:role:del")
    @Operation(summary = "删除角色 -> 根据角色ID删除角色")
    public ResponseEntity<Void> deleteRoleByRoleId(@RequestParam("roleId")
                                                   @NotNull(message = "角色ID不能为空")
                                                   @Parameter(name = "roleId", required = true, description = "角色ID") Long roleId) {
        roleService.deleteRoleByRoleId(roleId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/updateRoleByRoleId")
    @SaCheckPermission("sys:role:edit")
    @Operation(summary = "修改角色 -> 根据角色ID修改")
    public ResponseEntity<Void> updateRoleByRoleId(@RequestBody @Validated(Update.class) RoleDTO roleDTO) {
        roleService.updateRoleByRoleId(roleDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getPageRoleList")
    @SaCheckPermission("sys:role:query")
    @Operation(summary = "查询角色(分页) -> 角色管理使用")
    public ResponseEntity<PageResp<RoleVO>> getPageRoleList(RoleQueryDTO query) {
        return ResponseEntity.ok(roleService.getPageRoleList(query));
    }

    @GetMapping("/getRoleByRoleId/{roleId}")
    @SaCheckPermission("sys:role:query")
    @Operation(summary = "根据角色ID查询角色")
    public ResponseEntity<RoleVO> getRoleByRoleId(@PathVariable(value = "roleId")
                                                  @NotNull(message = "角色ID不能为空")
                                                  @Parameter(name = "roleId", description = "角色ID", required = true) Long roleId) {
        return ResponseEntity.ok(roleService.getRoleByRoleId(roleId));
    }

}
