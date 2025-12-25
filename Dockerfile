# 阶段1：构建 Jar 包（多阶段构建，减小镜像体积）
FROM maven:3.9-eclipse-temurin-8-alpine AS build
# 设置工作目录
WORKDIR /app
# 复制 pom.xml 和源码
COPY pom.xml .
COPY src ./src
# 打包生成 Jar 包（跳过测试）
# 使用 --mount 挂载 Maven 本地仓库缓存，target 指向 Maven 默认的本地仓库路径
# 这样可以加快构建速度，避免每次修改代码后构建时都重新下载 Maven 依赖
# 执行命令时需要确保 Docker 开启了 BuildKit，不过据说现在较新版本的 Docker 默认开启
RUN --mount=type=cache,target=/root/.m2 \
    mvn clean package -DskipTests

# 阶段2：运行 Jar 包
FROM eclipse-temurin:8-jdk-alpine
# 设置工作目录
WORKDIR /app
# 从构建阶段复制 Jar 包到当前镜像
COPY --from=build /app/target/*.jar app.jar
# 暴露端口（与 application.properties 中的 server.port 一致）
EXPOSE 8080
# 启动命令
ENTRYPOINT ["java", "-jar", "app.jar"]