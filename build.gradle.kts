plugins {
	kotlin("jvm") version "2.1.0"
	kotlin("kapt") version "2.1.0"
	kotlin("plugin.spring") version "2.1.0"
	kotlin("plugin.jpa") version "2.1.0"
	id("org.springframework.boot") version "3.4.1"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.asciidoctor.jvm.convert") version "4.0.2"
}

val snippetsDir = layout.buildDirectory.dir("generated-snippets")

fun getGitHash(): String {
	return providers.exec {
		commandLine("git", "rev-parse", "--short", "HEAD")
	}.standardOutput.asText.get().trim()
}

group = "com.tutomato.commerce"
version = getGitHash()

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
		jvmToolchain(17)
	}
}

repositories {
	mavenCentral()
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:2024.0.0")
	}
}

dependencies {
	// Kotlin
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Spring
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")

    // DB
	runtimeOnly("com.mysql:mysql-connector-j")

    // Test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("io.mockk:mockk:1.13.10")
	testImplementation("org.mockito.kotlin:mockito-kotlin:5.3.1")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:mysql")
	testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
	testImplementation(kotlin("test"))

}

tasks.withType<Test> {
	useJUnitPlatform()
	systemProperty("user.timezone", "UTC")
}

tasks.test {
	outputs.dir(snippetsDir)
	useJUnitPlatform()
	systemProperty("user.timezone", "UTC")
}

tasks.asciidoctor {
	doFirst {	// asciidoctor 수행 전 실행
		delete(project.extra["docsOutputDir"]!!)	// 기존 API 문서 삭제
		delete(project.extra["projectDocsDir"]!!)	// 기존에 복사된 API 문서 삭제
	}
	inputs.dir(project.extra["snippetsDir"]!!)
	dependsOn(tasks.test)
	doLast {	// asciidoctor 수행 완료 후 실행
		copy {	// 생성된 API 문서를 지정된 경로에 복사
			from(project.extra["docsOutputDir"]!!)
			into(project.extra["projectDocsDir"]!!)
		}
	}
}