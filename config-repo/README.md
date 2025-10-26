This is a local config repository used by the Spring Cloud Config Server for the project.

Structure:
- `application.yml` : default properties for all apps
- `auth-service.yml` : overrides for the auth-service
- `api-gateway.yml` : overrides for the api-gateway

To use the local repo, the config server is configured with `spring.cloud.config.server.native.search-locations: file:../config-repo`.

You can edit these files and the config server will serve updated properties (may require refresh depending on client setup).