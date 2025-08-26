# 📸 Photo Gallery Microservices

## ✅ Giới thiệu

Hệ thống **Photo Gallery** được xây dựng bằng **Kotlin + Spring Boot + Gradle**, triển khai theo kiến trúc **Microservice**, nhằm quản lý hình ảnh/video giống iOS Gallery.
Hệ thống cho phép:

* Hiển thị ảnh/video từ thư mục mount (không lưu file).
* Quản lý metadata: Favorite, Tag, Collection.
* Đăng ký/Đăng nhập, phân quyền người dùng (RBAC).
* Chia sẻ bộ sưu tập cho người khác với quyền `VIEW` hoặc `EDIT`.
* Giao tiếp giữa các service qua **REST API**, sử dụng **JWT** cho xác thực.

---

## ✅ Kiến trúc hệ thống

```
[ API Gateway ] ---> [ Auth Service ] ---> [ MySQL ]
                   |-> [ User Service ] ---> [ MySQL ]
                   |-> [ Media Service ]
                   |-> [ Metadata Service ] ---> [ MySQL ]
                   |-> [ Sharing Service ] ---> [ MySQL ]
```

* **API Gateway**: Định tuyến request đến các service.
* **Auth Service**: Đăng ký, đăng nhập, sinh JWT, phân quyền.
* **User Service**: Quản lý thông tin người dùng.
* **Media Service**: Đọc ảnh/video từ volume mount.
* **Metadata Service**: Quản lý Favorite, Tag, Collection.
* **Sharing Service**: Chia sẻ bộ sưu tập cho user khác.

---

## ✅ Công nghệ sử dụng

* **Ngôn ngữ**: Kotlin (JVM 17)
* **Framework**: Spring Boot 3.x
* **Build tool**: Gradle (Kotlin DSL)
* **Database**: MySQL cho các service (mỗi service 1 DB)
* **API Documentation**: Swagger (springdoc-openapi)
* **Authentication**: JWT + Spring Security
* **Containerization**: Docker
* **API Gateway**: Spring Cloud Gateway
* **Optional**: Docker Compose để chạy toàn bộ hệ thống

---

## ✅ Các service

| Service         | Port | Chức năng                           |
| --------------- | ---- | ----------------------------------- |
| **API Gateway** | 8080 | Route request đến các service khác  |
| **Auth**        | 8081 | Đăng nhập, đăng ký, JWT, Role       |
| **User**        | 8082 | Quản lý thông tin user              |
| **Media**       | 8083 | Đọc file ảnh/video từ thư mục mount |
| **Metadata**    | 8084 | Quản lý tag, favorite, collection   |
| **Sharing**     | 8085 | Chia sẻ collection với user khác    |

---

## ✅ Chức năng chính

* **Auth Service**

  * Đăng ký, đăng nhập
  * Sinh JWT, phân quyền (`ADMIN`, `USER`, `VIEWER`)
* **User Service**

  * CRUD user profile
* **Media Service**

  * Liệt kê ảnh/video từ thư mục mount
* **Metadata Service**

  * Quản lý tag, favorite
  * Tạo và quản lý collection
* **Sharing Service**

  * Chia sẻ collection cho user khác
  * Gán quyền `VIEW` hoặc `EDIT`

---

## ✅ Cấu trúc multi-repo

```
photo-gallery/
├── api-gateway/
├── auth-service/
├── user-service/
├── media-service/
├── metadata-service/
├── sharing-service/
└── common-library/ (optional: DTO, utils)
```

---

## ✅ Cách chạy dự án

### **1. Chạy 1 service (ví dụ: Auth Service)**

```bash
cd auth-service
./gradlew bootRun
```

Swagger UI: `http://localhost:8081/swagger-ui.html`

### **2. Build Docker image**

```bash
./gradlew bootJar
docker build -t your-org/auth-service:1.0 .
```

### **3. Chạy bằng Docker**

```bash
docker run -d -p 8081:8081 --name auth-service your-org/auth-service:1.0
```

### **4. Chạy tất cả service bằng Docker Compose**

File `docker-compose.yml` sẽ được cung cấp ở repo `deployment`:

```bash
docker-compose up -d
```

---

## ✅ API Documentation

* Mỗi service tích hợp **Swagger UI**:

  * Auth Service: `http://localhost:8081/swagger-ui.html`
  * User Service: `http://localhost:8082/swagger-ui.html`
  * Metadata Service: `http://localhost:8084/swagger-ui.html`
* Spec OpenAPI được sinh tự động từ code.

---

## ✅ Roadmap

* [x] Tạo **Auth Service** (JWT + Role)
* [ ] Tạo **User Service**
* [ ] Tạo **Media Service** (scan folder, trả file)
* [ ] Tạo **Metadata Service**
* [ ] Tạo **Sharing Service**
* [ ] API Gateway + Docker Compose
* [ ] Triển khai trên server

---

## ✅ Yêu cầu hệ thống

* JDK 17+
* Gradle 8+
* Docker 20+
* MySQL 8+

