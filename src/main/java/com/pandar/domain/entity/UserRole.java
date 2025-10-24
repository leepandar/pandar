package com.pandar.domain.entity;

import com.mybatisflex.annotation.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户与角色关联表 1用户-N角色
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(value = "sys_user_role")
public class UserRole implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;

}
