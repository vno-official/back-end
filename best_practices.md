# 📘 Project Best Practices

## 1. Project Purpose
Hệ thống vno là backend đa dịch vụ (microservices) xây dựng bằng Spring Boot và Gradle. Mục tiêu là cung cấp nền tảng quản lý ghi chú (note) với tag, người dùng, tổ chức, xác thực JWT, thông báo theo thời gian thực, cấu hình tập trung và cổng API. Hạ tầng hướng đến khả năng mở rộng, tách biệt mối quan tâm, và tích hợp qua HTTP/Kafka/WebSocket.

## 2. Project Structure
- Kiến trúc: Multi-module Gradle, microservices độc lập.
- Thư mục chính:
  - src/
    - api-gateway: Spring Cloud Gateway (định tuyến, auth filter)
    - auth-service: Xác thực, phát hành/refresh JWT, bảo mật Spring Security
    - user-service: Quản lý người dùng
    - organization-service: Quản lý tổ chức và thành viên
    - note-service: Quản lý ghi chú (Note), hướng tới hỗ trợ tag và sự kiện
    - tag-service: Quản lý tag độc lập (chuẩn hoá, tái sử dụng)
    - notification-service: Thông báo (Kafka/WebSocket), realtime
    - service-discovery: Eureka Server (khám phá dịch vụ)
    - config-server: Spring Cloud Config (cấu hình tập trung), config-repo/ chứa YAML dịch vụ
    - common: Thư viện dùng chung (ApiResponse, BusinessException, AppConstants, DateUtils)
- File/Thư mục quan trọng:
  - settings.gradle, build.gradle: quản lý modules và dependency versions (libs.versions.toml)
  - config-repo/*.yml: cấu hình mỗi service; application.yml gốc
  - src/*/src/main/resources/application.yml(.properties): cấu hình cục bộ cho service
  - Flyway migrations: ví dụ auth-service/db/migration/V1__create_auth_tables.sql; tag-service có migration V1__create_tags_table.sql

## 3. Test Strategy
- Framework: JUnit 5 + Spring Boot Test (hiện có ví dụ trong common, tag-service, notification-service).
- Tổ chức:
  - test/java/... phản chiếu cấu trúc main/java.
  - Đặt tên ClassTest hoặc ...Tests theo chuẩn Spring Boot Starter.
- Mức độ kiểm thử:
  - Unit test: service, util, security helpers, JWT utils (mock repository/service bằng Mockito).
  - Slice test: @WebMvcTest cho controller, @DataJpaTest cho repository (sử dụng H2/containers).
  - Integration test: chạy ngữ cảnh Spring, test flows qua REST; với messaging/WebSocket, dùng Testcontainers cho Kafka nếu có.
- Mocking guidelines:
  - Ưu tiên interface và constructor injection để dễ mock.
  - Tránh mock framework nội bộ Spring; chỉ mock boundary (repo, client) và external IO.
- Coverage expectations:
  - Core business >= 80%; controller mapping và config critical paths có smoke tests.

## 4. Code Style
- Ngôn ngữ: Java 17+ (theo tiêu chuẩn Spring hiện đại)
- Quy ước đặt tên:
  - Class: PascalCase (NoteService, AuthController)
  - Methods/variables: camelCase (createNote, refreshToken)
  - DTO: Hậu tố Request/Response/Dto; Entity: tên domain (User, Note)
  - Repository: Hậu tố Repository; Controller/Service tương ứng.
- Tổ chức lớp:
  - controller → service → repository → model/dto; không gọi repository trực tiếp từ controller.
  - common dùng cho hạ tầng chia sẻ (ApiResponse, exceptions, constants, utils).
- Async/Reactive:
  - Gateway có filter/bảo mật; các service còn lại dùng MVC đồng bộ mặc định. Nếu dùng WebFlux/Kafka, giữ boundary tách biệt.
- Documentation:
  - JavaDoc cho public API/service methods quan trọng.
  - Comment ngắn gọn cho logic phi-trivial; tránh lặp mô tả hiển nhiên.
- Error handling:
  - Dùng BusinessException (common) cho lỗi nghiệp vụ; map sang mã HTTP phù hợp ở advice layer.
  - Validate đầu vào ở DTO layer (Bean Validation) và tái sử dụng messages chuẩn.
  - Log lỗi tại boundary (controller advice, message listener) với context (requestId, userId).

## 5. Common Patterns
- DTO mapping: tách DTO và Entity; mapping tập trung (MapStruct khuyến nghị nếu dùng nhiều mapping).
- Service layer: stateless, constructor injection, rõ ràng transaction boundaries (@Transactional ở service).
- Repository: Spring Data JPA conventions; phương thức tìm kiếm rõ ràng; index phù hợp ở migrations.
- Configuration:
  - Externalized config qua Spring Cloud Config; local override bằng application.yml.
  - Sử dụng profiles (dev,test,prod); secrets qua environment variables.
- Security:
  - JWT qua auth-service; api-gateway xác thực và propagate claims.
  - Áp dụng role/permission ở service phía sau khi cần.
- Messaging & Realtime:
  - Notification qua Kafka consumer/producer; WebSocket broker cho push realtime.
  - Event naming: <Domain><Action>Event (NoteCreatedEvent).

## 6. Do's and Don'ts
- ✅ Do
  - Tách rõ các layer; viết test trước cho business critical.
  - Dùng constructor injection, final fields, và @Transactional đúng phạm vi.
  - Chuẩn hoá response qua ApiResponse ở common khi phù hợp.
  - Migrations bằng Flyway cho mọi thay đổi schema; không chỉnh schema thủ công.
  - Cấu hình qua config-repo; không hardcode secrets/URLs.
  - Log có cấu trúc, bao gồm correlation-id qua gateway.
- ❌ Don't
  - Không gọi trực tiếp DB từ controller; không lẫn lộn DTO và Entity trong API.
  - Không bắt Exception chung chung và nuốt lỗi; không lạm dụng @Autowired field injection.
  - Không chia sẻ schema DB giữa microservices nếu không cần thiết.
  - Không đẩy logic nghiệp vụ sang controller hoặc repository.

## 7. Tools & Dependencies
- Key:
  - Spring Boot, Spring Web, Spring Security, Spring Data JPA, Spring Cloud (Gateway, Config, Netflix Eureka)
  - Flyway cho migration, Kafka cho messaging, WebSocket cho realtime
  - JUnit 5, Mockito, Testcontainers (khuyến nghị)
- Setup cơ bản:
  - Java 17+, Gradle Wrapper (./gradlew build)
  - Chạy config-server, service-discovery trước; sau đó các services theo nhu cầu.
  - Cấu hình dịch vụ trong config-repo/*.yml; application.yml nội bộ chỉ cho local.

## 8. Other Notes
- Khi phát triển note-service:
  - Tạo entity Note + migrations (Flyway) và repository; dùng DTO Request/Response.
  - Tích hợp tag-service qua REST hoặc qua domain association nếu dùng DB riêng.
  - Phát hành events (NoteCreated/Updated/Deleted) để đồng bộ notification-service.
  - Bảo vệ endpoints qua gateway; sử dụng claims (userId, orgId) từ JWT để phân quyền dữ liệu.
- Khi thêm API mới:
  - Cập nhật route ở api-gateway và config tương ứng ở config-repo.
  - Viết tests: unit + slice/integration cho flow chính.
  - Cập nhật tài liệu API và cấu hình (profiles) tương ứng.
