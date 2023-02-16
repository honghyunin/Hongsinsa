import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.8"
	id("io.spring.dependency-management") version "1.0.6.RELEASE"
	kotlin("jvm") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22"
	kotlin("plugin.jpa") version "1.7.22"
	kotlin("kapt") version "1.6.21"
}

group = "commerce"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

val querydslVersion = "5.0.0"
apply(plugin = "java")
apply(plugin = "kotlin-kapt")

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.security:spring-security-config")

	runtimeOnly("com.h2database:h2")

	kapt("jakarta.persistence:jakarta.persistence-api")
	kapt("jakarta.annotation:jakarta.annotation-api")
	implementation("com.querydsl:querydsl-jpa:$querydslVersion")
	kapt("com.querydsl:querydsl-apt:$querydslVersion:jpa")
	implementation("io.jsonwebtoken:jjwt:0.9.1")
	kapt("org.springframework.boot:spring-boot-configuration-processor")

	implementation("org.springframework.boot:spring-boot-starter-batch")
	testImplementation("org.springframework.batch:spring-batch-test")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")

	testImplementation("io.kotest:kotest-extensions-spring:4.4.3")

	testImplementation("io.kotest:kotest-runner-junit5-jvm:4.4.3")
	testImplementation("io.kotest:kotest-assertions-core-jvm:4.4.3")

	testImplementation("io.mockk:mockk:1.12.4")

}
extra["kotlin-coroutines.version"] = "1.6.0"

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
