## 项目简介

基于SpringBoot3+Vue3的前后端分离的单体多租户脚手架，具备一些常用的基础功能。

管理员账号/密码： `admin`/`111111`  
租户账号/密码：`dongdong`/`111111`

## 技术选型

### 前端

| 名称          | 版本     | 说明                      |
|-------------|--------|-------------------------|
| Vue         | 3.2.47 | 用于构建用户界面的 JavaScript 框架 |
| Vite        | 4.3.2  | 前端构建工具                  |
| Arco-Design | 2.45.3 | 字节出的 Vue3 UI 组件库        |
| Windicss    | 3.5.6  | CSS 框架                  |
| Vue-Router  | 4.1.6  | Vue官方路由                 |
| Vueuse      | 10.1.2 | 基于Composition API的实用函数库 |
| Axios       | 1.4.0  | 基于Promise的HTTP客户端       |
| Pinia       | 2.0.36 | Vue 状态管理                |
| Vue-Cropper | 1.0.9  | 图片裁剪                    |
| Nprogress   | 0.2.0  | 进度条                     |
| Tinymce     | 6.6.0  | 富文本编辑器                  |

### 后端

| 名称           | 版本     | 说明                      |
|--------------|--------|-------------------------|
| Java         | 17     | 无需多言                    |
| SpringBoot   | 3.1.1  | Java开发框架                |
| Redisson     | 3.36.0 | Redis Java客户端           |
| Mysql        | 8.0.33 | Mysql数据库驱动              |
| Mybatis-flex | 1.9.7  | ORM框架                   |
| Hutool       | 5.8.32 | Java工具库                 |
| Satoken      | 1.39.0 | Java权限认证框架              |
| Knife4j      | 4.1.0  | Swagger2和OpenAPI3增强解决方案 |
| Mica-xss     | 3.3.2  | xss防护                   |

## 中间件

| 名称    | 版本     | 说明       |
|-------|--------|----------|
| mysql | 8.0.24 | 关系型数据库   |
| redis | 7.2.4  | NoSQL数据库 |

## 项目目录
```
com.pandar     
│    └── java
│       └── common                        // 公共资源
│             └── base                    // 基类
│             └── constant                // 常量
│             └── enums                   // 枚举
│             └── exception               // 异常
│             └── group                   // 自定义分组  
│       └── config                        // 配置
│             └── async                   // 线程池配置
│             └── convert                 // 自定义数据转换
│             └── dict                    // 自定义字典数据
│             └── file                    // 自定义文件处理
│             └── mybatis                 // mybatis-flex配置
│             └── redis                   // redis配置
│             └── resubmit                // 自定义注解防重复请求
│             └── satoken                 // sa-token配置
│             └── swagger                 // swagger配置
│             └── tenant                  // 自定义注解忽略多租户
│             └── trace                   // 链路追踪拦截器
│       └── controller                    // controller层
│       └── domain                        // 实体类
│             └── dto                     // dto
│             └── entity                  // entity
│             └── vo                      // vo
│       └── manager                       // manager层公共处理
│       └── maooer                        // mapper层
│       └── service                       // service层
│       └── utils                         // 工具类
│    └── resources
│       └── mapper                        // mybatis mapper.xml
│       └── application.yml               // yml配置
│       └── application-dev.yml           // yml配置
│       └── logback.xml                   // 日志配置
├── .gitignore                            // git忽略文件和目录配置
├── README.md                             // 项目说明文件
├── pom.xml                               // pom文件
```
![](https://apebbs.cn/resources/%E4%B8%8D%E8%A6%81%E8%AE%B2%E4%BB%80%E4%B9%88%E5%BA%95%E5%B1%82%E5%8E%9F%E7%90%86.png)