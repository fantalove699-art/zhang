# 健康管理平台 - 项目代码维基文档

## 1. 项目概述

本项目是一个基于 **Spring + Hibernate + Jetty** 的健康管理平台后端服务，提供健康数据采集、分析、评估、报告等核心功能，支持微信公众号集成、设备数据接入、支付宝支付等业务场景。

### 1.1 技术栈

| 分类 | 技术 | 版本 | 说明 |
|------|------|------|------|
| 语言 | Java | 1.8 | 开发语言 |
| 框架 | Spring Framework | 4.1.9.RELEASE | 核心框架 |
| ORM | Hibernate | 4.3.11.Final | 数据访问层 |
| 数据库 | MySQL | 5.1.39 | 关系型数据库 |
| 缓存 | J2Cache + Redis | - | 两级缓存 |
| 消息队列 | ActiveMQ | 5.7.0 | 异步消息 |
| 定时任务 | Quartz | 2.2.3 | 任务调度 |
| RPC | Dubbo | 2.6.4 | 分布式服务调用 |
| Web容器 | Jetty | 8.1.22.v20160922 | 嵌入式服务器 |
| 模板引擎 | Beetl | 2.7.12 | 页面渲染 |

---

## 2. 项目架构

### 2.1 整体架构

```
┌─────────────────────────────────────────────────────────────────┐
│                        前端层 (Frontend)                        │
│  Web页面 / 移动端App / 微信公众号 / 健康设备                      │
└───────────────────────────┬─────────────────────────────────────┘
                            │ HTTP / Dubbo / MQTT
                            ▼
┌─────────────────────────────────────────────────────────────────┐
│                        控制层 (Controller)                      │
│  module/controller / weixin/controller / colormed/controller    │
└───────────────────────────┬─────────────────────────────────────┘
                            │ 调用
                            ▼
┌─────────────────────────────────────────────────────────────────┐
│                        服务层 (Service)                         │
│  module/service / dubbo/service / common/service               │
└───────────────────────────┬─────────────────────────────────────┘
                            │ 持久化
                            ▼
┌─────────────────────────────────────────────────────────────────┐
│                        数据访问层 (DAO)                         │
│  module/dao / colormed/dao / common/persistence                │
└───────────────────────────┬─────────────────────────────────────┘
                            │ JDBC
                            ▼
┌─────────────────────────────────────────────────────────────────┐
│                      数据库层 (Database)                        │
│                     MySQL + Redis + ActiveMQ                    │
└─────────────────────────────────────────────────────────────────┘
```

### 2.2 模块划分

| 模块 | 包路径 | 职责说明 |
|------|--------|----------|
| **common** | `com.hnky.health.common` | 通用工具、基础类、配置常量 |
| **module** | `com.hnky.health.module` | 核心业务模块（控制器、服务、DAO、实体） |
| **weixin** | `com.hnky.health.weixin` | 微信公众号、支付相关功能 |
| **dubbo** | `com.hnky.health.dubbo` | Dubbo远程服务实现 |
| **colormed** | `com.hnky.health.colormed` | 体检设备数据处理 |
| **iot** | `com.hnky.health.iot` | IoT设备服务端 |
| **ds** | `com.hnky.health.ds` | 第三方对接模块 |
| **xiekang** | `com.hnky.health.xiekang` | 协康设备对接 |
| **alipay** | `com.alipay` | 支付宝支付集成 |

---

## 3. 核心类与功能

### 3.1 基础组件

#### 3.1.1 BaseDao - DAO支持类

**位置**: `src/main/java/com/hnky/health/common/persistence/BaseDao.java`

**核心功能**:
- 封装Hibernate Session操作
- 提供通用CRUD方法
- 支持HQL/SQL分页查询
- 集成Hibernate Search全文检索

**关键方法**:

| 方法名 | 功能说明 | 参数 | 返回值 |
|--------|----------|------|--------|
| `getSession()` | 获取Hibernate Session | 无 | `Session` |
| `get(Serializable id)` | 根据ID获取实体 | `id`: 主键 | `T` |
| `save(T t)` | 保存/更新实体 | `t`: 实体对象 | void |
| `find(Page<E> page, String qlString)` | HQL分页查询 | `page`:分页对象, `qlString`:HQL语句 | `Page<E>` |
| `findBySql(Page<E> page, String sqlString)` | SQL分页查询 | `page`:分页对象, `sqlString`:SQL语句 | `Page<E>` |
| `search(Page<T> page, Query query, Sort sort)` | 全文检索 | `page`:分页对象, `query`:Lucene查询 | `Page<T>` |

**设计特点**:
- 使用泛型实现类型安全
- 支持HQL和原生SQL两种查询方式
- 自动处理分页逻辑
- 集成全文检索能力

---

#### 3.1.2 BaseService - Service基类

**位置**: `src/main/java/com/hnky/health/common/service/BaseService.java`

**核心功能**:
- 提供日志记录能力
- 封装缓存访问逻辑
- 定义数据获取抽象方法

**关键方法**:

| 方法名 | 功能说明 | 参数 | 返回值 |
|--------|----------|------|--------|
| `getCache(String key)` | 获取缓存数据，缓存不存在则从数据库读取 | `key`:缓存键 | `T` |
| `getData(String key)` | 抽象方法，子类实现从数据库读取数据 | `key`:数据标识 | `T` |

**设计特点**:
- 缓存与数据库访问解耦
- 缓存异常时自动降级到数据库
- 模板方法模式，子类只需实现`getData()`

---

#### 3.1.3 BaseController - Controller基类

**位置**: `src/main/java/com/hnky/health/common/web/BaseController.java`

**核心功能**:
- 封装响应结果
- 提供参数验证
- 统一异常处理
- 数据绑定初始化

**关键方法**:

| 方法名 | 功能说明 | 参数 | 返回值 |
|--------|----------|------|--------|
| `success()` | 返回成功响应 | 无 | `Result` |
| `success(Object object)` | 返回带数据的成功响应 | `object`:响应数据 | `Result` |
| `error(String message)` | 返回错误响应 | `message`:错误信息 | `Result` |
| `beanValidator(Object object, Class<?>... groups)` | Bean参数验证 | `object`:待验证对象 | void |
| `toJsonString(HttpServletResponse response, Object object)` | 返回JSON响应 | `response`:响应对象, `object`:数据 | void |
| `validate()` | 判断是否为一体机请求 | 无 | `boolean` |
| `getTenant()` | 获取租户标识 | 无 | `String` |

---

### 3.2 启动组件

#### 3.2.1 JettyFactory - Jetty服务器工厂

**位置**: `src/test/java/JettyFactory.java`

**核心功能**:
- 创建嵌入式Jetty服务器
- 配置Web应用上下文
- 支持热重载功能

**关键方法**:

| 方法名 | 功能说明 | 参数 | 返回值 |
|--------|----------|------|--------|
| `createServerInSource(int port, String contextPath)` | 创建开发环境服务器 | `port`:端口, `contextPath`:上下文路径 | `Server` |
| `setTldJarNames(Server server, String... jarNames)` | 设置TLD文件所在Jar包 | `server`:服务器实例, `jarNames`:Jar包名 | void |
| `reloadContext(Server server)` | 热重载Web应用 | `server`:服务器实例 | void |

---

#### 3.2.2 StartServer - 服务器启动类

**位置**: `src/test/java/StartServer.java`

**核心功能**:
- 项目启动入口
- 配置服务器参数
- 提供热重载交互

**配置常量**:

| 常量名 | 值 | 说明 |
|--------|-----|------|
| `PORT` | 8080 | 服务端口 |
| `CONTEXT` | `/health` | 上下文路径 |
| `TLD_JAR_NAMES` | `["spring-webmvc"]` | TLD Jar包列表 |

---

### 3.3 核心业务模块

#### 3.3.1 健康数据管理

**涉及Controller**:

| Controller | 功能说明 |
|------------|----------|
| `BloodPressureController` | 血压数据管理 |
| `BloodGlucoseController` | 血糖数据管理 |
| `BloodLipidController` | 血脂数据管理 |
| `BloodOxygenController` | 血氧数据管理 |
| `HeartRateController` | 心率数据管理 |
| `TemperatureController` | 体温数据管理 |
| `WeightController` | 体重数据管理 |
| `UricAcidController` | 尿酸数据管理 |
| `SleepController` | 睡眠数据管理 |
| `SportController` | 运动数据管理 |

**数据采集流程图**:

```
健康设备 → GrDataUploadController → GrInfoCreateDto → 
MeasureDataService → BaseDao → MySQL
```

---

#### 3.3.2 健康评估模块

**涉及Service**:

| Service | 功能说明 |
|---------|----------|
| `EvaluationService` | 综合评估服务 |
| `HealthAssessService` | 健康评估服务 |
| `DiseaseRiskService` | 疾病风险评估 |
| `HealthScoreService` | 健康评分服务 |

**评估类型**:
- 高血压风险评估 (`EvaluationHypertensionDao`)
- 糖尿病风险评估 (`EvaluationGlycuresisDao`)
- 冠心病风险评估 (`EvaluationChdDao`)
- 脑卒中风险评估 (`EvaluationStrokeDao`)
- 骨质疏松评估 (`EvaluationOsteoporosisDao`)
- 癌症风险评估 (`EvaluationCancerCharacterDao`)

---

#### 3.3.3 用户管理模块

**涉及Controller**:

| Controller | 功能说明 |
|------------|----------|
| `UserController` | 用户基本信息管理 |
| `LoginController` | 用户登录认证 |
| `SmsCheckController` | 短信验证码 |

**涉及Service**:

| Service | 功能说明 |
|---------|----------|
| `UserService` | 用户核心服务 |
| `SmsCaptchaService` | 短信验证码服务 |
| `DeviceTokenService` | 设备Token管理 |

---

#### 3.3.4 微信集成模块

**配置类**:

| Config类 | 说明 |
|----------|------|
| `WxConfig` | 微信基础配置 |
| `WxMpConfig` | 公众号配置 |
| `WxCyhlConfig` | 朝阳护理配置 |
| `WxEproConfig` | 鄂尔多斯配置 |
| `WxYdjkConfig` | 移动健康配置 |
| `WxXpzyConfig` | 新平中医配置 |
| `WxZzgjConfig` | 郑州骨科配置 |

**Handler类**:

| Handler | 功能说明 |
|---------|----------|
| `SubscribeHandler` | 关注事件处理 |
| `UnsubscribeHandler` | 取消关注处理 |
| `MenuHandler` | 菜单点击处理 |
| `TextHandler` | 文本消息处理 |
| `ScanHandler` | 扫码事件处理 |

---

#### 3.3.5 Dubbo服务模块

**服务实现类**:

| ServiceImpl | 接口说明 |
|-------------|----------|
| `UserApiServiceImpl` | 用户API服务 |
| `MeasureServiceImpl` | 测量数据服务 |
| `ReportServiceImpl` | 报告服务 |
| `EvaluationServiceImpl` | 评估服务 |
| `SysOrgServerServiceImpl` | 组织服务 |
| `UserMeasureStatisticsServiceImpl` | 用户测量统计服务 |

---

### 3.4 工具类

**常用工具**:

| 工具类 | 功能说明 |
|--------|----------|
| `JsonMapper` | JSON序列化/反序列化 |
| `DateUtils` | 日期处理工具 |
| `HttpTools` | HTTP请求工具 |
| `JedisUtils` | Redis操作工具 |
| `CacheUtils` | 缓存操作工具 |
| `PushUtils` | 消息推送工具 |
| `CommonUtil` | 通用工具方法 |
| `BeanValidators` | Bean验证工具 |
| `IdCardUtils` | 身份证处理工具 |
| `SpringBeanUtils` | Spring Bean工具 |

---

## 4. 数据库与数据结构

### 4.1 实体基类

#### 4.1.1 IdEntity

**位置**: `src/main/java/com/hnky/health/common/persistence/IdEntity.java`

**字段**:
- `id`: 主键ID
- `createDate`: 创建时间
- `updateDate`: 更新时间
- `delFlag`: 删除标记（0-正常，1-删除）

#### 4.1.2 VipIdEntity

**位置**: `src/main/java/com/hnky/health/common/persistence/VipIdEntity.java`

**字段**:
- 继承`IdEntity`
- `userId`: 用户ID

---

### 4.2 核心实体

**健康数据实体**:

| 实体类 | 表名 | 说明 |
|--------|------|------|
| `BloodPressure` | `health_blood_pressure` | 血压数据 |
| `BloodGlucose` | `health_blood_glucose` | 血糖数据 |
| `BloodLipid` | `health_blood_lipid` | 血脂数据 |
| `BloodOxygen` | `health_blood_oxygen` | 血氧数据 |
| `Ecg` | `health_ecg` | 心电图数据 |
| `Sleep` | `health_sleep` | 睡眠数据 |
| `Sport` | `health_sport` | 运动数据 |
| `Weight` | `health_weight` | 体重数据 |
| `Temperature` | `health_temperature` | 体温数据 |
| `UricAcid` | `health_uric_acid` | 尿酸数据 |

**评估实体**:

| 实体类 | 说明 |
|--------|------|
| `EvaluationHypertension` | 高血压评估 |
| `EvaluationGlycuresis` | 糖尿病评估 |
| `EvaluationChd` | 冠心病评估 |
| `EvaluationStroke` | 脑卒中评估 |

---

## 5. 配置与运行

### 5.1 配置文件结构

```
src/main/resources/
├── spring-context.xml          # Spring核心配置
├── spring-mvc.xml              # Spring MVC配置
├── spring-context-jedis.xml    # Redis配置
├── spring-context-jms.xml      # JMS配置
├── spring-context-task.xml     # 任务调度配置
├── spring-dubbo.xml            # Dubbo配置
├── jetty/
│   └── webdefault.xml          # Jetty配置
├── system/
│   └── SystemUrl.xml           # 系统URL配置
├── weixin/
│   ├── wx.properties           # 微信基础配置
│   ├── wx-cyhl.properties      # 朝阳护理微信配置
│   ├── wx-epro.properties      # 鄂尔多斯微信配置
│   ├── wx-xpzy.properties      # 新平中医微信配置
│   ├── wx-ydjk.properties      # 移动健康微信配置
│   ├── wx-zzgj.properties      # 郑州骨科微信配置
│   └── wx-test.properties      # 测试环境微信配置
├── logback.xml                 # 日志配置
├── log4j.properties            # Log4j配置
├── j2cache.properties          # J2Cache配置
├── caffeine.properties         # Caffeine本地缓存配置
├── dubbo.properties            # Dubbo配置
├── quartz.properties           # Quartz配置
└── kaiyuncare.properties       # 凯云护理配置
```

### 5.2 启动方式

#### 5.2.1 开发态运行

**方法一：运行StartServer类**

```java
// 直接运行 src/test/java/StartServer.java
public static void main(String[] args) throws Exception {
    Server server = JettyFactory.createServerInSource(PORT, CONTEXT);
    server.setAttribute("org.eclipse.jetty.server.Request.maxFormContentSize", -1);
    JettyFactory.setTldJarNames(server, TLD_JAR_NAMES);
    server.start();
    System.out.println("Server running at http://localhost:" + PORT + CONTEXT);
}
```

**方法二：Maven Jetty插件**

```bash
mvn jetty:run -Djetty.port=8080
```

**方法三：Maven Exec插件**

```bash
mvn exec:java -Dexec.mainClass="StartServer" -Dexec.classpathScope="test"
```

#### 5.2.2 打包部署

```bash
# 编译打包
mvn clean package -DskipTests

# 部署到外部容器（如Tomcat）
# 将 target/health.war 复制到 Tomcat/webapps 目录
```

### 5.3 访问地址

| 环境 | 地址 | 端口 |
|------|------|------|
| 开发环境 | http://localhost:8080/health | 8080 |
| 管理后台 | http://localhost:8080/health/admin | 8080 |

---

## 6. 依赖关系

### 6.1 核心依赖

| 依赖 | GroupId | ArtifactId | 版本 |
|------|---------|------------|------|
| Spring Context | org.springframework | spring-context | 4.1.9.RELEASE |
| Spring WebMVC | org.springframework | spring-webmvc | 4.1.9.RELEASE |
| Spring ORM | org.springframework | spring-orm | 4.1.9.RELEASE |
| Spring JMS | org.springframework | spring-jms | 4.1.9.RELEASE |
| Hibernate Core | org.hibernate | hibernate-core | 4.3.11.Final |
| Hibernate Search | org.hibernate | hibernate-search-orm | 5.3.0.Final |
| Druid | com.alibaba | druid | 1.0.29 |
| Jetty Webapp | org.eclipse.jetty.aggregate | jetty-webapp | 8.1.22.v20160922 |
| Dubbo | com.alibaba | dubbo | 2.6.4 |
| Redis | redis.clients | jedis | 2.9.0 |
| ActiveMQ | org.apache.activemq | activemq-core | 5.7.0 |
| Quartz | org.quartz-scheduler | quartz | 2.2.3 |
| Beetl | com.ibeetl | beetl | 2.7.12 |
| Fastjson | com.alibaba | fastjson | 1.2.33 |
| Guava | com.google.guava | guava | 20.0 |
| HttpClient | org.apache.httpcomponents | httpclient | 4.5.3 |

### 6.2 支付相关依赖

| 依赖 | GroupId | ArtifactId | 版本 |
|------|---------|------------|------|
| 微信公众号SDK | com.github.binarywang | weixin-java-mp | 2.7.7 |
| 微信支付SDK | com.github.binarywang | weixin-java-pay | 2.7.7 |
| 阿里云SDK核心 | com.aliyun | aliyun-java-sdk-core | 3.7.1 |
| 阿里云绿网 | com.aliyun | aliyun-java-sdk-green | 2.6.0 |
| 极光推送 | cn.jpush.api | jpush-client | 3.2.9 |

---

## 7. 安全与权限

### 7.1 安全组件

| 组件 | 功能说明 |
|------|----------|
| `TextAntispamDetection` | 文本反垃圾检测（阿里云绿网） |
| `Digests` | 消息摘要工具（MD5/SHA） |
| `Encodes` | 编码工具（Base64/URL编码） |

### 7.2 数据验证

**自定义校验注解**:

| 注解 | 说明 |
|------|------|
| `@Idcard` | 身份证号校验 |

**校验组**:

| 校验组 | 用途 |
|--------|------|
| `Group1` | 第一组校验 |
| `Group2` | 第二组校验 |
| `Group3` | 第三组校验 |
| `Group4` | 第四组校验 |

---

## 8. 扩展与集成

### 8.1 设备接入

**支持的设备类型**:

| 设备类型 | 模块 | 说明 |
|----------|------|------|
| 体检一体机 | colormed | 体检设备数据接入 |
| IoT设备 | iot | 物联网设备接入 |
| 协康设备 | xiekang | 第三方设备对接 |

**设备数据流程**:

```
设备 → Controller → DTO → Service → DAO → Database
                              ↓
                         Redis缓存
```

### 8.2 第三方集成

| 第三方服务 | 集成方式 | 说明 |
|------------|----------|------|
| 微信公众号 | weixin-java-mp | 消息处理、菜单管理 |
| 微信支付 | weixin-java-pay | 支付、退款、对账 |
| 支付宝 | 自研SDK | 签名、支付 |
| 阿里云IoT | iot-client-message | 设备消息通信 |
| 极光推送 | jpush-client | 消息推送 |

---

## 9. 代码规范

### 9.1 命名规范

| 类型 | 规范 | 示例 |
|------|------|------|
| 类名 | 大驼峰 | `UserController`, `BloodPressureService` |
| 方法名 | 小驼峰 | `getUserById`, `saveBloodPressure` |
| 变量名 | 小驼峰 | `userId`, `bloodPressureList` |
| 常量名 | 全大写+下划线 | `MAX_PAGE_SIZE`, `DEFAULT_PORT` |
| 包名 | 全小写 | `com.hnky.health.module.controller` |

### 9.2 目录结构规范

```
src/main/java/com/hnky/health/
├── module/
│   ├── controller/     # REST API控制器
│   ├── service/        # 业务逻辑服务
│   ├── dao/            # 数据访问对象
│   ├── entity/         # 数据库实体
│   ├── dto/            # 数据传输对象
│   ├── util/           # 模块工具类
│   ├── jms/            # 消息队列
│   └── quartz/         # 定时任务
├── common/
│   ├── persistence/     # 持久化基础类
│   ├── service/         # 服务基础类
│   ├── web/            # Web基础类
│   ├── util/           # 通用工具类
│   ├── constant/       # 常量定义
│   ├── config/         # 配置类
│   ├── listener/       # 监听器
│   ├── json/           # JSON处理
│   └── rest/           # REST相关
```

---

## 10. 总结

本项目是一个功能完整的健康管理平台后端服务，具备以下特点：

1. **技术栈成熟**：基于Spring 4.x + Hibernate 4.x，稳定可靠
2. **模块化设计**：清晰的模块划分，便于维护和扩展
3. **多端支持**：支持Web、移动端、微信公众号、IoT设备接入
4. **缓存优化**：集成J2Cache实现两级缓存
5. **分布式支持**：通过Dubbo实现服务化拆分
6. **消息队列**：通过ActiveMQ实现异步处理
7. **定时任务**：通过Quartz实现任务调度

**核心业务能力**：
- 健康数据采集与管理
- 多维度健康评估
- 个性化健康报告
- 微信公众号集成
- 第三方支付支持
- 设备数据接入