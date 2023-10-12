plugins {
    java
    kotlin("jvm") version "1.9.10"
    id("fabric-loom") version "1.4-SNAPSHOT"
}

version = project.property("mod_version")!!
group = project.property("maven_group")!!

base {
    archivesName.set(project.property("archives_base_name").toString())
}

repositories {
    // Add repositories to retrieve artifacts from in here.
    // You should only use this when depending on other mods because
    // Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
    // See https://docs.gradle.org/current/userguide/declaring_repositories.html
    // for more information about repositories.

    flatDir {
        dirs("libs")
    }
}

loom {
    splitEnvironmentSourceSets()

    mods {
        create("modid") {
            sourceSet(sourceSets.main.get())
            sourceSet(sourceSets.getByName("client"))
        }
    }
}

dependencies {
    fun modLib(module: Any) {
        modImplementation(module)
        include(module)
    }

    fun library(module: Any) {
        implementation(module)
        include(module)
    }

    // To change the versions see the gradle.properties file
    minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")
    mappings("net.fabricmc:yarn:${project.property("yarn_mappings")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${project.property("loader_version")}")
    // library(fabricApi.module("fabric-resource-loader-v0", "${project.property("fabric_version")}"))

    // Fabric API. This is technically optional, but you probably want it anyway.
    // modImplementation("net.fabricmc.fabric-api:fabric-api:${project.property("fabric_version")}")

    // Kotlin adapter for fabric
    modImplementation("net.fabricmc:fabric-language-kotlin:${project.property("fabric_kotlin_version")}")

    // Kotlin standard library
    library(kotlin("stdlib", "${project.property("kotlin_version")}"))

    // Event Manager
    library(files("libs\\rush-${project.property("rush_version")}.jar"))

    // Command API
    library(fabricApi.module("fabric-command-api-v2", "${project.property("fabric_version")}"))

    // Uncomment the following line to enable the deprecated Fabric API modules.
    // These are included in the Fabric API production distribution and allow you to update your mod to the latest modules at a later more convenient time.

    // modImplementation("net.fabricmc.fabric-api:fabric-api-deprecated:${project.property("fabric_version")}")
}

java {
    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
    // if it is present.
    // If you remove this line, sources will not be generated.
    withSourcesJar()

    targetCompatibility = JavaVersion.VERSION_17
    sourceCompatibility = JavaVersion.VERSION_17
}

kotlin {
    // Minecraft 1.18 (1.18-pre2) upwards uses Java 17.
    jvmToolchain(17)
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand(mapOf("version" to version))
        }
    }

    withType<JavaCompile>().configureEach {
        // Minecraft 1.18 (1.18-pre2) upwards uses Java 17.
        options.release.set(17)
    }

    jar {
        from("LICENSE") {
            rename { "${it}_${base.archivesName.get()}" }
        }
    }
}