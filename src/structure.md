```md
vno/
└── back-end/
    ├── .gitignore
    ├── settings.gradle
    ├── build.gradle
    ├── docker-compose.yml
    ├── Dockerfile.base
    ├── README.md
    ├── gradle/
    │   └── wrapper/
    │       └── gradle-wrapper.properties
    ├── gradlew
    ├── gradlew.bat
    └── src/
        ├── api-gateway/
        │   ├── .gitignore
        │   ├── gradlew / gradlew.bat
        │   ├── gradle/wrapper/gradle-wrapper.properties
        │   ├── build.gradle
        │   ├── settings.gradle
        │   └── src/
        │       ├── main/
        │       │   ├── java/com/vno/gateway/
        │       │   │   ├── ApiGatewayApplication.java
        │       │   │   ├── config/GatewayConfig.java
        │       │   │   ├── filter/AuthFilter.java
        │       │   │   └── controller/HealthCheckController.java
        │       │   └── resources/application.yml
        │       └── test/java/com/vno/gateway/ApiGatewayApplicationTests.java

        ├── auth-service/
        │   ├── .gitignore
        │   ├── gradlew / gradlew.bat
        │   ├── gradle/wrapper/gradle-wrapper.properties
        │   ├── build.gradle
        │   ├── settings.gradle
        │   └── src/
        │       ├── main/java/com/vno/auth/
        │       │   ├── AuthServiceApplication.java
        │       │   ├── config/SecurityConfig.java
        │       │   ├── controller/AuthController.java
        │       │   ├── service/AuthService.java
        │       │   ├── dto/{AuthRequest, AuthResponse}.java
        │       │   └── model/User.java
        │       └── resources/application.yml

        ├── user-service/
        │   ├── .gitignore
        │   ├── gradlew / gradlew.bat
        │   ├── gradle/wrapper/gradle-wrapper.properties
        │   ├── build.gradle
        │   ├── settings.gradle
        │   └── src/
        │       ├── main/java/com/vno/user/
        │       │   ├── UserServiceApplication.java
        │       │   ├── controller/UserController.java
        │       │   ├── service/UserService.java
        │       │   ├── model/UserEntity.java
        │       │   ├── dto/UserDTO.java
        │       │   └── repository/UserRepository.java
        │       └── resources/application.yml

        ├── organization-service/
        │   ├── .gitignore
        │   ├── gradlew / gradlew.bat
        │   ├── gradle/wrapper/gradle-wrapper.properties
        │   ├── build.gradle
        │   ├── settings.gradle
        │   └── src/
        │       ├── main/java/com/vno/organization/
        │       │   ├── OrganizationServiceApplication.java
        │       │   ├── controller/OrganizationController.java
        │       │   ├── service/OrganizationService.java
        │       │   ├── model/{Organization, Member}.java
        │       │   ├── dto/{OrganizationDTO, MemberDTO}.java
        │       │   └── repository/{OrganizationRepository, MemberRepository}.java
        │       └── resources/application.yml

        ├── note-service/
        │   ├── .gitignore
        │   ├── gradlew / gradlew.bat
        │   ├── gradle/wrapper/gradle-wrapper.properties
        │   ├── build.gradle
        │   ├── settings.gradle
        │   └── src/
        │       ├── main/java/com/vno/note/
        │       │   ├── NoteServiceApplication.java
        │       │   ├── controller/NoteController.java
        │       │   ├── service/NoteService.java
        │       │   ├── model/{NoteEntity, TagEntity}.java
        │       │   ├── dto/{NoteDTO, TagDTO}.java
        │       │   ├── repository/{NoteRepository, TagRepository}.java
        │       │   └── event/{NoteCreatedEvent, NoteUpdatedEvent, NoteDeletedEvent}.java
        │       └── resources/application.yml

        ├── tag-service/
        │   ├── .gitignore
        │   ├── gradlew / gradlew.bat
        │   ├── gradle/wrapper/gradle-wrapper.properties
        │   ├── build.gradle
        │   ├── settings.gradle
        │   └── src/
        │       ├── main/java/com/vno/tag/
        │       │   ├── TagServiceApplication.java
        │       │   ├── controller/TagController.java
        │       │   ├── service/TagService.java
        │       │   ├── model/TagEntity.java
        │       │   ├── dto/TagDTO.java
        │       │   └── repository/TagRepository.java
        │       └── resources/application.yml

        ├── notification-service/
        │   ├── .gitignore
        │   ├── gradlew / gradlew.bat
        │   ├── gradle/wrapper/gradle-wrapper.properties
        │   ├── build.gradle
        │   ├── settings.gradle
        │   └── src/
        │       ├── main/java/com/vno/notification/
        │       │   ├── NotificationServiceApplication.java
        │       │   ├── controller/{NotificationController, NotificationWSHandler}.java
        │       │   ├── service/NotificationService.java
        │       │   ├── dto/NotificationDTO.java
        │       │   ├── model/NotificationEntity.java
        │       │   ├── repository/NotificationRepository.java
        │       │   └── event/NotificationCreatedEvent.java
        │       └── resources/application.yml

        ├── service-discovery/
        │   ├── .gitignore
        │   ├── gradlew / gradlew.bat
        │   ├── gradle/wrapper/gradle-wrapper.properties
        │   ├── build.gradle
        │   ├── settings.gradle
        │   └── src/
        │       ├── main/java/com/vno/servicediscovery/
        │       │   ├── ServiceDiscoveryApplication.java
        │       │   └── config/EurekaConfig.java
        │       └── resources/application.yml

        ├── config-server/
        │   ├── .gitignore
        │   ├── gradlew / gradlew.bat
        │   ├── gradle/wrapper/gradle-wrapper.properties
        │   ├── build.gradle
        │   ├── settings.gradle
        │   └── src/
        │       ├── main/java/com/vno/configserver/
        │       │   ├── ConfigServerApplication.java
        │       │   └── config/ConfigServerConfig.java
        │       └── resources/application.yml

        └── common/
            ├── .gitignore
            ├── gradlew / gradlew.bat
            ├── gradle/wrapper/gradle-wrapper.properties
            ├── build.gradle
            ├── settings.gradle
            └── src/
                ├── main/java/com/vno/common/
                │   ├── dto/ApiResponse.java
                │   ├── exception/BusinessException.java
                │   ├── constants/AppConstants.java
                │   └── util/DateUtils.java
                └── test/java/com/vno/common/CommonModuleTests.java

```
