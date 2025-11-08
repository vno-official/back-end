# setup-contracts.ps1
$ErrorActionPreference = "Stop"

$PROJECT_ROOT = "vno-backend"
$CONTRACTS    = "$PROJECT_ROOT/contracts"

Write-Host "Đang tạo cấu trúc contracts..." -ForegroundColor Green

# Tạo thư mục
New-Item -ItemType Directory -Force -Path "$CONTRACTS/openapi"  | Out-Null
New-Item -ItemType Directory -Force -Path "$CONTRACTS/proto"    | Out-Null
New-Item -ItemType Directory -Force -Path "$CONTRACTS/asyncapi" | Out-Null

# ==== OPENAPI ====
@"
openapi: 3.0.3
info:
  title: Auth Service API
  version: 1.0.0
servers:
  - url: http://localhost:8081/api/auth
paths:
  /login:
    post:
      summary: Login
      responses:
        '200':
          description: OK
"@ | Set-Content -Encoding UTF8 "$CONTRACTS/openapi/auth-service.yaml"

@"
openapi: 3.0.3
info:
  title: User Service API
  version: 1.0.0
servers:
  - url: http://localhost:8082/api/users
paths:
  /users:
    get:
      summary: List users
      responses:
        '200':
          description: OK
"@ | Set-Content -Encoding UTF8 "$CONTRACTS/openapi/user-service.yaml"

@"
openapi: 3.0.3
info:
  title: Gateway Aggregated API
  version: 1.0.0
paths:
  /auth/login:
    `$ref: './auth-service.yaml#/paths/~1login'
"@ | Set-Content -Encoding UTF8 "$CONTRACTS/openapi/gateway-aggregated.yaml"

# ==== PROTO ====
@"
syntax = "proto3";

option java_package = "com.vno.auth.grpc";
service AuthService {
  rpc Login (LoginRequest) returns (LoginResponse);
}
message LoginRequest { string username = 1; string password = 2; }
message LoginResponse { string token = 1; }
"@ | Set-Content -Encoding UTF8 "$CONTRACTS/proto/auth.proto"

@"
syntax = "proto3";

option java_package = "com.vno.user.grpc";
service UserService {
  rpc CreateUser (CreateUserRequest) returns (User);
}
message CreateUserRequest { string username = 1; string email = 2; }
message User { string id = 1; string username = 2; }
"@ | Set-Content -Encoding UTF8 "$CONTRACTS/proto/user.proto"

# ==== ASYNCAPI ====
@"
asyncapi: 3.0.0
info:
  title: User Events
  version: 1.0.0
servers:
  kafka-local:
    url: localhost:9092
    protocol: kafka
channels:
  user.created:
    messages:
      userCreated:
        payload:
          type: object
          properties:
            id: { type: string }
"@ | Set-Content -Encoding UTF8 "$CONTRACTS/asyncapi/user-events.yaml"

Write-Host "HOÀN TẤT! Kiểm tra: $CONTRACTS" -ForegroundColor Green
Get-ChildItem -Recurse $CONTRACTS | Format-Table Name, FullName