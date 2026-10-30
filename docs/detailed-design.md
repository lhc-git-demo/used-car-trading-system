# 详细设计

## 1. 数据库表设计
### 1.1 users 表
字段：id, username, password, nickname, phone, email, role, enabled, created_at, updated_at

### 1.2 categories 表
字段：id, name, parent_id, sort_order, created_at, updated_at

### 1.3 cars 表
字段：id, title, brand, model, year, mileage, price, color, fuel_type, description, image_url, status, category_id, owner_id, created_at, updated_at

### 1.4 messages 表
字段：id, car_id, user_id, content, reply, status, contact_phone, created_at, updated_at

## 2. 接口设计
### 用户相关
- POST /api/auth/register
- POST /api/auth/login
- GET /api/users

### 车辆相关
- GET /api/cars
- GET /api/cars/{id}
- POST /api/cars
- PUT /api/cars/{id}
- DELETE /api/cars/{id}

### 分类相关
- GET /api/categories
- POST /api/categories

### 留言相关
- GET /api/messages
- POST /api/messages

## 3. 业务流程
1. 用户注册登录
2. 用户发布二手车信息
3. 管理员审核并上架
4. 用户浏览车辆并筛选
5. 用户留言咨询
6. 管理员处理留言并回复
