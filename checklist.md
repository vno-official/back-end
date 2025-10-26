# Service startup checklist

Recommended start order (with reasons):
1) Databases and infra
   - PostgreSQL (used by auth, user, note, org, tag services)
   - Kafka (optional; required if enabling async notifications)
2) Service Discovery
   - Eureka Server: http://localhost:8761/
3) Config Server
   - Config Server (native file backend pointing to config-repo)
4) Core services (register to Eureka)
   - auth-service
   - user-service
   - organization-service
   - note-service
   - tag-service
   - notification-service
5) API Gateway
   - api-gateway: http://localhost:8080

Verification steps:
- Open Eureka dashboard and confirm all instances are UP
- From gateway, GET /actuator/health -> UP
- Obtain JWT from auth-service via /api/auth/login
- Call a protected endpoint via gateway with Authorization: Bearer {{JWT_TOKEN}}
- Open Swagger UIs via gateway (see api-links.md)
- Test WebSocket/SSE at /ws/notifications if enabled
