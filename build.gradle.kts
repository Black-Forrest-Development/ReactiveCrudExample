plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.21"
    id("org.jetbrains.kotlin.kapt") version "1.6.21"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.6.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.micronaut.application") version "3.5.3"
    id("com.google.cloud.tools.jib") version "2.8.0"
    id("io.micronaut.test-resources") version "3.5.3"
}

version = "0.1"
group = "de.sambalmueslie.sample"

val kotlinVersion = project.properties.get("kotlinVersion")
repositories {
    mavenCentral()
}

dependencies {


    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut.cache:micronaut-cache-caffeine")
    runtimeOnly("ch.qos.logback:logback-classic")

    // validation
    kapt("io.micronaut:micronaut-http-validation")
    implementation("io.micronaut:micronaut-validation")
    // data jdbc
    runtimeOnly("io.micronaut.sql:micronaut-jdbc-hikari")
    kapt("io.micronaut.data:micronaut-data-processor")
//    compileOnly("jakarta.persistence:jakarta.persistence-api:3.0.0")
    runtimeOnly("org.postgresql:postgresql")
    implementation("jakarta.annotation:jakarta.annotation-api")
    compileOnly("jakarta.persistence:jakarta.persistence-api:3.1.0")
    // flyway
    implementation("io.micronaut.flyway:micronaut-flyway")
    // R2DBC
    implementation("io.micronaut.data:micronaut-data-r2dbc")
    runtimeOnly("org.postgresql:r2dbc-postgresql")
    // testcontainer
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:r2dbc")
    testImplementation("org.testcontainers:testcontainers")
    // kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.micronaut.kotlin:micronaut-kotlin-extension-functions")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    // openapi / swagger
    kapt("io.micronaut.openapi:micronaut-openapi")
    implementation("io.swagger.core.v3:swagger-annotations")
    // reactor
    implementation("io.micronaut.reactor:micronaut-reactor")
    implementation("io.micronaut.reactor:micronaut-reactor-http-client")


}


application {
    mainClass.set("de.sambalmueslie.sample.ApplicationKt")
}
java {
    sourceCompatibility = JavaVersion.toVersion("17")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
    test {
        useJUnitPlatform()
    }
    jib {
        to {
            image = "gcr.io/myapp/jib-image"
        }
    }
}
graalvmNative.toolchainDetection.set(false)


micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(false)
        annotations("de.sambalmueslie.sample.*")
    }
    testResources {
        additionalModules.add("r2dbc-postgresql")
    }
}



