package com.pandar.controller;

import com.pandar.common.base.PageResp;
import com.pandar.domain.dto.OperLogQueryDTO;
import com.pandar.domain.entity.OperLog;
import com.pandar.service.OperLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "操作日志")
@RequestMapping("/sys/operlog")
@RestController
@RequiredArgsConstructor
public class OperLogController {

    private final OperLogService operLogService;

    @GetMapping("/page")
    @Operation(summary = "查询操作日志列表(分页)")
    public ResponseEntity<PageResp<OperLog>> getPageOperLogList(OperLogQueryDTO query) {
        return ResponseEntity.ok(operLogService.getPageOperLogList(query));
    }

    @GetMapping("/{operId}")
    @Operation(summary = "根据操作日志ID查询操作日志")
    public ResponseEntity<OperLog> getOperLogByOperId(@PathVariable(value = "operId")
                                                  @NotNull(message = "岗位ID不能为空")
                                                  @Parameter(name = "operId", description = "操作日志ID", required = true) Long operId) {
        return ResponseEntity.ok(operLogService.getOperLogByOperId(operId));
    }
}
