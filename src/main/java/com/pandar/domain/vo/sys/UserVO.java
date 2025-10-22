package com.pandar.domain.vo.sys;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.pandar.common.enums.StatusEnum;
import com.pandar.common.enums.UserEnum;
import com.pandar.config.convert.LongArrJsonSerializer;
import com.pandar.common.annotation.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@Schema(name = "用户管理实体")
public class UserVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(name = "userId", description = "用户ID", type = "string")
    private Long userId;

    @Schema(name = "username", description = "用户账号", type = "string")
    private String username;

    @JsonSerialize(using = NullSerializer.class)
    @Schema(name = "password", description = "密码", type = "string")
    private String password;

    @Schema(name = "nickname", description = "用户昵称", type = "string")
    private String nickname;

    @Schema(name = "userRealName", description = "用户真实姓名", type = "string")
    private String userRealName;

    @Schema(name = "phone", description = "手机号码", type = "string")
    private String phone;

    @Schema(name = "email", description = "用户邮箱，可为空", type = "string")
    private String email;

    @SchemaEnum(implementation = UserEnum.UserSex.class)
    @Schema(name = "userSex", description = "用户性别", type = "integer")
    private Integer userSex;

    @Schema(name = "userAvatar", description = "用户头像base64编码", type = "string")
    private String userAvatar;

    @Schema(name = "remark", description = "备注", type = "string")
    private String remark;

    @SchemaEnum(implementation = StatusEnum.class)
    @Schema(name = "status", description = "用户状态", type = "integer")
    private Integer status;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(name = "tenantId", description = "租户编号", type = "string")
    private Long tenantId;

    @JsonSerialize(using = LongArrJsonSerializer.class)
    @Schema(name = "roles", description = "角色ID集合", type = "array")
    private Set<Long> roleIds;

    @JsonSerialize(using = LongArrJsonSerializer.class)
    @Schema(name = "postIds", description = "岗位ID集合", type = "array")
    private Set<Long> postIds;

    @JsonSerialize(using = LongArrJsonSerializer.class)
    @Schema(name = "deptIds", description = "部门ID集合", type = "array")
    private Set<Long> deptIds;

    @Schema(name = "checkedPermIds", description = "用户部门编码集合，全勾选的节点", type = "array")
    private List<String> checkedDeptIds;

}
