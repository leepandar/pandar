package com.pandar.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.pandar.common.enums.StatusEnum;
import com.pandar.domain.entity.sys.Post;
import com.pandar.domain.dto.sys.PostQueryDTO;
import com.pandar.domain.vo.sys.PostVO;

import java.util.List;

public interface PostMapper extends BaseMapper<Post> {

    /**
     * 根据岗位编码查询岗位
     *
     * @param postCode 岗位编码
     * @return 岗位实体
     */
    default Post selectPostByPostCode(String postCode) {
        return selectOneByQuery(QueryWrapper.create().eq(Post::getPostCode, postCode));
    }

    /**
     * 根据岗位ID删除岗位
     *
     * @param postId 岗位ID
     */
    default void deletePostByPostId(Long postId) {
        deleteByQuery(QueryWrapper.create().eq(Post::getPostId, postId));
    }

    /**
     * 根据岗位ID查询岗位(单条)
     *
     * @param postId 岗位ID
     * @return 岗位实体
     */
    default Post selectPostByPostId(Long postId) {
        return selectOneByQuery(QueryWrapper.create().eq(Post::getPostId, postId));
    }

    /**
     * 根据岗位ID查询岗位(批量)
     *
     * @param postIds 岗位ID列表
     * @return 岗位实体列表
     */
    default List<PostVO> selectPostByPostIds(List<Long> postIds) {
        return selectListByQueryAs(QueryWrapper.create().in(Post::getPostId, postIds), PostVO.class);
    }

    /**
     * 根据岗位ID修改岗位
     *
     * @param mPost 岗位数据
     */
    default void updatePostByPostId(Post mPost) {
        updateByQuery(mPost, QueryWrapper.create().eq(Post::getPostId, mPost.getPostId()));
    }

    /**
     * 查询岗位列表(分页)
     *
     * @param query 查询条件
     * @return 岗位分页数据
     */
    default Page<PostVO> selectPagePostList(PostQueryDTO query) {
        return paginateAs(query.getPageNum(), query.getPageSize(),
                QueryWrapper.create()
                        .eq(Post::getStatus, query.getStatus())
                        .like(Post::getPostName, query.getPostName())
                        .like(Post::getPostCode, query.getPostCode())
                        .orderBy(Post::getPostSort, true),
                PostVO.class
        );
    }

    /**
     * 根据租户ID查询岗位列表 -> 字典查询
     *
     * @return 部门列表
     */
    default List<Post> selectPostDict() {
        return selectListByQuery(QueryWrapper.create().eq(Post::getStatus, StatusEnum.STATUS_1.getCode()));
    }
}
