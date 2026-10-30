# 二手车交易管理系统

基于 Spring Boot + Vue 3 + MySQL 的二手车交易管理系统，包含用户端与管理员端，支持注册登录、二手车展示、筛选检索、详情查看、发布收车信息、在线留言咨询、后台管理等功能。

## 功能概览

### 用户端
- 用户注册与登录
- 二手车信息浏览
- 车辆检索与筛选
- 车辆详情查看
- 发布/管理二手车信息
- 在线留言咨询

### 管理端
- 用户管理
- 二手车信息发布/编辑/下架
- 留言管理
- 车辆分类管理
- 数据统计概览

## 技术栈
- 后端：Spring Boot 3 / Java 17 / Spring Data JPA / MySQL
- 前端：Vue 3 / Vite / Element Plus / Axios
- 数据库：MySQL 8.0+

## 运行方式

### 1. 导入数据库
```bash
mysql -u root -p < sql/init.sql
```

### 2. 启动后端
```bash
cd backend
mvn clean package
java -jar target/used-car-system-0.0.1-SNAPSHOT.jar
```

后端默认启动在：
- http://localhost:8080

### 3. 启动前端
```bash
cd frontend
npm install
npm run dev
```

前端默认启动在：
- http://localhost:5173

## 默认账号
- 管理员：admin / 123456
- 用户：demo / 123456

## 项目结构
```text
.
├── backend
│   ├── pom.xml
│   └── src
├── frontend
│   ├── package.json
│   └── src
├── sql
│   └── init.sql
├── docs
│   ├── requirements-analysis.md
│   ├── outline-design.md
│   ├── detailed-design.md
│   └── report.md
├── README.md
└── .gitignore
```

## 说明
本项目为毕业设计/综合训练项目示例，可作为后端接口、数据库设计、前后端联调和文档编写的参考实现。
