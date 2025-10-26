package com.pandar.modules.sys.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;
import com.pandar.common.enums.StatusEnum;
import com.pandar.modules.sys.domain.entity.Dept;
import com.pandar.modules.sys.domain.dto.DeptQueryDTO;

import java.util.List;

public interface DeptMapper extends BaseMapper<Dept> {

    /**
     * 根据部门ID查询直属下级数量
     *
     * @param deptId 部门ID
     * @return 下级数量
     */
    default long selectChildrenCountByDeptId(Long deptId) {
        return selectCountByQuery(QueryWrapper.create().where("FIND_IN_SET(" + deptId + ", ancestors)"));
    }

    /**
     * 根据部门ID删除部门
     *
     * @param deptId 部门ID
     */
    default void deleteDeptByDeptId(Long deptId) {
        deleteByQuery(QueryWrapper.create()
                .eq(Dept::getDeptId, deptId));
    }

    /**
     * 根据部门ID获取部门(单条)
     *
     * @param deptId 部门ID
     * @return 部门实体
     */
    default Dept selectDeptByDeptId(Long deptId) {
        return selectOneByQuery(QueryWrapper.create().eq(Dept::getDeptId, deptId));
    }

    /**
     * 根据部门ID获取部门(批量)
     *
     * @param deptIds 部门ID列表
     * @return 部门实体列表
     */
    default List<Dept> selectDeptByDeptIds(List<Long> deptIds) {
        return selectListByQuery(QueryWrapper.create().in(Dept::getDeptId, deptIds));
    }

    /**
     * 根据部门ID修改部门
     *
     * @param dept 部门实体
     */
    default void updateDeptByDeptId(Dept dept) {
        updateByQuery(dept,
                QueryWrapper.create().eq(Dept::getDeptId, dept.getDeptId()));
    }

    /**
     * 根据条件查询部门列表
     *
     * @param query 查询参数
     * @return 部门列表
     */
    default List<Dept> selectDeptList(DeptQueryDTO query) {
        return selectListByQuery(QueryWrapper.create()
                .eq(Dept::getStatus, query.getStatus())
                .like(Dept::getDeptName, query.getDeptName()));
    }

    /**
     * 根据租户ID查询部门列表 -> 字典查询
     *
     * @return 部门列表
     */
    default List<Dept> selectDeptDict() {
        return selectListByQuery(QueryWrapper.create()
                .eq(Dept::getStatus, StatusEnum.STATUS_1.getCode()));
    }

    /**
     * 根据部门ID查询所有子集部门
     *
     * @param deptId 部门ID
     * @return 子集部门列表
     */
    default List<Dept> selectChildrenDeptByDeptId(Long deptId) {
        return selectListByQuery(QueryWrapper.create().where("FIND_IN_SET(" + deptId + ", ancestors)"));
    }
}
