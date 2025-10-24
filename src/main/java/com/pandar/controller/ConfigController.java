package com.pandar.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.pandar.common.annotation.Log;
import com.pandar.common.base.PageResp;
import com.pandar.common.enums.BusinessTypeEnum;
import com.pandar.common.group.Add;
import com.pandar.common.group.Update;
import com.pandar.domain.dto.ConfigDTO;
import com.pandar.domain.dto.ConfigQueryDTO;
import com.pandar.domain.vo.ConfigVO;
import com.pandar.service.ConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "参数配置管理")
@RequestMapping("/sys/config")
@Validated
@RestController
@RequiredArgsConstructor
public class ConfigController {

    private final ConfigService configService;

    @GetMapping("/page")
    @SaCheckPermission("sys:config:query")
    @Operation(summary = "查询参数配置列表(分页)")
    public ResponseEntity<PageResp<ConfigVO>> getPageConfigList(ConfigQueryDTO query) {
        return ResponseEntity.ok(configService.getPageConfigList(query));
    }

    @GetMapping("/{configId}")
    @SaCheckPermission("sys:config:query")
    @Operation(summary = "根据参数ID查询参数")
    public ResponseEntity<ConfigVO> getConfigByConfigId(@PathVariable(value = "configId")
                                                        @NotNull(message = "参数ID不能为空")
                                                        @Parameter(name = "configId", description = "参数ID", required = true) Long configId) {
        return ResponseEntity.ok(configService.getConfigByConfigId(configId));
    }

    @PostMapping("/add")
    @SaCheckPermission("sys:config:add")
    @Operation(summary = "添加参数")
    @Log(title = "参数配置", businessType = BusinessTypeEnum.INSERT)
    public ResponseEntity<Void> addConfig(@RequestBody @Validated(Add.class) ConfigDTO configDTO) {
        configService.addConfig(configDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("edit")
    @SaCheckPermission("sys:config:edit")
    @Operation(summary = "修改参数")
    @Log(title = "参数配置", businessType = BusinessTypeEnum.UPDATE)
    public ResponseEntity<Void> updateConfigByConfigId(@RequestBody @Validated(Update.class) ConfigDTO configDTO) {
        configService.updateConfigByConfigId(configDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    @SaCheckPermission("sys:config:del")
    @Operation(summary = "删除参数 -> 根据参数ID删除")
    @Log(title = "参数配置", businessType = BusinessTypeEnum.DELETE)
    public ResponseEntity<Void> deleteConfigByConfigId(@RequestParam("configId")
                                                       @NotNull(message = "参数ID不能为空")
                                                       @Parameter(name = "configId", required = true, description = "参数ID") Long configId) {
        configService.deleteConfigByConfigId(configId);
        return ResponseEntity.ok().build();
    }
}
