import org.gradle.kotlin.dsl.execution.ProgramText.Companion.from
import java.util.*

val minecraftVersion: String by project
val yarn: String by project
val jsr: String by project
val junit: String by project

plugins {
    id("com.jfrog.bintray") version ("1.8.5")
    id("fabric-loom") version("0.5-SNAPSHOT")
    id("java-library")
    id("maven")
    id("maven-publish")
}

apply("project.gradle")

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

group = "user11681"
version = "0.5.0"

repositories {
    mavenLocal()

    maven("https://maven.fabricmc.net/")
    maven("https://jitpack.io")
    maven("https://raw.githubusercontent.com/Devan-Kerman/Devan-Repo/master")
    maven("https://dl.bintray.com/ladysnake/libs")
    maven("https://maven.jamieswhiteshirt.com/libs-release")
    maven("https://maven.blamejared.com")
    maven("https://mod-buildcraft.com/maven")
    maven("https://dl.bintray.com/zundrel/wrenchable")
    maven("https://maven.dblsaiko.net/")
    maven("https://dl.bintray.com/earthcomputer/mods")
    maven("https://server.bbkr.space/artifactory/libs-release")
}

dependencies {
    minecraft("com.mojang:minecraft:${minecraftVersion}")
    mappings("net.fabricmc:yarn:${minecraftVersion}+build.${yarn}:v2")
    modImplementation("net.fabricmc:fabric-loader:+")

    compileOnly("jsr")

    testImplementation("junit")

//    modImplementation "com.jamieswhiteshirt:developer-mode:1.0.15"
}

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, "seconds")
}

tasks.getByName<ProcessResources>("processResources") {
    inputs.property("version", version)
}

// ensure that the encoding is set to UTF-8, no matter what the system default is
// this fixes some edge cases with special characters not displaying correctly
// see http://yodaconditions.net/blog/fix-for-java-file-encoding-problems-with-gradle.html
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

// Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
// if it is present.
// If you remove this task, sources will not be generated.
val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

task("jar") {
    from("LICENSE")
}

/*
bintray {
    user = System.getenv("BINTRAY_USER")
    key = System.getenv("BINTRAY_API_KEY")
    setPublications("bintray")

    pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
        repo = "maven"
        name = "limitless"
        licenses = ["LGPL-3.0"]
        vcsUrl = "https://github.com/user11681/limitless.git"

        version {
            name = version
            released = Date()
        }
    })
}

publishing {
    publications {
        bintray(MavenPublication) {
            groupId group
            artifactId name
            version(version)

            artifact(remapJar) {
                builtBy = remapJar
            }

            artifact(sourcesJar) {
                builtBy = remapSourcesJar
            }
        }
    }

    repositories {
        mavenLocal()
    }
}
*/