
```
vno-backend
├─ .qodo
│  ├─ agents
│  └─ workflows
├─ api-gateway
│  ├─ build.gradle.kts
│  ├─ Dockerfile
│  └─ src
│     └─ main
│        ├─ java
│        │  └─ com
│        │     └─ vno
│        │        └─ gateway
│        │           ├─ ApiGatewayApplication.java
│        │           ├─ config
│        │           │  ├─ GatewayOpenApiProperties.java
│        │           │  ├─ OpenApiAggregationConfig.java
│        │           │  ├─ OpenApiRouteConfig.java
│        │           │  ├─ SecurityConfig.java
│        │           │  └─ WebClientConfig.java
│        │           ├─ controller
│        │           │  └─ OpenApiAggregatorController.java
│        │           ├─ filter
│        │           │  ├─ AuthFilter.java
│        │           │  └─ JwtAuthenticationFilter.java
│        │           └─ security
│        │              ├─ JwtAuthenticationToken.java
│        │              └─ JwtUtil.java
│        └─ resources
│           └─ application.yml
├─ api-links.md
├─ auth-service
│  ├─ build.gradle.kts
│  ├─ Dockerfile
│  └─ src
│     ├─ main
│     │  ├─ java
│     │  │  └─ com
│     │  │     └─ vno
│     │  │        └─ auth
│     │  │           ├─ AuthServiceApplication.java
│     │  │           ├─ client
│     │  │           │  └─ UserServiceClient.java
│     │  │           ├─ config
│     │  │           │  ├─ OpenApiConfig.java
│     │  │           │  ├─ OpenApiRedirectController.java
│     │  │           │  ├─ SecurityConfig.java
│     │  │           │  └─ WebConfig.java
│     │  │           ├─ controller
│     │  │           │  └─ AuthController.java
│     │  │           ├─ dto
│     │  │           │  ├─ AuthResponse.java
│     │  │           │  ├─ LoginRequest.java
│     │  │           │  ├─ RefreshTokenRequest.java
│     │  │           │  ├─ RegisterRequest.java
│     │  │           │  ├─ UserSessionResponse.java
│     │  │           │  └─ VerifyEmailRequest.java
│     │  │           ├─ entity
│     │  │           │  ├─ RefreshToken.java
│     │  │           │  └─ User.java
│     │  │           ├─ exception
│     │  │           │  ├─ GlobalExceptionHandler.java
│     │  │           │  ├─ InvalidCredentialsException.java
│     │  │           │  ├─ TokenRefreshException.java
│     │  │           │  └─ UserAlreadyExistsException.java
│     │  │           ├─ filter
│     │  │           ├─ model
│     │  │           │  ├─ RefreshToken.java
│     │  │           │  ├─ Token.java
│     │  │           │  └─ UserSession.java
│     │  │           ├─ repository
│     │  │           │  ├─ RefreshTokenRepository.java
│     │  │           │  ├─ TokenRepository.java
│     │  │           │  ├─ UserRepository.java
│     │  │           │  └─ UserSessionRepository.java
│     │  │           ├─ security
│     │  │           │  └─ JwtService.java
│     │  │           ├─ service
│     │  │           │  ├─ AuthService.java
│     │  │           │  └─ impl
│     │  │           │     └─ AuthServiceImpl.java
│     │  │           ├─ task
│     │  │           │  └─ CleanupExpiredSessionsTask.java
│     │  │           └─ util
│     │  │              └─ SecurityUtils.java
│     │  └─ resources
│     │     ├─ application.yml
│     │     ├─ bootstrap.yml
│     │     └─ db
│     │        └─ migration
│     │           ├─ postgres
│     │           │  └─ V1__init.sql
│     │           ├─ V1__create_refresh_tokens_table.sql
│     │           └─ V1__create_tables.sql
│     └─ test
├─ best_practices.md
├─ build.gradle.kts
├─ checklist.md
├─ classpath-auth-service.md
├─ clean-all.bat
├─ common
│  ├─ build.gradle.kts
│  └─ src
│     └─ main
│        ├─ java
│        │  └─ com
│        │     └─ vno
│        │        └─ common
│        │           ├─ dto
│        │           │  ├─ ApiResponse.java
│        │           │  └─ ErrorResponse.java
│        │           ├─ entity
│        │           │  └─ BaseEntity.java
│        │           ├─ exception
│        │           │  └─ BusinessException.java
│        │           ├─ response
│        │           │  └─ ApiResponse.java
│        │           ├─ security
│        │           └─ ultil
│        └─ resources
│           └─ application.yml
├─ config-repo
│  ├─ api-gateway.yml
│  ├─ application.yml
│  ├─ auth-service.yml
│  ├─ note-service.yml
│  ├─ notification-service.yml
│  ├─ organization-service.yml
│  ├─ README.md
│  ├─ service-discovery.yml
│  ├─ tag-service.yml
│  └─ user-service.yml
├─ config-server
│  ├─ build.gradle.kts
│  ├─ Dockerfile
│  └─ src
│     └─ main
│        ├─ java
│        │  └─ com
│        │     └─ vno
│        │        └─ config
│        │           └─ ConfigServerApplication.java
│        └─ resources
│           ├─ application.yml
│           └─ config
│              └─ auth-service.yml
├─ contracts
│  ├─ asyncapi
│  │  └─ user-events.yaml
│  ├─ openapi
│  │  ├─ auth-service.yaml
│  │  └─ user-service.yaml
│  └─ proto
│     ├─ auth.proto
│     └─ user.proto
├─ gen-order.md
├─ gradle
│  ├─ catalog.versions.toml
│  └─ wrapper
│     └─ gradle-wrapper.properties
├─ gradle.properties
├─ gradlew
├─ gradlew.bat
├─ how-to-run.md
├─ mermaid-diagram.svg
├─ note-service
│  ├─ build.gradle.kts
│  └─ src
│     └─ test
├─ notification-service
│  ├─ build.gradle.kts
│  ├─ Dockerfile
│  └─ src
│     └─ main
│        ├─ java
│        │  └─ com
│        │     └─ vno
│        │        └─ notification
│        │           ├─ controller
│        │           │  └─ NotificationController.java
│        │           └─ NotificationServiceApplication.java
│        └─ resources
│           ├─ application.yml
│           └─ bootstrap.yml
├─ organization-service
│  ├─ build.gradle.kts
│  └─ src
│     └─ main
│        ├─ java
│        │  └─ com
│        │     └─ vno
│        │        └─ organization
│        │           ├─ client
│        │           │  └─ UserServiceClient.java
│        │           ├─ dto
│        │           │  ├─ MemberRequest.java
│        │           │  ├─ MemberResponse.java
│        │           │  ├─ OrganizationRequest.java
│        │           │  └─ OrganizationResponse.java
│        │           ├─ exception
│        │           │  ├─ MemberAlreadyExistsException.java
│        │           │  ├─ MemberNotFoundException.java
│        │           │  ├─ OrganizationNotFoundException.java
│        │           │  └─ UnauthorizedAccessException.java
│        │           ├─ mapper
│        │           │  ├─ MemberMapper.java
│        │           │  └─ OrganizationMapper.java
│        │           ├─ model
│        │           │  ├─ enums
│        │           │  │  ├─ MemberRole.java
│        │           │  │  ├─ MemberStatus.java
│        │           │  │  └─ OrganizationStatus.java
│        │           │  ├─ Member.java
│        │           │  ├─ MemberId.java
│        │           │  └─ Organization.java
│        │           ├─ OrganizationServiceApplication.java
│        │           ├─ repository
│        │           │  ├─ MemberRepository.java
│        │           │  └─ OrganizationRepository.java
│        │           └─ service
│        │              ├─ impl
│        │              │  ├─ MemberServiceImpl.java
│        │              │  └─ OrganizationServiceImpl.java
│        │              ├─ MemberService.java
│        │              └─ OrganizationService.java
│        └─ resources
│           └─ application.yml
├─ README - Copy.md
├─ README.md
├─ restructure-rule.md
├─ run-all.bat
├─ run-order.md
├─ scripts
│  ├─ setup-contracts.ps1
│  └─ sync-contracts.ps1
├─ service-discovery
│  ├─ build.gradle.kts
│  ├─ docker-compose.yml
│  ├─ Dockerfile
│  └─ src
│     └─ main
│        ├─ java
│        │  └─ com
│        │     └─ vno
│        │        └─ eureka
│        │           └─ ServiceDiscoveryApplication.java
│        └─ resources
│           └─ application.yml
├─ settings.gradle.kts
├─ structure.md
├─ tag-service
│  ├─ build.gradle.kts
│  └─ src
│     └─ main
│        ├─ java
│        │  └─ com
│        │     └─ vno
│        │        └─ tag
│        │           ├─ config
│        │           │  └─ OpenApiConfig.java
│        │           ├─ controller
│        │           │  └─ TagController.java
│        │           └─ TagServiceApplication.java
│        └─ resources
│           └─ application.yml
├─ target.md
├─ todo.md
├─ user-service
│  ├─ build.gradle.kts
│  ├─ Dockerfile
│  └─ src
│     ├─ main
│     │  ├─ java
│     │  │  └─ com
│     │  │     └─ vno
│     │  │        └─ user
│     │  │           ├─ config
│     │  │           │  ├─ OpenApiConfig.java
│     │  │           │  ├─ OpenApiRedirectController.java
│     │  │           │  └─ PasswordConfig.java
│     │  │           ├─ controller
│     │  │           │  ├─ InternalAuthController.java
│     │  │           │  └─ UserController.java
│     │  │           ├─ dto
│     │  │           │  ├─ UserRequest.java
│     │  │           │  └─ UserResponse.java
│     │  │           ├─ entity
│     │  │           │  ├─ EmailVerification.java
│     │  │           │  └─ User.java
│     │  │           ├─ exception
│     │  │           │  └─ ResourceNotFoundException.java
│     │  │           ├─ mapper
│     │  │           │  └─ UserMapper.java
│     │  │           ├─ model
│     │  │           │  ├─ enums
│     │  │           │  │  └─ UserStatus.java
│     │  │           │  └─ User.java
│     │  │           ├─ repository
│     │  │           │  ├─ EmailVerificationRepository.java
│     │  │           │  └─ UserRepository.java
│     │  │           ├─ service
│     │  │           │  ├─ impl
│     │  │           │  │  └─ UserServiceImpl.java
│     │  │           │  └─ UserService.java
│     │  │           └─ UserServiceApplication.java
│     │  └─ resources
│     │     ├─ application.yml
│     │     ├─ bootstrap.yml
│     │     └─ db
│     │        └─ migration
│     │           └─ V1__create_users_table.sql
│     └─ test
└─ WARP.md

```