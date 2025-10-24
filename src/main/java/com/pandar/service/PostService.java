package com.pandar.service;

import com.pandar.common.base.PageResp;
import com.pandar.domain.dto.PostDTO;
import com.pandar.domain.dto.PostQueryDTO;
import com.pandar.domain.vo.PostVO;

import java.util.List;

public interface PostService {

    /**
     * 添加岗位
     *
     * @param postDTO 岗位数据
     */
    void addPost(PostDTO postDTO);

    /**
     * 删除岗位 -> 根据岗位ID删除
     *
     * @param postId 岗位ID
     */
    void deletePostByPostId(Long postId);

    /**
     * 修改岗位 -> 根据岗位ID修改
     *
     * @param postDTO 岗位数据
     */
    void updatePostByPostId(PostDTO postDTO);

    /**
     * 查询岗位列表(分页)
     *
     * @param query 查询参数
     * @return 岗位列表
     */
    PageResp<PostVO> getPagePostList(PostQueryDTO query);

    /**
     * 根据岗位ID查询岗位
     *
     * @param postId 岗位ID
     * @return 岗位数据
     */
    PostVO getPostByPostId(Long postId);

    /**
     * 根据用户ID查询用户岗位
     *
     * @param userId 用户ID
     * @return 岗位列表
     */
    List<PostVO> getPostByUserId(Long userId);

}
