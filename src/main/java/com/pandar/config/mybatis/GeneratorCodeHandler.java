package com.pandar.config.mybatis;

import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.config.TableConfig;
import com.pandar.common.base.BaseEntity;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 配置Mybatis-Flex生成代码
 */
public class GeneratorCodeHandler {

    public static final String url = "jdbc:mysql://127.0.0.1:3306/pandar?characterEncoding=utf-8";

    public static final String username = "root";

    public static final String password = "root";

    public static void main(String[] args) {
        //配置数据源
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        //项目根目录
        String projectPath = System.getProperty("user.dir");
        //生成配置
        GlobalConfig globalConfig = new GlobalConfig();
        //设置根包
        globalConfig.setBasePackage("com.pandar");
        //生成路径
        globalConfig.setSourceDir(projectPath + "\\generator\\java");

        /* ------------------------ 实体配置 ------------------------ */

        //生成实体
        globalConfig.setEntityGenerateEnable(true);
        //使用lombok
        globalConfig.setEntityWithLombok(true);
        //设置项目的JDK版本，项目的JDK为14及以上时建议设置该项，小于14则可以不设置
        globalConfig.setEntityJdkVersion(17);
        //设置生成 entity 包名
        globalConfig.setEntityPackage("com.pandar.entity");
        //自动填充
        TableConfig tableConfig = new TableConfig();
        tableConfig.setInsertListenerClass(InsertFullColumnHandler.class);
        tableConfig.setUpdateListenerClass(UpdateFullColumnHandler.class);
        globalConfig.setTableConfig(tableConfig);
        //设置实体类的父类
        globalConfig.setEntitySuperClass(BaseEntity.class);
        //父类字段忽略
        globalConfig.getStrategyConfig()
                .setIgnoreColumns("id", "deleted", "create_time", "create_id", "update_id", "update_time", "version");

        /* ------------------------ mapper 配置 ------------------------ */

        //设置生成 mapper
        globalConfig.setMapperGenerateEnable(true);
        //mapper包
        globalConfig.setMapperPackage("com.pandar.mapper");
        //生成mapper xml文件
        globalConfig.enableMapperXml();
        //生成mapper.xml路径
        globalConfig.setMapperXmlPath(projectPath + "/generator/resources/mapper");

        /* ------------------------ 生成哪些表 ------------------------ */
        globalConfig.setGenerateTable(
                "sys_config",
                "sys_dept",
                "sys_dict",
                "sys_file",
                "sys_notice",
                "sys_perms",
                "sys_post",
                "sys_role",
                "sys_role_dept",
                "sys_role_perm",
                "sys_storage",
                "sys_tenant",
                "sys_tenant_package",
                "sys_tenant_package_perm",
                "sys_user",
                "sys_user_dept",
                "sys_user_post",
                "sys_user_role"
        );
        //是否生成service和controller
        globalConfig.setServiceGenerateEnable(true);
        globalConfig.setServiceImplGenerateEnable(true);
        globalConfig.setControllerGenerateEnable(true);

        //通过 datasource 和 globalConfig 创建代码生成器
        Generator generatorHandler = new Generator(dataSource, globalConfig);
        generatorHandler.generate();
    }

}
