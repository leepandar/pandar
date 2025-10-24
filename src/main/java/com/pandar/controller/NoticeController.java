package com.pandar.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.pandar.common.annotation.Log;
import com.pandar.common.base.PageReq;
import com.pandar.common.base.PageResp;
import com.pandar.common.enums.BusinessTypeEnum;
import com.pandar.common.group.Add;
import com.pandar.common.group.Update;
import com.pandar.domain.dto.NoticeDTO;
import com.pandar.domain.dto.NoticeQueryDTO;
import com.pandar.domain.vo.NoticeVO;
import com.pandar.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import net.dreamlu.mica.xss.core.XssCleanIgnore;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "通知、公告管理")
@RequestMapping("/sys/notice")
@Validated
@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/page")
    @SaCheckPermission("sys:notice:query")
    @Operation(summary = "查询公告列表(分页) -> 公告管理使用")
    public ResponseEntity<PageResp<NoticeVO>> getPageNoticeList(NoticeQueryDTO query) {
        return ResponseEntity.ok(noticeService.getPageNoticeList(query));
    }

    @GetMapping("/list")
    @Operation(summary = "查询公告列表(分页) -> 首页使用")
    public ResponseEntity<PageResp<NoticeVO>> getPageHomeNoticeList(PageReq pageReq) {
        return ResponseEntity.ok(noticeService.getPageHomeNoticeList(pageReq));
    }

    @GetMapping("/{noticeId}")
    @Operation(summary = "根据公告ID查询公告")
    public ResponseEntity<NoticeVO> getNoticeByNoticeId(@PathVariable(value = "noticeId")
                                                        @NotNull(message = "公告ID不能为空")
                                                        @Parameter(name = "noticeId", description = "公告ID", required = true) Long noticeId) {
        return ResponseEntity.ok(noticeService.getNoticeByNoticeId(noticeId));
    }

    @XssCleanIgnore
    @PostMapping("/add")
    @SaCheckPermission("sys:notice:add")
    @Operation(summary = "添加公告")
    @Log(title = "通知公告", businessType = BusinessTypeEnum.INSERT)
    public ResponseEntity<Void> addNotice(@RequestBody @Validated(Add.class) NoticeDTO noticeDTO) {
        noticeService.addNotice(noticeDTO);
        return ResponseEntity.ok().build();
    }

    @XssCleanIgnore
    @PutMapping("/edit")
    @SaCheckPermission("sys:notice:edit")
    @Operation(summary = "修改公告 -> 根据公告ID修改")
    @Log(title = "通知公告", businessType = BusinessTypeEnum.UPDATE)
    public ResponseEntity<Void> updateNoticeByNoticeId(@RequestBody @Validated(Update.class) NoticeDTO noticeDTO) {
        noticeService.updateNoticeByNoticeId(noticeDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    @SaCheckPermission("sys:notice:del")
    @Operation(summary = "删除公告 -> 根据公告ID删除")
    @Log(title = "通知公告", businessType = BusinessTypeEnum.DELETE)
    public ResponseEntity<Void> deleteNoticeByNoticeId(@RequestParam("noticeId")
                                                       @NotNull(message = "公告ID不能为空")
                                                       @Parameter(name = "noticeId", required = true, description = "公告ID") Long noticeId) {
        noticeService.deleteNoticeByNoticeId(noticeId);
        return ResponseEntity.ok().build();
    }
}
