package com.pandar.domain.vo;

import com.pandar.common.group.Add;
import com.pandar.common.group.Update;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(name = "租户数据源实体")
public class TenantDatasourceVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "数据源名称不能为空", groups = {Add.class, Update.class})
    @Schema(name = "datasourceName", description = "数据源连接", type = "string")
    private String datasourceName;

    @NotBlank(message = "数据源连接不能为空", groups = {Add.class, Update.class})
    @Schema(name = "datasourceUrl", description = "数据源连接", type = "string")
    private String datasourceUrl;

    @NotBlank(message = "数据源用户名不能为空", groups = {Add.class, Update.class})
    @Schema(name = "username", description = "数据源用户名", type = "string")
    private String username;

    @NotBlank(message = "数据源密码不能为空", groups = {Add.class, Update.class})
    @Schema(name = "password", description = "数据源密码", type = "string")
    private String password;

}
