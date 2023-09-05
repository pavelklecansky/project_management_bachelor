
plugins {
    java
    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.2"
}

val mapstructVersion = "1.5.5.Final"
val mapstructSpringExtensionsVersion = "1.0.2"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":frontend"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("org.projectlombok:lombok:1.18.22")
    implementation("org.modelmapper:modelmapper:2.4.4")
    implementation("org.liquibase:liquibase-core")
    implementation("org.mapstruct:mapstruct:${mapstructVersion}")
    annotationProcessor("org.mapstruct:mapstruct-processor:${mapstructVersion}")
    implementation("org.mapstruct.extensions.spring:mapstruct-spring-annotations:${mapstructSpringExtensionsVersion}")
    annotationProcessor("org.mapstruct.extensions.spring:mapstruct-spring-extensions:${mapstructSpringExtensionsVersion}")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
    runtimeOnly("org.postgresql:postgresql")
    compileOnly("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.4.2.Final")
    testImplementation("io.rest-assured:rest-assured:5.3.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql:1.18.3")
    testImplementation("org.apache.httpcomponents:httpclient:4.5.14")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
}

