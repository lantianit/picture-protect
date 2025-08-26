# 配置文件说明

## 配置文件加载机制

Spring Boot 按照以下顺序加载配置文件：

1. `application.yml` (主配置文件)
2. `application-{profile}.yml` (环境特定配置文件)

## 当前配置结构

```
src/main/resources/
├── application.yml                    # 主配置文件
├── application-dev.yml               # 开发环境配置（包含敏感信息）
└── application-dev.yml.template      # 配置模板
```

## 如何确保配置正确加载

### 1. 激活开发环境配置

在主配置文件 `application.yml` 中已经设置了：

```yaml
spring:
  profiles:
    active: dev  # 激活开发环境配置
```

### 2. 启动应用

应用启动时会自动加载：
- `application.yml` (基础配置)
- `application-dev.yml` (开发环境配置，会覆盖基础配置)

### 3. 验证配置加载

应用启动时，`ConfigurationValidator` 会自动验证配置是否正确加载，控制台会显示：

```
=== 配置验证开始 ===
验证腾讯云COS配置...
✅ COS Host: https://yupi-1318253552.cos.ap-guangzhou.myqcloud.com
✅ COS SecretId: AKID****HQN9
✅ COS SecretKey: bBf2****2Vk
✅ COS Bucket: yupi-1318253552
验证阿里云AI配置...
✅ 阿里云AI API Key: sk-a5****b589
=== 配置验证完成 ===
```

## 启动方式

### 方式1：IDE启动
直接在IDE中运行主类 `YuPictureBackendApplication`

### 方式2：命令行启动
```bash
# 使用Maven
mvn spring-boot:run

# 或者打包后运行
mvn clean package
java -jar target/yu-picture-backend-ddd-0.0.1-SNAPSHOT.jar
```

### 方式3：指定环境变量
```bash
# 可以通过环境变量指定profile
export SPRING_PROFILES_ACTIVE=dev
java -jar target/yu-picture-backend-ddd-0.0.1-SNAPSHOT.jar
```

## 故障排除

### 问题1：配置未加载
**现象**：启动时显示配置验证失败
**解决**：
1. 检查 `application-dev.yml` 文件是否存在
2. 检查文件路径是否正确
3. 检查YAML语法是否正确

### 问题2：配置被覆盖
**现象**：配置值不是预期的
**解决**：
1. 检查 `application.yml` 中是否有相同的配置项
2. 检查环境变量是否设置了相同的配置

### 问题3：敏感信息泄露
**现象**：日志中显示完整的密钥
**解决**：
1. 检查 `ConfigurationValidator` 是否正确掩码了敏感信息
2. 确保生产环境使用环境变量

## 环境配置

### 开发环境 (dev)
- 配置文件：`application-dev.yml`
- 激活方式：`spring.profiles.active=dev`

### 生产环境 (prod)
- 配置文件：`application-prod.yml`
- 激活方式：`spring.profiles.active=prod`
- 推荐使用环境变量

### 测试环境 (test)
- 配置文件：`application-test.yml`
- 激活方式：`spring.profiles.active=test`

## 安全提醒

1. **不要提交** `application-dev.yml` 到git
2. **定期更换** API密钥和密码
3. **生产环境** 使用环境变量管理敏感信息
4. **备份配置** 到安全的地方
