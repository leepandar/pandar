package com.pandar.modules.sys.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.pandar.common.annotation.Log;
import com.pandar.common.enums.BusinessTypeEnum;
import com.pandar.common.group.Add;
import com.pandar.common.group.Update;
import com.pandar.modules.sys.domain.dto.PermDTO;
import com.pandar.modules.sys.domain.dto.PermQueryDTO;
import com.pandar.modules.sys.domain.vo.PermVO;
import com.pandar.modules.sys.service.PermService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "权限管理")
@RequestMapping("/sys/permission")
@Validated
@RestController
@RequiredArgsConstructor
public class PermController {

    private final PermService permService;

    @GetMapping("/list")
    @SaCheckPermission("sys:perm:query")
    @Operation(summary = "查询权限列表(不分页，获取全部数据) -> 权限管理使用")
    public ResponseEntity<List<PermVO>> getPermList(PermQueryDTO query) {
        return ResponseEntity.ok(permService.getPermList(query));
    }

    @GetMapping("/enable/list")
    @Operation(summary = "查询系统租户权限列表 -> 只获取正常状态的权限")
    public ResponseEntity<List<PermVO>> getEnablePermList() {
        return ResponseEntity.ok(permService.getEnablePermList());
    }

    @GetMapping("/tenant/list")
    @Operation(summary = "查询租户权限列表 -> 只获取正常状态的权限")
    public ResponseEntity<List<PermVO>> getTenantEnablePermList() {
        return ResponseEntity.ok(permService.getTenantEnablePermList());
    }

    @GetMapping("/{permId}")
    @SaCheckPermission("sys:perm:query")
    @Operation(summary = "根据权限ID查询权限")
    public ResponseEntity<PermVO> getPermByPermId(@PathVariable(value = "permId")
                                                  @NotNull(message = "权限ID不能为空")
                                                  @Parameter(name = "permId", description = "权限ID", required = true) Long permId) {
        return ResponseEntity.ok(permService.getPermByPermId(permId));
    }

    @PostMapping("/add")
    @SaCheckPermission("sys:perm:add")
    @Operation(summary = "添加权限")
    @Log(title = "权限管理", businessType = BusinessTypeEnum.INSERT)
    public ResponseEntity<Void> addPerm(@RequestBody @Validated(Add.class) PermDTO permDTO) {
        permService.addPerm(permDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/edit")
    @SaCheckPermission("sys:perm:edit")
    @Operation(summary = "修改权限 -> 根据权限ID修改")
    @Log(title = "权限管理", businessType = BusinessTypeEnum.UPDATE)
    public ResponseEntity<Void> updatePermByPermId(@RequestBody @Validated(Update.class) PermDTO permDTO) {
        permService.updatePermByPermId(permDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    @SaCheckPermission("sys:perm:del")
    @Operation(summary = "删除权限 -> 根据权限ID删除")
    @Log(title = "权限管理", businessType = BusinessTypeEnum.DELETE)
    public ResponseEntity<Void> deletePermByPermId(@RequestParam("permId")
                                                   @NotNull(message = "权限ID不能为空")
                                                   @Parameter(name = "permId", required = true, description = "权限ID") Long permId) {
        permService.deletePermByPermId(permId);
        return ResponseEntity.ok().build();
    }
}
