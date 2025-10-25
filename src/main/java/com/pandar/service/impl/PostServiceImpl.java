package com.pandar.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.pandar.common.base.PageResp;
import com.pandar.common.constant.BizConstant;
import com.pandar.common.enums.PostEnum;
import com.pandar.common.exception.BusinessException;
import com.pandar.config.excel.vo.ErrorMessage;
import com.pandar.domain.dto.PostDTO;
import com.pandar.domain.entity.Post;
import com.pandar.domain.entity.UserPost;
import com.pandar.domain.dto.PostQueryDTO;
import com.pandar.domain.vo.ConfigVO;
import com.pandar.domain.vo.PostExcelVO;
import com.pandar.domain.vo.PostVO;
import com.pandar.mapper.PostMapper;
import com.pandar.mapper.UserPostMapper;
import com.pandar.service.PostService;
import com.pandar.utils.UnqIdUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;
    private final UserPostMapper userPostMapper;

    /**
     * 添加岗位
     *
     * @param postDTO 岗位数据
     */
    @Override
    public void addPost(PostDTO postDTO) {
        Post post = postMapper.selectPostByPostCode(postDTO.getPostCode());
        Assert.isNull(post, () -> new BusinessException(PostEnum.ErrorMsg.EXISTS_POST.getDesc()));
        long postId = UnqIdUtil.uniqueId();
        post = BeanUtil.copyProperties(postDTO, Post.class);
        post.setPostId(postId);
        postMapper.insert(post, true);
    }

    /**
     * 删除岗位 -> 根据岗位ID删除
     *
     * @param postId 岗位ID
     */
    @Override
    public void deletePostByPostId(Long postId) {
        //删除岗位
        postMapper.deletePostByPostId(postId);
        //删除岗位与用户关联关系
        LogicDeleteManager.execWithoutLogicDelete(() ->
                userPostMapper.deleteByQuery(QueryWrapper.create().eq(UserPost::getPostId, postId))
        );
    }

    /**
     * 修改岗位 -> 根据岗位ID修改
     *
     * @param postDTO 岗位数据
     */
    @Override
    public void updatePostByPostId(PostDTO postDTO) {
        Post newPost = BeanUtil.copyProperties(postDTO, Post.class);
        postMapper.updatePostByPostId(newPost);
    }

    /**
     * 查询岗位列表(分页)
     *
     * @param query 查询参数
     * @return 岗位列表
     */
    @Override
    public PageResp<PostVO> getPagePostList(PostQueryDTO query) {
        Page<PostVO> postVOPage = postMapper.selectPagePostList(query);
        return new PageResp<>(postVOPage.getRecords(), postVOPage.getTotalRow());
    }

    /**
     * 查询岗位列表(列表)
     *
     * @param query 查询参数
     * @return 岗位列表
     */
    @Override
    public List<PostExcelVO> getPostList(PostQueryDTO query) {
        List<Post> postList = postMapper.selectPostList(query);
        List<PostExcelVO> postExcelVOList = BeanUtil.copyToList(postList, PostExcelVO.class);
        return postExcelVOList;
    }

    /**
     * 根据岗位ID查询岗位
     *
     * @param postId 岗位ID
     * @return 岗位数据
     */
    @Override
    public PostVO getPostByPostId(Long postId) {
        return BeanUtil.copyProperties(postMapper.selectPostByPostId(postId), PostVO.class);
    }

    /**
     * 根据用户ID查询用户岗位
     *
     * @param userId 用户ID
     * @return 岗位列表
     */
    @Override
    public List<PostVO> getPostByUserId(Long userId) {
        //查询用户与岗位关联数据
        List<UserPost> userPostList = userPostMapper.selectUserPostRelation(userId);
        if (CollectionUtil.isEmpty(userPostList)) {
            return CollectionUtil.list(false);
        }
        List<Long> postIds = userPostList.stream().map(UserPost::getPostId).toList();
        return postMapper.selectPostByPostIds(postIds);
    }

    /**
     * 导入岗位
     *
     * @param posts
     * @param bindingResult
     * @return
     */
    @Override
    public ResponseEntity importPost(List<PostExcelVO> posts, BindingResult bindingResult) {
        // 通用校验获取失败的数据
        List<ErrorMessage> errorMessageList = (List<ErrorMessage>) bindingResult.getTarget();
        // 个性化校验逻辑
        List<Post> postList = postMapper.selectAll();
        for (PostExcelVO excel : posts) {
            Set<String> errorMsg = new HashSet<>();
            boolean existPost = postList.stream()
                    .anyMatch(post -> excel.getPostName().equals(post.getPostName())
                            || excel.getPostCode().equals(post.getPostCode()));

            if (existPost) {
                errorMsg.add("岗位名称【" + excel.getPostName() + "】或编码【" + excel.getPostCode() + "】已经存在");
            }
            // 数据合法情况
            if (CollUtil.isEmpty(errorMsg)) {
                insertExcelPost(excel);
            } else {
                // 数据不合法
                errorMessageList.add(new ErrorMessage(excel.getLineNum(), errorMsg));
            }
        }
        if (CollUtil.isNotEmpty(errorMessageList)) {
            return ResponseEntity.ok(errorMessageList);
        }
        return ResponseEntity.ok(BizConstant.IMPORT_SUCCESS);
    }

    /**
     * 插入excel Post
     */
    private void insertExcelPost(PostExcelVO excel) {
        Post post = new Post();
        BeanUtil.copyProperties(excel, post);
        long postId = UnqIdUtil.uniqueId();
        post.setPostId(postId);
        postMapper.insert(post, true);
    }

}
