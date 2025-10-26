
```
test
├─ .qodo
│  ├─ agents
│  └─ workflows
├─ api-links.md
├─ best_practices.md
├─ checklist.md
├─ classpath-auth-service.md
├─ clean-all.bat
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
├─ gen-order.md
├─ gradle
│  ├─ libs.versions.toml
│  └─ wrapper
│     ├─ gradle-wrapper.jar
│     └─ gradle-wrapper.properties
├─ gradle.properties
├─ gradlew
├─ gradlew.bat
├─ how-to-run.md
├─ README - Copy.md
├─ README.md
├─ run-all.bat
├─ run-order.md
├─ SETUP.md
├─ src
│  ├─ api-gateway
│  │  ├─ bin
│  │  │  ├─ .project
│  │  │  ├─ .settings
│  │  │  │  └─ org.eclipse.jdt.core.prefs
│  │  │  ├─ default
│  │  │  ├─ generated-sources
│  │  │  │  └─ annotations
│  │  │  ├─ main
│  │  │  │  ├─ application.yml
│  │  │  │  └─ com
│  │  │  │     └─ vno
│  │  │  │        └─ gateway
│  │  │  │           ├─ ApiGatewayApplication.class
│  │  │  │           ├─ config
│  │  │  │           │  └─ GatewayConfig.class
│  │  │  │           └─ filter
│  │  │  │              └─ AuthFilter.class
│  │  │  └─ src
│  │  │     └─ main
│  │  │        └─ java
│  │  │           └─ com
│  │  │              └─ vno
│  │  │                 └─ gateway
│  │  │                    ├─ ApiGatewayApplication.class
│  │  │                    └─ config
│  │  │                       └─ GatewayConfig.class
│  │  ├─ Dockerfile
│  │  └─ src
│  │     └─ main
│  │        ├─ java
│  │        │  └─ com
│  │        │     └─ vno
│  │        │        └─ gateway
│  │        │           ├─ ApiGatewayApplication.java
│  │        │           ├─ config
│  │        │           │  └─ GatewayConfig.java
│  │        │           └─ filter
│  │        │              └─ AuthFilter.java
│  │        └─ resources
│  │           └─ application.yml
│  ├─ auth-service
│  │  ├─ bin
│  │  │  ├─ .project
│  │  │  ├─ .settings
│  │  │  │  └─ org.eclipse.jdt.core.prefs
│  │  │  ├─ default
│  │  │  ├─ generated-sources
│  │  │  │  └─ annotations
│  │  │  ├─ main
│  │  │  │  ├─ application.yml
│  │  │  │  ├─ bootstrap.yml
│  │  │  │  ├─ com
│  │  │  │  │  └─ vno
│  │  │  │  │     └─ auth
│  │  │  │  │        ├─ AuthServiceApplication.class
│  │  │  │  │        ├─ client
│  │  │  │  │        │  ├─ UserServiceClient$ApiResponse.class
│  │  │  │  │        │  ├─ UserServiceClient$CreateUserRequest.class
│  │  │  │  │        │  ├─ UserServiceClient$UserResponse.class
│  │  │  │  │        │  ├─ UserServiceClient$VerifyRequest.class
│  │  │  │  │        │  ├─ UserServiceClient$VerifyResponse.class
│  │  │  │  │        │  └─ UserServiceClient.class
│  │  │  │  │        ├─ config
│  │  │  │  │        │  ├─ JwtConfig.class
│  │  │  │  │        │  ├─ JwtProperties$Token.class
│  │  │  │  │        │  ├─ JwtProperties.class
│  │  │  │  │        │  ├─ OpenApiConfig.class
│  │  │  │  │        │  └─ WebConfig.class
│  │  │  │  │        ├─ controller
│  │  │  │  │        │  └─ AuthController.class
│  │  │  │  │        ├─ dto
│  │  │  │  │        │  ├─ AuthResponse$AuthResponseBuilder.class
│  │  │  │  │        │  ├─ AuthResponse$UserInfo$UserInfoBuilder.class
│  │  │  │  │        │  ├─ AuthResponse$UserInfo.class
│  │  │  │  │        │  ├─ AuthResponse.class
│  │  │  │  │        │  ├─ LoginRequest$LoginRequestBuilder.class
│  │  │  │  │        │  ├─ LoginRequest.class
│  │  │  │  │        │  ├─ RefreshTokenRequest$RefreshTokenRequestBuilder.class
│  │  │  │  │        │  ├─ RefreshTokenRequest.class
│  │  │  │  │        │  ├─ RegisterRequest$RegisterRequestBuilder.class
│  │  │  │  │        │  ├─ RegisterRequest.class
│  │  │  │  │        │  ├─ UserSessionResponse$UserSessionResponseBuilder.class
│  │  │  │  │        │  ├─ UserSessionResponse.class
│  │  │  │  │        │  ├─ VerifyEmailRequest$VerifyEmailRequestBuilder.class
│  │  │  │  │        │  └─ VerifyEmailRequest.class
│  │  │  │  │        ├─ entity
│  │  │  │  │        │  ├─ RefreshToken$RefreshTokenBuilder.class
│  │  │  │  │        │  ├─ RefreshToken.class
│  │  │  │  │        │  └─ User.class
│  │  │  │  │        ├─ exception
│  │  │  │  │        │  ├─ GlobalExceptionHandler.class
│  │  │  │  │        │  ├─ InvalidCredentialsException.class
│  │  │  │  │        │  ├─ TokenRefreshException.class
│  │  │  │  │        │  └─ UserAlreadyExistsException.class
│  │  │  │  │        ├─ model
│  │  │  │  │        │  ├─ RefreshToken$RefreshTokenBuilder.class
│  │  │  │  │        │  ├─ RefreshToken.class
│  │  │  │  │        │  ├─ Token$TokenBuilder.class
│  │  │  │  │        │  ├─ Token$TokenType.class
│  │  │  │  │        │  ├─ Token.class
│  │  │  │  │        │  ├─ UserSession$UserSessionBuilder.class
│  │  │  │  │        │  └─ UserSession.class
│  │  │  │  │        ├─ repository
│  │  │  │  │        │  ├─ RefreshTokenRepository.class
│  │  │  │  │        │  ├─ TokenRepository.class
│  │  │  │  │        │  ├─ UserRepository.class
│  │  │  │  │        │  └─ UserSessionRepository.class
│  │  │  │  │        ├─ security
│  │  │  │  │        │  ├─ JwtAuthenticationFilter.class
│  │  │  │  │        │  ├─ JwtService.class
│  │  │  │  │        │  └─ SecurityConfig.class
│  │  │  │  │        ├─ service
│  │  │  │  │        │  ├─ AuthService.class
│  │  │  │  │        │  └─ impl
│  │  │  │  │        │     └─ AuthServiceImpl.class
│  │  │  │  │        └─ task
│  │  │  │  │           └─ CleanupExpiredSessionsTask.class
│  │  │  │  └─ db
│  │  │  │     └─ migration
│  │  │  │        ├─ postgres
│  │  │  │        │  └─ V1__init.sql
│  │  │  │        ├─ V1__create_refresh_tokens_table.sql
│  │  │  │        └─ V1__create_tables.sql
│  │  │  └─ src
│  │  │     └─ main
│  │  │        ├─ java
│  │  │        │  └─ com
│  │  │        │     └─ vno
│  │  │        │        └─ auth
│  │  │        │           ├─ AuthServiceApplication.class
│  │  │        │           ├─ config
│  │  │        │           │  ├─ JwtConfig.class
│  │  │        │           │  ├─ JwtProperties$Token.class
│  │  │        │           │  ├─ JwtProperties.class
│  │  │        │           │  ├─ OpenApiConfig.class
│  │  │        │           │  └─ WebConfig.class
│  │  │        │           ├─ controller
│  │  │        │           │  └─ AuthController.class
│  │  │        │           ├─ dto
│  │  │        │           │  ├─ AuthResponse$AuthResponseBuilder.class
│  │  │        │           │  ├─ AuthResponse$UserInfo$UserInfoBuilder.class
│  │  │        │           │  ├─ AuthResponse$UserInfo.class
│  │  │        │           │  ├─ AuthResponse.class
│  │  │        │           │  ├─ LoginRequest$LoginRequestBuilder.class
│  │  │        │           │  ├─ LoginRequest.class
│  │  │        │           │  ├─ RefreshTokenRequest$RefreshTokenRequestBuilder.class
│  │  │        │           │  ├─ RefreshTokenRequest.class
│  │  │        │           │  ├─ RegisterRequest$RegisterRequestBuilder.class
│  │  │        │           │  ├─ RegisterRequest.class
│  │  │        │           │  ├─ UserSessionResponse$UserSessionResponseBuilder.class
│  │  │        │           │  ├─ UserSessionResponse.class
│  │  │        │           │  ├─ VerifyEmailRequest$VerifyEmailRequestBuilder.class
│  │  │        │           │  └─ VerifyEmailRequest.class
│  │  │        │           ├─ entity
│  │  │        │           │  ├─ RefreshToken$RefreshTokenBuilder.class
│  │  │        │           │  └─ RefreshToken.class
│  │  │        │           ├─ exception
│  │  │        │           │  ├─ GlobalExceptionHandler.class
│  │  │        │           │  ├─ InvalidCredentialsException.class
│  │  │        │           │  ├─ TokenRefreshException.class
│  │  │        │           │  └─ UserAlreadyExistsException.class
│  │  │        │           ├─ model
│  │  │        │           │  ├─ RefreshToken$RefreshTokenBuilder.class
│  │  │        │           │  ├─ RefreshToken.class
│  │  │        │           │  ├─ Token$TokenBuilder.class
│  │  │        │           │  ├─ Token$TokenType.class
│  │  │        │           │  ├─ Token.class
│  │  │        │           │  ├─ UserSession$UserSessionBuilder.class
│  │  │        │           │  └─ UserSession.class
│  │  │        │           ├─ repository
│  │  │        │           │  ├─ RefreshTokenRepository.class
│  │  │        │           │  ├─ TokenRepository.class
│  │  │        │           │  └─ UserSessionRepository.class
│  │  │        │           ├─ security
│  │  │        │           │  ├─ JwtAuthenticationFilter.class
│  │  │        │           │  ├─ JwtService.class
│  │  │        │           │  └─ SecurityConfig.class
│  │  │        │           ├─ service
│  │  │        │           │  ├─ AuthService.class
│  │  │        │           │  └─ impl
│  │  │        │           │     └─ AuthServiceImpl.class
│  │  │        │           └─ task
│  │  │        │              └─ CleanupExpiredSessionsTask.class
│  │  │        └─ resources
│  │  │           ├─ application.yml
│  │  │           ├─ bootstrap.yml
│  │  │           └─ db
│  │  │              └─ migration
│  │  │                 ├─ V1__create_refresh_tokens_table.sql
│  │  │                 └─ V1__create_tables.sql
│  │  ├─ Dockerfile
│  │  └─ src
│  │     └─ main
│  │        ├─ java
│  │        │  └─ com
│  │        │     └─ vno
│  │        │        └─ auth
│  │        │           ├─ AuthServiceApplication.java
│  │        │           ├─ client
│  │        │           │  └─ UserServiceClient.java
│  │        │           ├─ config
│  │        │           │  ├─ JwtConfig.java
│  │        │           │  ├─ JwtProperties.java
│  │        │           │  ├─ OpenApiConfig.java
│  │        │           │  └─ WebConfig.java
│  │        │           ├─ controller
│  │        │           │  └─ AuthController.java
│  │        │           ├─ dto
│  │        │           │  ├─ AuthResponse.java
│  │        │           │  ├─ LoginRequest.java
│  │        │           │  ├─ RefreshTokenRequest.java
│  │        │           │  ├─ RegisterRequest.java
│  │        │           │  ├─ UserSessionResponse.java
│  │        │           │  └─ VerifyEmailRequest.java
│  │        │           ├─ entity
│  │        │           │  ├─ RefreshToken.java
│  │        │           │  └─ User.java
│  │        │           ├─ exception
│  │        │           │  ├─ GlobalExceptionHandler.java
│  │        │           │  ├─ InvalidCredentialsException.java
│  │        │           │  ├─ TokenRefreshException.java
│  │        │           │  └─ UserAlreadyExistsException.java
│  │        │           ├─ model
│  │        │           │  ├─ RefreshToken.java
│  │        │           │  ├─ Token.java
│  │        │           │  └─ UserSession.java
│  │        │           ├─ repository
│  │        │           │  ├─ RefreshTokenRepository.java
│  │        │           │  ├─ TokenRepository.java
│  │        │           │  ├─ UserRepository.java
│  │        │           │  └─ UserSessionRepository.java
│  │        │           ├─ security
│  │        │           │  ├─ JwtAuthenticationFilter.java
│  │        │           │  ├─ JwtService.java
│  │        │           │  └─ SecurityConfig.java
│  │        │           ├─ service
│  │        │           │  ├─ AuthService.java
│  │        │           │  └─ impl
│  │        │           │     └─ AuthServiceImpl.java
│  │        │           └─ task
│  │        │              └─ CleanupExpiredSessionsTask.java
│  │        └─ resources
│  │           ├─ application.yml
│  │           ├─ bootstrap.yml
│  │           └─ db
│  │              └─ migration
│  │                 ├─ postgres
│  │                 │  └─ V1__init.sql
│  │                 ├─ V1__create_refresh_tokens_table.sql
│  │                 └─ V1__create_tables.sql
│  ├─ bin
│  │  ├─ default
│  │  └─ generated-sources
│  │     └─ annotations
│  ├─ common
│  │  ├─ bin
│  │  │  ├─ .project
│  │  │  ├─ .settings
│  │  │  │  └─ org.eclipse.jdt.core.prefs
│  │  │  ├─ default
│  │  │  ├─ generated-sources
│  │  │  │  └─ annotations
│  │  │  ├─ main
│  │  │  │  ├─ application.yml
│  │  │  │  └─ com
│  │  │  │     └─ vno
│  │  │  │        └─ common
│  │  │  │           ├─ dto
│  │  │  │           │  ├─ ApiResponse$ApiResponseBuilder.class
│  │  │  │           │  ├─ ApiResponse.class
│  │  │  │           │  ├─ ErrorResponse$ErrorResponseBuilder.class
│  │  │  │           │  └─ ErrorResponse.class
│  │  │  │           ├─ entity
│  │  │  │           │  └─ BaseEntity.class
│  │  │  │           ├─ exception
│  │  │  │           │  ├─ BusinessException.class
│  │  │  │           │  └─ GlobalExceptionHandler.class
│  │  │  │           ├─ security
│  │  │  │           │  └─ JwtUtil.class
│  │  │  │           └─ ultil
│  │  │  └─ src
│  │  │     └─ main
│  │  │        └─ java
│  │  │           └─ com
│  │  │              └─ vno
│  │  │                 └─ common
│  │  │                    ├─ dto
│  │  │                    │  ├─ ApiResponse$ApiResponseBuilder.class
│  │  │                    │  ├─ ApiResponse.class
│  │  │                    │  ├─ ErrorResponse$ErrorResponseBuilder.class
│  │  │                    │  └─ ErrorResponse.class
│  │  │                    ├─ entity
│  │  │                    │  └─ BaseEntity.class
│  │  │                    ├─ exception
│  │  │                    │  ├─ BusinessException.class
│  │  │                    │  └─ GlobalExceptionHandler.class
│  │  │                    └─ security
│  │  │                       └─ JwtUtil.class
│  │  └─ src
│  │     └─ main
│  │        ├─ java
│  │        │  └─ com
│  │        │     └─ vno
│  │        │        └─ common
│  │        │           ├─ dto
│  │        │           │  ├─ ApiResponse.java
│  │        │           │  └─ ErrorResponse.java
│  │        │           ├─ entity
│  │        │           │  └─ BaseEntity.java
│  │        │           ├─ exception
│  │        │           │  ├─ BusinessException.java
│  │        │           │  └─ GlobalExceptionHandler.java
│  │        │           ├─ security
│  │        │           │  └─ JwtUtil.java
│  │        │           └─ ultil
│  │        └─ resources
│  │           └─ application.yml
│  ├─ config-server
│  │  ├─ bin
│  │  │  ├─ default
│  │  │  ├─ generated-sources
│  │  │  │  └─ annotations
│  │  │  └─ main
│  │  │     ├─ application.yml
│  │  │     ├─ com
│  │  │     │  └─ vno
│  │  │     │     └─ config
│  │  │     │        └─ ConfigServerApplication.class
│  │  │     └─ config
│  │  │        └─ auth-service.yml
│  │  ├─ Dockerfile
│  │  └─ src
│  │     └─ main
│  │        ├─ java
│  │        │  └─ com
│  │        │     └─ vno
│  │        │        └─ config
│  │        │           └─ ConfigServerApplication.java
│  │        └─ resources
│  │           ├─ application.yml
│  │           └─ config
│  │              └─ auth-service.yml
│  ├─ db
│  │  └─ init
│  │     └─ create_databases.sql
│  ├─ demo
│  ├─ docker-compose.yml
│  ├─ gradle
│  │  └─ wrapper
│  │     └─ gradle-wrapper.properties
│  ├─ note-service
│  │  ├─ bin
│  │  │  ├─ .project
│  │  │  ├─ .settings
│  │  │  │  └─ org.eclipse.jdt.core.prefs
│  │  │  ├─ default
│  │  │  ├─ generated-sources
│  │  │  │  └─ annotations
│  │  │  └─ main
│  │  │     ├─ bootstrap.yml
│  │  │     └─ com
│  │  │        └─ vno
│  │  │           └─ note
│  │  │              ├─ config
│  │  │              │  ├─ OpenApiConfig.class
│  │  │              │  └─ SecurityConfig.class
│  │  │              ├─ controller
│  │  │              │  ├─ NoteController$Note.class
│  │  │              │  └─ NoteController.class
│  │  │              └─ NoteServiceApplication.class
│  │  └─ src
│  │     └─ main
│  │        ├─ java
│  │        │  └─ com
│  │        │     └─ vno
│  │        │        └─ note
│  │        │           ├─ config
│  │        │           │  ├─ OpenApiConfig.java
│  │        │           │  └─ SecurityConfig.java
│  │        │           ├─ controller
│  │        │           │  └─ NoteController.java
│  │        │           └─ NoteServiceApplication.java
│  │        └─ resources
│  │           └─ bootstrap.yml
│  ├─ notification-service
│  │  ├─ bin
│  │  │  ├─ .settings
│  │  │  │  └─ org.eclipse.jdt.core.prefs
│  │  │  ├─ default
│  │  │  ├─ generated-sources
│  │  │  │  └─ annotations
│  │  │  ├─ main
│  │  │  │  ├─ bootstrap.yml
│  │  │  │  └─ com
│  │  │  │     └─ vno
│  │  │  │        └─ notification
│  │  │  │           ├─ config
│  │  │  │           │  └─ SecurityConfig.class
│  │  │  │           ├─ controller
│  │  │  │           │  └─ NotificationController.class
│  │  │  │           └─ NotificationServiceApplication.class
│  │  │  └─ src
│  │  │     ├─ main
│  │  │     │  ├─ java
│  │  │     │  │  └─ com
│  │  │     │  │     └─ example
│  │  │     │  │        └─ notificationservice
│  │  │     │  │           ├─ config
│  │  │     │  │           ├─ controller
│  │  │     │  │           ├─ dto
│  │  │     │  │           ├─ kafka
│  │  │     │  │           ├─ model
│  │  │     │  │           ├─ repository
│  │  │     │  │           └─ service
│  │  │     │  └─ resources
│  │  │     └─ test
│  │  │        └─ java
│  │  │           └─ com
│  │  │              └─ example
│  │  │                 └─ notificationservice
│  │  ├─ Dockerfile
│  │  └─ src
│  │     └─ main
│  │        ├─ java
│  │        │  └─ com
│  │        │     └─ vno
│  │        │        └─ notification
│  │        │           ├─ config
│  │        │           │  └─ SecurityConfig.java
│  │        │           ├─ controller
│  │        │           │  └─ NotificationController.java
│  │        │           └─ NotificationServiceApplication.java
│  │        └─ resources
│  │           └─ bootstrap.yml
│  ├─ organization-service
│  │  ├─ bin
│  │  │  ├─ default
│  │  │  │  └─ com
│  │  │  │     └─ vno
│  │  │  │        └─ organization
│  │  │  │           └─ mapper
│  │  │  │              └─ OrganizationMapperImpl.class
│  │  │  ├─ generated-sources
│  │  │  │  └─ annotations
│  │  │  │     └─ com
│  │  │  │        └─ vno
│  │  │  │           └─ organization
│  │  │  │              └─ mapper
│  │  │  │                 └─ OrganizationMapperImpl.java
│  │  │  └─ main
│  │  │     └─ com
│  │  │        └─ vno
│  │  │           └─ organization
│  │  │              ├─ client
│  │  │              │  ├─ UserServiceClient$UserResponse.class
│  │  │              │  └─ UserServiceClient.class
│  │  │              ├─ dto
│  │  │              │  ├─ MemberRequest.class
│  │  │              │  ├─ MemberResponse$MemberResponseBuilder.class
│  │  │              │  ├─ MemberResponse.class
│  │  │              │  ├─ OrganizationRequest.class
│  │  │              │  ├─ OrganizationResponse$OrganizationResponseBuilder.class
│  │  │              │  └─ OrganizationResponse.class
│  │  │              ├─ exception
│  │  │              │  ├─ MemberAlreadyExistsException.class
│  │  │              │  ├─ MemberNotFoundException.class
│  │  │              │  ├─ OrganizationNotFoundException.class
│  │  │              │  └─ UnauthorizedAccessException.class
│  │  │              ├─ mapper
│  │  │              │  ├─ MemberMapper.class
│  │  │              │  └─ OrganizationMapper.class
│  │  │              ├─ model
│  │  │              │  ├─ enums
│  │  │              │  │  ├─ MemberRole.class
│  │  │              │  │  ├─ MemberStatus.class
│  │  │              │  │  └─ OrganizationStatus.class
│  │  │              │  ├─ Member.class
│  │  │              │  ├─ MemberId.class
│  │  │              │  ├─ Organization$OrganizationBuilder.class
│  │  │              │  └─ Organization.class
│  │  │              ├─ OrganizationServiceApplication.class
│  │  │              ├─ repository
│  │  │              │  ├─ MemberRepository.class
│  │  │              │  └─ OrganizationRepository.class
│  │  │              └─ service
│  │  │                 ├─ impl
│  │  │                 │  ├─ MemberServiceImpl.class
│  │  │                 │  └─ OrganizationServiceImpl.class
│  │  │                 ├─ MemberService.class
│  │  │                 └─ OrganizationService.class
│  │  └─ src
│  │     └─ main
│  │        └─ java
│  │           └─ com
│  │              └─ vno
│  │                 └─ organization
│  │                    ├─ client
│  │                    │  └─ UserServiceClient.java
│  │                    ├─ dto
│  │                    │  ├─ MemberRequest.java
│  │                    │  ├─ MemberResponse.java
│  │                    │  ├─ OrganizationRequest.java
│  │                    │  └─ OrganizationResponse.java
│  │                    ├─ exception
│  │                    │  ├─ MemberAlreadyExistsException.java
│  │                    │  ├─ MemberNotFoundException.java
│  │                    │  ├─ OrganizationNotFoundException.java
│  │                    │  └─ UnauthorizedAccessException.java
│  │                    ├─ mapper
│  │                    │  ├─ MemberMapper.java
│  │                    │  └─ OrganizationMapper.java
│  │                    ├─ model
│  │                    │  ├─ enums
│  │                    │  │  ├─ MemberRole.java
│  │                    │  │  ├─ MemberStatus.java
│  │                    │  │  └─ OrganizationStatus.java
│  │                    │  ├─ Member.java
│  │                    │  ├─ MemberId.java
│  │                    │  └─ Organization.java
│  │                    ├─ OrganizationServiceApplication.java
│  │                    ├─ repository
│  │                    │  ├─ MemberRepository.java
│  │                    │  └─ OrganizationRepository.java
│  │                    └─ service
│  │                       ├─ impl
│  │                       │  ├─ MemberServiceImpl.java
│  │                       │  └─ OrganizationServiceImpl.java
│  │                       ├─ MemberService.java
│  │                       └─ OrganizationService.java
│  ├─ service-discovery
│  │  ├─ bin
│  │  │  ├─ default
│  │  │  ├─ generated-sources
│  │  │  │  └─ annotations
│  │  │  └─ main
│  │  │     ├─ application.yml
│  │  │     └─ com
│  │  │        └─ vno
│  │  │           └─ eureka
│  │  │              ├─ ServiceDiscoveryApplication$WebSecurityConfig.class
│  │  │              └─ ServiceDiscoveryApplication.class
│  │  ├─ docker-compose.yml
│  │  ├─ Dockerfile
│  │  └─ src
│  │     └─ main
│  │        ├─ java
│  │        │  └─ com
│  │        │     └─ vno
│  │        │        └─ eureka
│  │        │           └─ ServiceDiscoveryApplication.java
│  │        └─ resources
│  │           └─ application.yml
│  ├─ structure.md
│  ├─ tag-service
│  │  ├─ bin
│  │  │  ├─ default
│  │  │  ├─ generated-sources
│  │  │  │  └─ annotations
│  │  │  └─ main
│  │  │     ├─ bootstrap.yml
│  │  │     └─ com
│  │  │        └─ vno
│  │  │           └─ tag
│  │  │              ├─ config
│  │  │              │  ├─ OpenApiConfig.class
│  │  │              │  └─ SecurityConfig.class
│  │  │              ├─ controller
│  │  │              │  ├─ TagController$Tag.class
│  │  │              │  └─ TagController.class
│  │  │              └─ TagServiceApplication.class
│  │  └─ src
│  │     └─ main
│  │        ├─ java
│  │        │  └─ com
│  │        │     └─ vno
│  │        │        └─ tag
│  │        │           ├─ config
│  │        │           │  ├─ OpenApiConfig.java
│  │        │           │  └─ SecurityConfig.java
│  │        │           ├─ controller
│  │        │           │  └─ TagController.java
│  │        │           └─ TagServiceApplication.java
│  │        └─ resources
│  │           └─ bootstrap.yml
│  ├─ target.md
│  ├─ todo.md
│  └─ user-service
│     ├─ bin
│     │  ├─ default
│     │  │  └─ com
│     │  │     └─ vno
│     │  │        └─ user
│     │  │           └─ mapper
│     │  │              └─ UserMapperImpl.class
│     │  ├─ generated-sources
│     │  │  └─ annotations
│     │  │     └─ com
│     │  │        └─ vno
│     │  │           └─ user
│     │  │              └─ mapper
│     │  │                 └─ UserMapperImpl.java
│     │  └─ main
│     │     ├─ application.yml
│     │     ├─ bootstrap.yml
│     │     ├─ com
│     │     │  └─ vno
│     │     │     └─ user
│     │     │        ├─ config
│     │     │        │  ├─ OpenApiConfig.class
│     │     │        │  ├─ PasswordConfig.class
│     │     │        │  └─ SecurityConfig.class
│     │     │        ├─ controller
│     │     │        │  ├─ InternalAuthController$UserInfo.class
│     │     │        │  ├─ InternalAuthController$VerifyRequest.class
│     │     │        │  ├─ InternalAuthController.class
│     │     │        │  └─ UserController.class
│     │     │        ├─ dto
│     │     │        │  ├─ UserRequest.class
│     │     │        │  ├─ UserResponse$UserResponseBuilder.class
│     │     │        │  └─ UserResponse.class
│     │     │        ├─ entity
│     │     │        ├─ exception
│     │     │        │  └─ ResourceNotFoundException.class
│     │     │        ├─ mapper
│     │     │        │  └─ UserMapper.class
│     │     │        ├─ model
│     │     │        │  ├─ enums
│     │     │        │  │  └─ UserStatus.class
│     │     │        │  ├─ User$UserBuilder.class
│     │     │        │  └─ User.class
│     │     │        ├─ repository
│     │     │        │  ├─ EmailVerificationRepository.class
│     │     │        │  └─ UserRepository.class
│     │     │        ├─ service
│     │     │        │  ├─ impl
│     │     │        │  │  └─ UserServiceImpl.class
│     │     │        │  └─ UserService.class
│     │     │        └─ UserServiceApplication.class
│     │     └─ db
│     │        └─ migration
│     │           └─ V1__create_users_table.sql
│     ├─ Dockerfile
│     └─ src
│        └─ main
│           ├─ java
│           │  └─ com
│           │     └─ vno
│           │        └─ user
│           │           ├─ config
│           │           │  ├─ OpenApiConfig.java
│           │           │  ├─ PasswordConfig.java
│           │           │  └─ SecurityConfig.java
│           │           ├─ controller
│           │           │  ├─ InternalAuthController.java
│           │           │  └─ UserController.java
│           │           ├─ dto
│           │           │  ├─ UserRequest.java
│           │           │  └─ UserResponse.java
│           │           ├─ entity
│           │           │  ├─ EmailVerification.java
│           │           │  └─ User.java
│           │           ├─ exception
│           │           │  └─ ResourceNotFoundException.java
│           │           ├─ mapper
│           │           │  └─ UserMapper.java
│           │           ├─ model
│           │           │  ├─ enums
│           │           │  │  └─ UserStatus.java
│           │           │  └─ User.java
│           │           ├─ repository
│           │           │  ├─ EmailVerificationRepository.java
│           │           │  └─ UserRepository.java
│           │           ├─ service
│           │           │  ├─ impl
│           │           │  │  └─ UserServiceImpl.java
│           │           │  └─ UserService.java
│           │           └─ UserServiceApplication.java
│           └─ resources
│              ├─ application.yml
│              ├─ bootstrap.yml
│              └─ db
│                 └─ migration
│                    └─ V1__create_users_table.sql
└─ WARP.md

```