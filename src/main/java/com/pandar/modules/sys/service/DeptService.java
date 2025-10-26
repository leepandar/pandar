package com.pandar.modules.sys.service;

import com.pandar.modules.sys.domain.dto.DeptDTO;
import com.pandar.modules.sys.domain.dto.DeptQueryDTO;
import com.pandar.modules.sys.domain.vo.DeptVO;

import java.util.List;

public interface DeptService {

    /**
     * 添加部门
     *
     * @param deptDTO 部门数据
     */
    void addDept(DeptDTO deptDTO);

    /**
     * 删除部门 -> 根据部门ID删除
     *
     * @param deptId 部门ID
     */
    void deleteDeptByDeptId(Long deptId);

    /**
     * 修改部门 -> 根据部门ID修改
     *
     * @param deptDTO 部门数据
     */
    void updateDeptByDeptId(DeptDTO deptDTO);

    /**
     * 查询部门列表（全部不分页） -> 部门管理使用
     *
     * @param query 查询参数
     * @return 部门树
     */
    List<DeptVO> getDeptList(DeptQueryDTO query);

    /**
     * 根据部门ID查询部门
     *
     * @param deptId 部门ID
     * @return 部门数据
     */
    DeptVO getDeptByDeptId(Long deptId);

    /**
     * 查询部门列表 -> 其他地方使用(只获取正常状态的部门)
     *
     * @return 部门树
     */
    List<DeptVO> getEnableDeptList();

    /**
     * 根据部门ID查询部门
     *
     * @param deptIds 部门ID列表
     * @return 用户所属部门列表
     */
    List<DeptVO> getDeptByDeptIds(List<Long> deptIds);

}
