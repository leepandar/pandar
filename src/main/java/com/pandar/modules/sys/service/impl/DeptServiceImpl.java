package com.pandar.modules.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.pandar.common.constant.CommonConstant;
import com.pandar.common.enums.DeptEnum;
import com.pandar.common.enums.StatusEnum;
import com.pandar.common.exception.BusinessException;
import com.pandar.modules.sys.domain.dto.DeptDTO;
import com.pandar.modules.sys.domain.entity.Dept;
import com.pandar.modules.sys.domain.entity.UserDept;
import com.pandar.modules.sys.domain.dto.DeptQueryDTO;
import com.pandar.modules.sys.domain.vo.DeptVO;
import com.pandar.modules.sys.domain.dto.UserQueryDTO;
import com.pandar.modules.sys.domain.vo.UserVO;
import com.pandar.modules.sys.mapper.DeptMapper;
import com.pandar.modules.sys.mapper.UserDeptMapper;
import com.pandar.modules.sys.mapper.UserMapper;
import com.pandar.modules.sys.service.DeptService;
import com.pandar.common.utils.UnqIdUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeptServiceImpl implements DeptService {

    private final UserMapper userMapper;
    private final DeptMapper deptMapper;
    private final UserDeptMapper userDeptMapper;

    /**
     * 添加部门
     *
     * @param deptDTO 部门数据
     */
    @Override
    public void addDept(DeptDTO deptDTO) {
        Dept dept = BeanUtil.copyProperties(deptDTO, Dept.class);
        dept.setDeptId(UnqIdUtil.uniqueId());
        dept.setStatus(StatusEnum.STATUS_1.getCode());
        //如果不是顶级
        if (CommonConstant.ZERO != deptDTO.getParentDeptId()) {
            Dept parentDept = deptMapper.selectDeptByDeptId(deptDTO.getParentDeptId());
            //祖级列表
            String ancestors = Optional.ofNullable(parentDept.getAncestors())
                    .map(a -> a + StrUtil.COMMA)
                    .orElse(StrUtil.EMPTY) + parentDept.getDeptId();
            dept.setAncestors(ancestors);
        }
        //新增
        deptMapper.insert(dept, true);
    }

    /**
     * 删除部门 -> 根据部门ID删除
     *
     * @param deptId 部门ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDeptByDeptId(Long deptId) {
        //检查是否包含下级，包含下级不允许删除
        long childrenCount = deptMapper.selectChildrenCountByDeptId(deptId);
        Assert.isFalse(childrenCount > 0, () -> new BusinessException(DeptEnum.ErrorMsg.CONTAIN_CHILDREN.getDesc()));
        //检查是否有用户在该部门，有用户在该部门不允许删除
        UserQueryDTO query = new UserQueryDTO();
        query.setPageNum(1L);
        query.setPageSize(1L);
        query.setDeptId(deptId);
        Page<UserVO> userVOPage = userMapper.selectPageUserList(query);
        Assert.isFalse(userVOPage.getTotalRow() > 0, () -> new BusinessException(DeptEnum.ErrorMsg.USER_DEPT_CHILDREN.getDesc()));
        //删除部门
        deptMapper.deleteDeptByDeptId(deptId);
        //删除部门和用户关联关系
        LogicDeleteManager.execWithoutLogicDelete(() ->
                userDeptMapper.deleteByQuery(QueryWrapper.create().eq(UserDept::getDeptId, deptId))
        );
    }

    /**
     * 修改部门 -> 根据部门ID修改
     *
     * @param deptDTO 部门数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDeptByDeptId(DeptDTO deptDTO) {
        //查询本级部门
        Dept currentDept = deptMapper.selectDeptByDeptId(deptDTO.getDeptId());
        Assert.notNull(currentDept, () -> new BusinessException(DeptEnum.ErrorMsg.NONENTITY_DEPT.getDesc()));
        Dept updateDept = BeanUtil.copyProperties(deptDTO, Dept.class);
        //如果不是顶级部门，查询上级部门
        if (CommonConstant.ZERO != deptDTO.getParentDeptId()) {
            Dept parentDept = deptMapper.selectDeptByDeptId(deptDTO.getParentDeptId());
            //祖级列表
            String ancestors = Optional.ofNullable(parentDept.getAncestors())
                    .map(a -> a + StrUtil.COMMA)
                    .orElse(StrUtil.EMPTY) + parentDept.getDeptId();
            updateDept.setAncestors(ancestors);
            //修改当前部门所有下级
            updateChildrenDeptAncestors(deptDTO.getDeptId(), currentDept.getAncestors(), ancestors);
        } else {
            //修改当前部门所有下级
            updateChildrenDeptAncestors(deptDTO.getDeptId(), currentDept.getAncestors(), StrUtil.EMPTY);
        }
        deptMapper.updateDeptByDeptId(updateDept);
    }

    /**
     * 根据部门ID修改下级部门的祖级
     *
     * @param deptId       部门ID
     * @param oldAncestors 旧的祖级
     * @param newAncestors 新的祖级
     */
    private void updateChildrenDeptAncestors(Long deptId, String oldAncestors, String newAncestors) {
        //修改当前部门所有下级
        List<Dept> children = deptMapper.selectChildrenDeptByDeptId(deptId);
        for (Dept child : children) {
            if (StrUtil.isBlank(oldAncestors)) {
                if (StrUtil.isNotBlank(newAncestors)) {
                    child.setAncestors(newAncestors);
                }
            } else {
                child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
            }
            deptMapper.updateDeptByDeptId(child);
        }
    }

    /**
     * 查询部门列表（全部不分页） -> 部门管理使用
     *
     * @param query 查询参数
     * @return 部门树
     */
    @Override
    public List<DeptVO> getDeptList(DeptQueryDTO query) {
        return deptToTree(deptMapper.selectDeptList(query));
    }

    /**
     * 根据部门ID查询部门
     *
     * @param deptId 部门ID
     * @return 部门数据
     */
    @Override
    public DeptVO getDeptByDeptId(Long deptId) {
        return BeanUtil.copyProperties(deptMapper.selectDeptByDeptId(deptId), DeptVO.class);
    }

    /**
     * 查询部门列表 -> 其他地方使用(只获取正常状态的部门)
     *
     * @return 部门树
     */
    @Override
    public List<DeptVO> getEnableDeptList() {
        //部门树
        return deptToTree(deptMapper.selectDeptDict());
    }

    /**
     * 根据部门ID查询部门
     *
     * @param deptIds 部门ID列表
     * @return 用户所属部门列表
     */
    @Override
    public List<DeptVO> getDeptByDeptIds(List<Long> deptIds) {
        List<Dept> deptList = deptMapper.selectDeptByDeptIds(deptIds);
        return BeanUtil.copyToList(deptList, DeptVO.class);
    }

    /**
     * 转换部门树
     *
     * @param deptList 部门数据集合
     * @return 部门数据集合
     */
    private List<DeptVO> deptToTree(List<Dept> deptList) {
        if (CollectionUtil.isEmpty(deptList)) {
            return CollectionUtil.list(false);
        }
        //查找顶级节点
        List<DeptVO> rootNodeList = deptList.stream()
                .filter(p -> ObjectUtil.isNotNull(p.getParentDeptId()))
                .filter(p -> CommonConstant.ZERO == p.getParentDeptId())
                .map(p -> BeanUtil.copyProperties(p, DeptVO.class))
                .sorted(Comparator.comparing(DeptVO::getDeptSort))
                .toList();
        //查找子节点
        findChildren(deptList, rootNodeList);
        return rootNodeList;
    }

    /**
     * 查找子节点
     *
     * @param deptList     部门数据集合
     * @param rootNodeList 部门顶级节点数据集合
     */
    private void findChildren(List<Dept> deptList, List<DeptVO> rootNodeList) {
        //遍历顶级节点
        rootNodeList.forEach(node -> {
            //存储子节点
            List<DeptVO> childrenNodes = CollectionUtil.list(false);
            //从部门数据集合中查找子节点
            deptList.forEach(p -> {
                //节点是否关联
                if (node.getDeptId().equals(p.getParentDeptId())) {
                    childrenNodes.add(BeanUtil.copyProperties(p, DeptVO.class));
                }
                //显示排序
                childrenNodes.sort(Comparator.comparing(DeptVO::getDeptSort));
            });
            //如果有关联的子节点
            if (CollectionUtil.isNotEmpty(childrenNodes)) {
                //将查询到的子节点挂在顶级节点上
                node.setChildren(childrenNodes);
                //对子节点继续递归，查找子节点的下级
                findChildren(deptList, childrenNodes);
            }
        });
    }

}
