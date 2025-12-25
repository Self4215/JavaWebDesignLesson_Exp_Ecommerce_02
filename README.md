# 中小型电商系统

> 23网络工程 尹智灏

本项目为中小型电商系统的 Docker 部署方案，包含 MySQL 数据库和 Java Web 应用服务。 通过 Docker Compose 进行编排，简化部署流程。

---

## 运行步骤

1. **拉取镜像**

   在终端中运行以下命令：
```bash
docker pull ghcr.io/self4215/javawebdesignlesson_exp_ecommerce_02:latest
```

2. **创建配置文件**

(1) **创建 `.env` 文件**

   为了安全起见，我们将敏感配置放入环境变量中。

   在任意目录下创建一个名为 `.env` 的文件，并填入以下内容（或者参考源码内的 [`.env.example`](.env.example) 文件）：
```dotenv
# 务必在生产环境中修改以下密码

# Database Configuration
MYSQL_DATABASE=ecommerce_db
MYSQL_ROOT_PASSWORD=your_secure_password

# Application Database Connection
DB_HOST=mysql
DB_PORT=3306
DB_USERNAME=root
DB_PASSWORD=your_secure_password
```

(2) **第二步：创建 `docker-compose.yml` 文件**

   在同一目录下创建一个名为 `docker-compose.yml` 的文件，并填入以下内容：
```yaml
version: '3.8'

services:
  mysql:
    image: mysql:8.4
    container_name: ecommerce-mysql
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: ${MYSQL_ROOT_PASSWORD}
      MYSQL_DATABASE: ${MYSQL_DATABASE}
    volumes:
      - mysql-data:/var/lib/mysql
    networks:
      - ecommerce-network

  app:
    image: ghcr.io/self4215/javawebdesignlesson_exp_ecommerce_02:latest 
    container_name: ecommerce-app
    restart: always
    ports:
      - "8080:8080"
    environment:
      - DB_HOST=${DB_HOST}
      - DB_PASSWORD=${DB_PASSWORD}
    depends_on:
      - mysql
    networks:
      - ecommerce-network

networks:
  ecommerce-network:

volumes:
  mysql-data:
```

3. **启动服务**

   在该目录下，运行以下命令：
```bash
docker-compose up -d
```

4. **访问项目**

   等待服务启动完成后，访问地址：`http://localhost:8080`

---

## 许可证

本项目采用 [MIT 许可证](LICENSE)。
