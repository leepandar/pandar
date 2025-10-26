package com.pandar.modules.sys.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.pandar.common.annotation.Log;
import com.pandar.common.base.PageResp;
import com.pandar.common.enums.BusinessTypeEnum;
import com.pandar.modules.sys.domain.dto.DictInfoDTO;
import com.pandar.modules.sys.domain.vo.DictCacheVO;
import com.pandar.modules.sys.domain.vo.DictInfoVO;
import com.pandar.modules.sys.domain.dto.DictQueryDTO;
import com.pandar.modules.sys.domain.vo.DictVO;
import com.pandar.modules.sys.service.DictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "字典管理")
@RequestMapping("/sys/dict")
@Validated
@RestController
@RequiredArgsConstructor
public class DictController {

    private final DictService dictService;

    @GetMapping("/page")
    @SaCheckPermission("sys:dict:query")
    @Operation(summary = "查询字典列表(分页)")
    public ResponseEntity<PageResp<DictVO>> getPageDictList(DictQueryDTO queryVO) {
        return ResponseEntity.ok(dictService.getPageDictList(queryVO));
    }

    @GetMapping("/list/{dictTypes}")
    @Operation(summary = "根据字典类型查询字典 -> 用于下拉框数据展示或编码转换")
    public ResponseEntity<List<DictCacheVO>> getDictList(@PathVariable(value = "dictTypes")
                                                         @NotEmpty(message = "字典类型不能为空")
                                                         @Parameter(name = "dictTypes", description = "字典类型列表，为空则查询所有字典数据") List<String> dictType) {
        return ResponseEntity.ok(dictService.getDictList(dictType));
    }

    @GetMapping("/dictType/{dictType}")
    @SaCheckPermission("sys:dict:query")
    @Operation(summary = "根据字典类型查询字典 -> 用于字典管理页面")
    public ResponseEntity<DictInfoVO> getDictByDictType(@PathVariable(value = "dictType")
                                                        @Parameter(name = "dictType", description = "字典类型", required = true) String dictType) {
        return ResponseEntity.ok(dictService.getDictByDictType(dictType));
    }

    @PostMapping("/add")
    @SaCheckPermission("sys:dict:add")
    @Operation(summary = "添加字典")
    @Log(title = "字典管理", businessType = BusinessTypeEnum.INSERT)
    public ResponseEntity<Void> addDict(@RequestBody @Validated DictInfoDTO dictInfoDTO) {
        dictService.addDict(dictInfoDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/edit")
    @SaCheckPermission("sys:dict:edit")
    @Operation(summary = "修改字典")
    @Log(title = "字典管理", businessType = BusinessTypeEnum.UPDATE)
    public ResponseEntity<Void> updateDictByDictId(@RequestBody @Validated DictInfoDTO dictInfoDTO) {
        dictService.updateDictByDictId(dictInfoDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    @SaCheckPermission("sys:dict:del")
    @Operation(summary = "删除字典 -> 根据字典ID删除")
    @Log(title = "字典管理", businessType = BusinessTypeEnum.DELETE)
    public ResponseEntity<Void> deleteDictByDictId(@RequestParam("dictId")
                                                   @NotNull(message = "字典ID不能为空")
                                                   @Parameter(name = "dictId", required = true, description = "字典ID") Long dictId) {
        dictService.deleteDictByDictId(dictId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/dictType/delete")
    @SaCheckPermission("sys:dict:del")
    @Operation(summary = "删除字典 -> 根据字典类型删除")
    @Log(title = "字典管理", businessType = BusinessTypeEnum.DELETE)
    public ResponseEntity<Void> deleteDictByDictType(@RequestParam("dictType")
                                                     @NotNull(message = "字典类型不能为空")
                                                     @Parameter(name = "dictType", required = true, description = "字典类型") String dictType) {
        dictService.deleteDictByDictType(dictType);
        return ResponseEntity.ok().build();
    }

}
