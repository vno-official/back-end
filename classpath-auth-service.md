runtimeClasspath - Runtime classpath of source set 'main'.
+--- org.springframework.boot:spring-boot-starter-validation -> 3.5.6
|    +--- org.springframework.boot:spring-boot-starter:3.5.6
|    |    +--- org.springframework.boot:spring-boot:3.5.6
|    |    |    +--- org.springframework:spring-core:6.2.11
|    |    |    |    \--- org.springframework:spring-jcl:6.2.11
|    |    |    \--- org.springframework:spring-context:6.2.11
|    |    |         +--- org.springframework:spring-aop:6.2.11
|    |    |         |    +--- org.springframework:spring-beans:6.2.11
|    |    |         |    |    \--- org.springframework:spring-core:6.2.11 (*)  
|    |    |         |    \--- org.springframework:spring-core:6.2.11 (*)       
|    |    |         +--- org.springframework:spring-beans:6.2.11 (*)
|    |    |         +--- org.springframework:spring-core:6.2.11 (*)
|    |    |         +--- org.springframework:spring-expression:6.2.11
|    |    |         |    \--- org.springframework:spring-core:6.2.11 (*)       
|    |    |         \--- io.micrometer:micrometer-observation:1.14.11 -> 1.15.4
|    |    |              \--- io.micrometer:micrometer-commons:1.15.4
|    |    +--- org.springframework.boot:spring-boot-autoconfigure:3.5.6        
|    |    |    \--- org.springframework.boot:spring-boot:3.5.6 (*)
|    |    +--- org.springframework.boot:spring-boot-starter-logging:3.5.6      
|    |    |    +--- ch.qos.logback:logback-classic:1.5.18
|    |    |    |    +--- ch.qos.logback:logback-core:1.5.18
|    |    |    |    \--- org.slf4j:slf4j-api:2.0.17
|    |    |    +--- org.apache.logging.log4j:log4j-to-slf4j:2.24.3
|    |    |    |    +--- org.apache.logging.log4j:log4j-api:2.24.3
|    |    |    |    \--- org.slf4j:slf4j-api:2.0.16 -> 2.0.17
|    |    |    \--- org.slf4j:jul-to-slf4j:2.0.17
|    |    |         \--- org.slf4j:slf4j-api:2.0.17
|    |    +--- jakarta.annotation:jakarta.annotation-api:2.1.1
|    |    +--- org.springframework:spring-core:6.2.11 (*)
|    |    \--- org.yaml:snakeyaml:2.4
|    +--- org.apache.tomcat.embed:tomcat-embed-el:10.1.46
|    \--- org.hibernate.validator:hibernate-validator:8.0.3.Final
|         +--- jakarta.validation:jakarta.validation-api:3.0.2
|         +--- org.jboss.logging:jboss-logging:3.4.3.Final -> 3.6.1.Final
|         \--- com.fasterxml:classmate:1.5.1 -> 1.7.0
+--- org.springframework.boot:spring-boot-starter-actuator -> 3.5.6
|    +--- org.springframework.boot:spring-boot-starter:3.5.6 (*)
|    +--- org.springframework.boot:spring-boot-actuator-autoconfigure:3.5.6
|    |    +--- com.fasterxml.jackson.core:jackson-databind:2.19.2
|    |    |    +--- com.fasterxml.jackson.core:jackson-annotations:2.19.2
|    |    |    |    \--- com.fasterxml.jackson:jackson-bom:2.19.2
|    |    |    |         +--- com.fasterxml.jackson.core:jackson-annotations:2.19.2 (c)
|    |    |    |         +--- com.fasterxml.jackson.core:jackson-core:2.19.2 (c)
|    |    |    |         +--- com.fasterxml.jackson.core:jackson-databind:2.19.2 (c)
|    |    |    |         +--- com.fasterxml.jackson.dataformat:jackson-dataformat-toml:2.19.2 (c)
|    |    |    |         +--- com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.19.2 (c)
|    |    |    |         +--- com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.19.2 (c)
|    |    |    |         +--- com.fasterxml.jackson.module:jackson-module-parameter-names:2.19.2 (c)
|    |    |    |         \--- com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.19.2 (c)
|    |    |    +--- com.fasterxml.jackson.core:jackson-core:2.19.2
|    |    |    |    \--- com.fasterxml.jackson:jackson-bom:2.19.2 (*)
|    |    |    \--- com.fasterxml.jackson:jackson-bom:2.19.2 (*)
|    |    +--- com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.19.2
|    |    |    +--- com.fasterxml.jackson.core:jackson-annotations:2.19.2 (*)
|    |    |    +--- com.fasterxml.jackson.core:jackson-core:2.19.2 (*)
|    |    |    +--- com.fasterxml.jackson.core:jackson-databind:2.19.2 (*)
|    |    |    \--- com.fasterxml.jackson:jackson-bom:2.19.2 (*)
|    |    +--- org.springframework.boot:spring-boot:3.5.6 (*)
|    |    +--- org.springframework.boot:spring-boot-actuator:3.5.6
|    |    |    \--- org.springframework.boot:spring-boot:3.5.6 (*)
|    |    \--- org.springframework.boot:spring-boot-autoconfigure:3.5.6 (*)
|    +--- io.micrometer:micrometer-observation:1.15.4 (*)
|    \--- io.micrometer:micrometer-jakarta9:1.15.4
|         +--- io.micrometer:micrometer-core:1.15.4
|         |    +--- io.micrometer:micrometer-commons:1.15.4
|         |    +--- io.micrometer:micrometer-observation:1.15.4 (*)
|         |    +--- org.hdrhistogram:HdrHistogram:2.2.2
|         |    \--- org.latencyutils:LatencyUtils:2.0.3
|         +--- io.micrometer:micrometer-commons:1.15.4
|         \--- io.micrometer:micrometer-observation:1.15.4 (*)
+--- io.micrometer:micrometer-tracing-bridge-brave -> 1.5.4
|    +--- io.micrometer:micrometer-tracing:1.5.4
|    |    +--- io.micrometer:micrometer-observation:1.15.4 (*)
|    |    +--- io.micrometer:context-propagation:1.1.3
|    |    \--- aopalliance:aopalliance:1.0
|    +--- org.slf4j:slf4j-api:1.7.36 -> 2.0.17
|    +--- io.zipkin.brave:brave:6.2.0 -> 6.1.0
|    +--- io.zipkin.brave:brave-context-slf4j:6.2.0 -> 6.1.0
|    |    \--- io.zipkin.brave:brave:6.1.0
|    +--- io.zipkin.brave:brave-instrumentation-http:6.2.0 -> 6.1.0
|    |    \--- io.zipkin.brave:brave:6.1.0
|    +--- io.zipkin.aws:brave-propagation-aws:1.3.0
|    |    \--- io.zipkin.brave:brave:6.1.0
|    \--- io.zipkin.contrib.brave-propagation-w3c:brave-propagation-tracecontext:0.2.0
+--- io.zipkin.reporter2:zipkin-reporter-brave -> 3.5.1
|    \--- io.zipkin.reporter2:zipkin-reporter:3.5.1
+--- org.mapstruct:mapstruct:1.5.5.Final
+--- project :services:common
|    +--- org.springframework.boot:spring-boot-starter-validation -> 3.5.6 (*)
|    +--- org.springframework.boot:spring-boot-starter-actuator -> 3.5.6 (*)
|    +--- io.micrometer:micrometer-tracing-bridge-brave -> 1.5.4 (*)
|    +--- io.zipkin.reporter2:zipkin-reporter-brave -> 3.5.1 (*)
|    +--- org.mapstruct:mapstruct:1.5.5.Final
|    +--- org.springframework.boot:spring-boot-starter-data-jpa -> 3.5.6
|    |    +--- org.springframework.boot:spring-boot-starter:3.5.6 (*)
|    |    +--- org.springframework.boot:spring-boot-starter-jdbc:3.5.6
|    |    |    +--- org.springframework.boot:spring-boot-starter:3.5.6 (*)
|    |    |    +--- com.zaxxer:HikariCP:6.3.3
|    |    |    |    \--- org.slf4j:slf4j-api:2.0.17
|    |    |    \--- org.springframework:spring-jdbc:6.2.11
|    |    |         +--- org.springframework:spring-beans:6.2.11 (*)
|    |    |         +--- org.springframework:spring-core:6.2.11 (*)
|    |    |         \--- org.springframework:spring-tx:6.2.11
|    |    |              +--- org.springframework:spring-beans:6.2.11 (*)
|    |    |              \--- org.springframework:spring-core:6.2.11 (*)
|    |    +--- org.hibernate.orm:hibernate-core:6.6.29.Final
|    |    |    +--- jakarta.persistence:jakarta.persistence-api:3.1.0
|    |    |    +--- jakarta.transaction:jakarta.transaction-api:2.0.1
|    |    |    +--- org.jboss.logging:jboss-logging:3.5.0.Final -> 3.6.1.Final
|    |    |    +--- org.hibernate.common:hibernate-commons-annotations:7.0.3.Final
|    |    |    +--- io.smallrye:jandex:3.2.0
|    |    |    +--- com.fasterxml:classmate:1.5.1 -> 1.7.0
|    |    |    +--- net.bytebuddy:byte-buddy:1.15.11 -> 1.17.7
|    |    |    +--- jakarta.xml.bind:jakarta.xml.bind-api:4.0.0 -> 4.0.2
|    |    |    |    \--- jakarta.activation:jakarta.activation-api:2.1.3 -> 2.1.4
|    |    |    +--- org.glassfish.jaxb:jaxb-runtime:4.0.2 -> 4.0.5
|    |    |    |    \--- org.glassfish.jaxb:jaxb-core:4.0.5
|    |    |    |         +--- jakarta.xml.bind:jakarta.xml.bind-api:4.0.2 (*)
|    |    |    |         +--- jakarta.activation:jakarta.activation-api:2.1.3 -> 2.1.4
|    |    |    |         +--- org.eclipse.angus:angus-activation:2.0.2
|    |    |    |         |    \--- jakarta.activation:jakarta.activation-api:2.1.3 -> 2.1.4
|    |    |    |         +--- org.glassfish.jaxb:txw2:4.0.5
|    |    |    |         \--- com.sun.istack:istack-commons-runtime:4.1.2
|    |    |    +--- jakarta.inject:jakarta.inject-api:2.0.1
|    |    |    \--- org.antlr:antlr4-runtime:4.13.0
|    |    +--- org.springframework.data:spring-data-jpa:3.5.4
|    |    |    +--- org.springframework.data:spring-data-commons:3.5.4
|    |    |    |    +--- org.springframework:spring-core:6.2.11 (*)
|    |    |    |    +--- org.springframework:spring-beans:6.2.11 (*)
|    |    |    |    \--- org.slf4j:slf4j-api:2.0.2 -> 2.0.17
|    |    |    +--- org.springframework:spring-orm:6.2.11
|    |    |    |    +--- org.springframework:spring-beans:6.2.11 (*)
|    |    |    |    +--- org.springframework:spring-core:6.2.11 (*)
|    |    |    |    +--- org.springframework:spring-jdbc:6.2.11 (*)
|    |    |    |    \--- org.springframework:spring-tx:6.2.11 (*)
|    |    |    +--- org.springframework:spring-context:6.2.11 (*)
|    |    |    +--- org.springframework:spring-aop:6.2.11 (*)
|    |    |    +--- org.springframework:spring-tx:6.2.11 (*)
|    |    |    +--- org.springframework:spring-beans:6.2.11 (*)
|    |    |    +--- org.springframework:spring-core:6.2.11 (*)
|    |    |    +--- org.antlr:antlr4-runtime:4.13.0
|    |    |    +--- jakarta.annotation:jakarta.annotation-api:2.0.0 -> 2.1.1
|    |    |    \--- org.slf4j:slf4j-api:2.0.2 -> 2.0.17
|    |    \--- org.springframework:spring-aspects:6.2.11
|    |         \--- org.aspectj:aspectjweaver:1.9.22.1 -> 1.9.24
|    +--- org.springframework.boot:spring-boot-starter-security -> 3.5.6
|    |    +--- org.springframework.boot:spring-boot-starter:3.5.6 (*)
|    |    +--- org.springframework:spring-aop:6.2.11 (*)
|    |    +--- org.springframework.security:spring-security-config:6.5.5
|    |    |    +--- org.springframework.security:spring-security-core:6.5.5
|    |    |    |    +--- org.springframework.security:spring-security-crypto:6.5.5
|    |    |    |    +--- org.springframework:spring-aop:6.2.11 (*)
|    |    |    |    +--- org.springframework:spring-beans:6.2.11 (*)
|    |    |    |    +--- org.springframework:spring-context:6.2.11 (*)
|    |    |    |    +--- org.springframework:spring-core:6.2.11 (*)
|    |    |    |    +--- org.springframework:spring-expression:6.2.11 (*)
|    |    |    |    \--- io.micrometer:micrometer-observation:1.14.11 -> 1.15.4 (*)
|    |    |    +--- org.springframework:spring-aop:6.2.11 (*)
|    |    |    +--- org.springframework:spring-beans:6.2.11 (*)
|    |    |    +--- org.springframework:spring-context:6.2.11 (*)
|    |    |    \--- org.springframework:spring-core:6.2.11 (*)
|    |    \--- org.springframework.security:spring-security-web:6.5.5
|    |         +--- org.springframework.security:spring-security-core:6.5.5 (*)
|    |         +--- org.springframework:spring-core:6.2.11 (*)
|    |         +--- org.springframework:spring-aop:6.2.11 (*)
|    |         +--- org.springframework:spring-beans:6.2.11 (*)
|    |         +--- org.springframework:spring-context:6.2.11 (*)
|    |         +--- org.springframework:spring-expression:6.2.11 (*)
|    |         \--- org.springframework:spring-web:6.2.11
|    |              +--- org.springframework:spring-beans:6.2.11 (*)
|    |              +--- org.springframework:spring-core:6.2.11 (*)
|    |              \--- io.micrometer:micrometer-observation:1.14.11 -> 1.15.4 (*)
|    +--- com.fasterxml.jackson.core:jackson-databind -> 2.19.2 (*)
|    +--- org.apache.commons:commons-lang3:3.14.0 -> 3.17.0
|    +--- io.jsonwebtoken:jjwt-api:0.12.5
|    +--- org.mapstruct:mapstruct -> 1.5.5.Final
|    +--- io.jsonwebtoken:jjwt-impl:0.12.5
|    |    \--- io.jsonwebtoken:jjwt-api:0.12.5
|    \--- io.jsonwebtoken:jjwt-jackson:0.12.5
|         +--- io.jsonwebtoken:jjwt-api:0.12.5
|         \--- com.fasterxml.jackson.core:jackson-databind:2.12.7.1 -> 2.19.2 (*)
+--- org.springframework.boot:spring-boot-starter-web -> 3.5.6
|    +--- org.springframework.boot:spring-boot-starter:3.5.6 (*)
|    +--- org.springframework.boot:spring-boot-starter-json:3.5.6
|    |    +--- org.springframework.boot:spring-boot-starter:3.5.6 (*)
|    |    +--- org.springframework:spring-web:6.2.11 (*)
|    |    +--- com.fasterxml.jackson.core:jackson-databind:2.19.2 (*)
|    |    +--- com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.19.2
|    |    |    +--- com.fasterxml.jackson.core:jackson-core:2.19.2 (*)
|    |    |    +--- com.fasterxml.jackson.core:jackson-databind:2.19.2 (*)
|    |    |    \--- com.fasterxml.jackson:jackson-bom:2.19.2 (*)
|    |    +--- com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.19.2 (*)
|    |    \--- com.fasterxml.jackson.module:jackson-module-parameter-names:2.19.2
|    |         +--- com.fasterxml.jackson.core:jackson-core:2.19.2 (*)
|    |         +--- com.fasterxml.jackson.core:jackson-databind:2.19.2 (*)
|    |         \--- com.fasterxml.jackson:jackson-bom:2.19.2 (*)
|    +--- org.springframework.boot:spring-boot-starter-tomcat:3.5.6
|    |    +--- jakarta.annotation:jakarta.annotation-api:2.1.1
|    |    +--- org.apache.tomcat.embed:tomcat-embed-core:10.1.46
|    |    +--- org.apache.tomcat.embed:tomcat-embed-el:10.1.46
|    |    \--- org.apache.tomcat.embed:tomcat-embed-websocket:10.1.46
|    |         \--- org.apache.tomcat.embed:tomcat-embed-core:10.1.46
|    +--- org.springframework:spring-web:6.2.11 (*)
|    \--- org.springframework:spring-webmvc:6.2.11
|         +--- org.springframework:spring-aop:6.2.11 (*)
|         +--- org.springframework:spring-beans:6.2.11 (*)
|         +--- org.springframework:spring-context:6.2.11 (*)
|         +--- org.springframework:spring-core:6.2.11 (*)
|         +--- org.springframework:spring-expression:6.2.11 (*)
|         \--- org.springframework:spring-web:6.2.11 (*)
+--- org.springframework.boot:spring-boot-starter-data-jpa -> 3.5.6 (*)
+--- org.springframework.boot:spring-boot-starter-security -> 3.5.6 (*)
+--- org.springframework.cloud:spring-cloud-starter-config -> 4.3.0
|    +--- org.springframework.cloud:spring-cloud-starter:4.3.0
|    |    +--- org.springframework.boot:spring-boot-starter:3.5.0 -> 3.5.6 (*)
|    |    +--- org.springframework.cloud:spring-cloud-context:4.3.0
|    |    |    \--- org.springframework.security:spring-security-crypto:6.5.0 -> 6.5.5
|    |    +--- org.springframework.cloud:spring-cloud-commons:4.3.0
|    |    |    \--- org.springframework.security:spring-security-crypto:6.5.0 -> 6.5.5
|    |    \--- org.bouncycastle:bcprov-jdk18on:1.80
|    +--- org.springframework.cloud:spring-cloud-config-client:4.3.0
|    |    +--- org.springframework.boot:spring-boot-autoconfigure:3.5.0 -> 3.5.6 (*)
|    |    +--- org.springframework.cloud:spring-cloud-starter:4.3.0 (*)
|    |    +--- org.springframework:spring-web:6.2.7 -> 6.2.11 (*)
|    |    +--- com.fasterxml.jackson.core:jackson-annotations:2.19.0 -> 2.19.2 (*)
|    |    +--- com.fasterxml.jackson.core:jackson-databind:2.19.0 -> 2.19.2 (*)
|    |    \--- org.apache.httpcomponents.client5:httpclient5:5.4.4 -> 5.5
|    |         +--- org.apache.httpcomponents.core5:httpcore5:5.3.4 -> 5.3.5
|    |         +--- org.apache.httpcomponents.core5:httpcore5-h2:5.3.4 -> 5.3.5
|    |         |    \--- org.apache.httpcomponents.core5:httpcore5:5.3.5
|    |         \--- org.slf4j:slf4j-api:1.7.36 -> 2.0.17
|    \--- com.fasterxml.jackson.core:jackson-databind:2.19.0 -> 2.19.2 (*)
+--- org.springframework.cloud:spring-cloud-starter-netflix-eureka-client -> 4.3.0
|    +--- org.springframework.cloud:spring-cloud-starter:4.3.0 (*)
|    +--- org.springframework.cloud:spring-cloud-netflix-eureka-client:4.3.0
|    |    +--- com.netflix.eureka:eureka-client:2.0.4
|    |    |    +--- com.netflix.netflix-commons:netflix-eventbus:0.3.0
|    |    |    |    +--- org.slf4j:slf4j-api:1.6.4 -> 2.0.17
|    |    |    |    +--- com.netflix.netflix-commons:netflix-infix:0.3.0
|    |    |    |    |    +--- org.slf4j:slf4j-api:1.6.4 -> 2.0.17
|    |    |    |    |    +--- commons-jxpath:commons-jxpath:1.3
|    |    |    |    |    +--- joda-time:joda-time:2.3
|    |    |    |    |    +--- org.antlr:antlr-runtime:3.4
|    |    |    |    |    |    +--- org.antlr:stringtemplate:3.2.1
|    |    |    |    |    |    |    \--- antlr:antlr:2.7.7
|    |    |    |    |    |    \--- antlr:antlr:2.7.7
|    |    |    |    |    +--- com.google.guava:guava:14.0.1
|    |    |    |    |    \--- com.google.code.gson:gson:2.1 -> 2.13.2
|    |    |    |    |         \--- com.google.errorprone:error_prone_annotations:2.41.0
|    |    |    |    +--- com.netflix.servo:servo-core:0.5.3
|    |    |    |    |    +--- org.slf4j:slf4j-api:1.6.3 -> 2.0.17
|    |    |    |    |    \--- com.google.guava:guava:14.0.1
|    |    |    |    \--- org.apache.commons:commons-math:2.2
|    |    |    +--- javax.annotation:javax.annotation-api:1.2
|    |    |    +--- com.thoughtworks.xstream:xstream:1.4.20
|    |    |    |    \--- io.github.x-stream:mxparser:1.2.2
|    |    |    |         \--- xmlpull:xmlpull:1.1.3.1
|    |    |    +--- jakarta.ws.rs:jakarta.ws.rs-api:3.0.0 -> 3.1.0
|    |    |    +--- jakarta.inject:jakarta.inject-api:2.0.1
|    |    |    +--- jakarta.annotation:jakarta.annotation-api:2.1.0 -> 2.1.1
|    |    |    +--- com.netflix.spectator:spectator-api:1.7.3
|    |    |    |    \--- org.slf4j:slf4j-api:1.7.36 -> 2.0.17
|    |    |    +--- org.slf4j:slf4j-api:1.7.36 -> 2.0.17
|    |    |    +--- org.apache.httpcomponents:httpclient:4.5.3
|    |    |    |    +--- org.apache.httpcomponents:httpcore:4.4.6 -> 4.4.16
|    |    |    |    \--- commons-codec:commons-codec:1.9 -> 1.18.0
|    |    |    +--- commons-configuration:commons-configuration:1.10
|    |    |    |    \--- commons-lang:commons-lang:2.6
|    |    |    +--- com.fasterxml.jackson.core:jackson-annotations:2.13.5 -> 2.19.2 (*)
|    |    |    +--- com.fasterxml.jackson.core:jackson-core:2.13.5 -> 2.19.2 (*)
|    |    |    +--- com.fasterxml.jackson.core:jackson-databind:2.13.5 -> 2.19.2 (*)
|    |    |    \--- org.codehaus.jettison:jettison:1.5.4
|    |    \--- org.apache.httpcomponents.client5:httpclient5:5.4.4 -> 5.5 (*)
|    +--- com.netflix.eureka:eureka-client:2.0.4 (*)
|    \--- org.springframework.cloud:spring-cloud-starter-loadbalancer:4.3.0
|         +--- org.springframework.cloud:spring-cloud-starter:4.3.0 (*)
|         +--- org.springframework.cloud:spring-cloud-loadbalancer:4.3.0
|         |    +--- org.springframework.cloud:spring-cloud-commons:4.3.0 (*)
|         |    +--- org.springframework.cloud:spring-cloud-context:4.3.0 (*)
|         |    +--- io.projectreactor:reactor-core:3.7.6 -> 3.7.11
|         |    |    \--- org.reactivestreams:reactive-streams:1.0.4
|         |    \--- io.projectreactor.addons:reactor-extra:3.5.2 -> 3.5.3
|         |         \--- io.projectreactor:reactor-core:3.5.20 -> 3.7.11 (*)
|         +--- org.springframework.boot:spring-boot-starter-cache:3.5.0 -> 3.5.6
|         |    +--- org.springframework.boot:spring-boot-starter:3.5.6 (*)
|         |    \--- org.springframework:spring-context-support:6.2.11
|         |         +--- org.springframework:spring-beans:6.2.11 (*)
|         |         +--- org.springframework:spring-context:6.2.11 (*)
|         |         \--- org.springframework:spring-core:6.2.11 (*)
|         \--- com.stoyanr:evictor:1.0.0
+--- org.springframework.cloud:spring-cloud-starter-openfeign -> 4.3.0
|    +--- org.springframework.cloud:spring-cloud-starter:4.3.0 (*)
|    +--- org.springframework.cloud:spring-cloud-openfeign-core:4.3.0
|    |    +--- org.springframework.boot:spring-boot-autoconfigure:3.5.0 -> 3.5.6 (*)
|    |    \--- io.github.openfeign:feign-form-spring:13.6
|    |         +--- org.apache.commons:commons-text:1.13.0
|    |         |    \--- org.apache.commons:commons-lang3:3.17.0
|    |         +--- io.github.openfeign:feign-form:13.6
|    |         |    \--- io.github.openfeign:feign-core:13.6
|    |         +--- org.springframework:spring-web:6.1.13 -> 6.2.11 (*)
|    |         \--- commons-fileupload:commons-fileupload:1.5
|    +--- org.springframework:spring-web:6.2.7 -> 6.2.11 (*)
|    +--- org.springframework.cloud:spring-cloud-commons:4.3.0 (*)
|    +--- io.github.openfeign:feign-core:13.6
|    \--- io.github.openfeign:feign-slf4j:13.6
|         +--- io.github.openfeign:feign-core:13.6
|         \--- org.slf4j:slf4j-api:2.0.17
+--- org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0
|    +--- org.springdoc:springdoc-openapi-starter-webmvc-api:2.5.0
|    |    +--- org.springdoc:springdoc-openapi-starter-common:2.5.0
|    |    |    +--- org.springframework.boot:spring-boot-autoconfigure:3.2.4 -> 3.5.6 (*)
|    |    |    \--- io.swagger.core.v3:swagger-core-jakarta:2.2.21
|    |    |         +--- org.apache.commons:commons-lang3:3.14.0 -> 3.17.0
|    |    |         +--- org.slf4j:slf4j-api:2.0.9 -> 2.0.17
|    |    |         +--- io.swagger.core.v3:swagger-annotations-jakarta:2.2.21
|    |    |         +--- io.swagger.core.v3:swagger-models-jakarta:2.2.21
|    |    |         |    \--- com.fasterxml.jackson.core:jackson-annotations:2.16.2 -> 2.19.2 (*)
|    |    |         +--- org.yaml:snakeyaml:2.2 -> 2.4
|    |    |         +--- jakarta.xml.bind:jakarta.xml.bind-api:3.0.1 -> 4.0.2 (*)
|    |    |         +--- jakarta.validation:jakarta.validation-api:3.0.2
|    |    |         +--- com.fasterxml.jackson.core:jackson-annotations:2.16.2 -> 2.19.2 (*)
|    |    |         +--- com.fasterxml.jackson.core:jackson-databind:2.16.2 -> 2.19.2 (*)
|    |    |         +--- com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.16.2 -> 2.19.2
|    |    |         |    +--- com.fasterxml.jackson.core:jackson-databind:2.19.2 (*)
|    |    |         |    +--- org.yaml:snakeyaml:2.4
|    |    |         |    +--- com.fasterxml.jackson.core:jackson-core:2.19.2 (*)
|    |    |         |    \--- com.fasterxml.jackson:jackson-bom:2.19.2 (*)
|    |    |         \--- com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.16.2 -> 2.19.2 (*)
|    |    \--- org.springframework:spring-webmvc:6.1.5 -> 6.2.11 (*)
|    \--- org.webjars:swagger-ui:5.13.0
+--- org.flywaydb:flyway-core -> 11.7.2
|    +--- com.fasterxml.jackson.dataformat:jackson-dataformat-toml:2.15.2 -> 2.19.2
|    |    +--- com.fasterxml.jackson.core:jackson-databind:2.19.2 (*)
|    |    +--- com.fasterxml.jackson.core:jackson-core:2.19.2 (*)
|    |    \--- com.fasterxml.jackson:jackson-bom:2.19.2 (*)
|    \--- com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.2 -> 2.19.2 (*)
+--- org.flywaydb:flyway-database-postgresql -> 11.7.2
|    \--- org.flywaydb:flyway-core:11.7.2 (*)
+--- io.jsonwebtoken:jjwt-api:0.12.5
+--- org.mapstruct:mapstruct -> 1.5.5.Final
+--- org.postgresql:postgresql -> 42.7.7
|    \--- org.checkerframework:checker-qual:3.49.3
+--- com.h2database:h2 -> 2.3.232
+--- io.jsonwebtoken:jjwt-impl:0.12.5 (*)
\--- io.jsonwebtoken:jjwt-jackson:0.12.5 (*)