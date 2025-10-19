package com.pandar.controller.sys;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.pandar.common.group.Add;
import com.pandar.common.group.Update;
import com.pandar.domain.dto.sys.DeptDTO;
import com.pandar.domain.dto.sys.DeptQueryDTO;
import com.pandar.domain.vo.sys.DeptVO;
import com.pandar.service.DeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "部门管理")
@RequestMapping("/sys/dept")
@Validated
@RestController
@RequiredArgsConstructor
public class DeptController {

    private final DeptService deptService;

    @GetMapping("/list")
    @SaCheckPermission("sys:dept:query")
    @Operation(summary = "查询部门列表(不分页，获取全部数据) -> 部门管理使用")
    public ResponseEntity<List<DeptVO>> getDeptList(DeptQueryDTO query) {
        return ResponseEntity.ok(deptService.getDeptList(query));
    }

    @GetMapping("/enable/list")
    @SaCheckPermission("sys:dept:query")
    @Operation(summary = "查询部门树 -> 只获取正常状态的部门")
    public ResponseEntity<List<DeptVO>> getEnableDeptList() {
        return ResponseEntity.ok(deptService.getEnableDeptList());
    }

    @GetMapping("/{deptId}")
    @SaCheckPermission("sys:dept:query")
    @Operation(summary = "根据部门ID查询部门")
    public ResponseEntity<DeptVO> getDeptByDeptId(@PathVariable(value = "deptId")
                                                  @NotNull(message = "部门ID不能为空")
                                                  @Parameter(name = "deptId", description = "部门ID", required = true) Long deptId) {
        return ResponseEntity.ok(deptService.getDeptByDeptId(deptId));
    }

    @PostMapping("/add")
    @SaCheckPermission("sys:dept:add")
    @Operation(summary = "添加部门")
    public ResponseEntity<Void> addDept(@RequestBody @Validated(Add.class) DeptDTO deptDTO) {
        deptService.addDept(deptDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/edit")
    @SaCheckPermission("sys:dept:edit")
    @Operation(summary = "修改部门 -> 根据部门ID修改")
    public ResponseEntity<Void> updateDeptByDeptId(@RequestBody @Validated(Update.class) DeptDTO deptDTO) {
        deptService.updateDeptByDeptId(deptDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    @SaCheckPermission("sys:dept:del")
    @Operation(summary = "删除部门 -> 根据部门ID删除")
    public ResponseEntity<Void> deleteDeptByDeptId(@RequestParam("deptId")
                                                   @NotNull(message = "部门ID不能为空")
                                                   @Parameter(name = "deptId", required = true, description = "部门ID") Long deptId) {
        deptService.deleteDeptByDeptId(deptId);
        return ResponseEntity.ok().build();
    }
}
