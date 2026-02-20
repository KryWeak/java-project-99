plugins {
	application
	checkstyle
	jacoco
	id("org.springframework.boot") version "3.3.8"
	id("io.spring.dependency-management") version "1.1.7"
	id("io.freefair.lombok") version "8.6"
	id("org.sonarqube") version "7.1.0.6387"
}

application {
	mainClass.set("hexlet.code.AppApplication")
}

group = "hexlet.code"
version = "0.0.1-SNAPSHOT"

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-devtools")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	runtimeOnly("com.h2database:h2")
	implementation("org.hibernate.validator:hibernate-validator:8.0.1.Final")
	testImplementation(platform("org.junit:junit-bom:5.10.0"))
	testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
	testImplementation("net.javacrumbs.json-unit:json-unit-assertj:3.2.2")
	implementation("net.datafaker:datafaker:2.0.1")
	implementation("org.instancio:instancio-junit:3.3.0")
	implementation("org.mapstruct:mapstruct:1.5.5.Final")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
	implementation("org.openapitools:jackson-databind-nullable:0.2.6")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	testImplementation("org.springframework.security:spring-security-test")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:2.8.5")
	implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.8.5")
	implementation("org.postgresql:postgresql:42.7.1")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.jacocoTestReport {
	reports {
		xml.required = true
	}
}

buildscript {
	repositories {
		mavenCentral()
	}
}

sonar {
	properties {
		property("sonar.projectKey", "KryWeak_java-project-99")
		property("sonar.organization", "kryweak")
	}
}