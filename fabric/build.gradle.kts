import net.fabricmc.loom.api.LoomGradleExtensionAPI

plugins {
    id("com.github.johnrengelman.shadow")
}

architectury {
    platformSetupLoomIde()
    fabric()
}

loom {
    accessWidenerPath.set(
        project(":common")
            .extensions
            .getByType<LoomGradleExtensionAPI>()
            .accessWidenerPath
    )
}

val common by configurations.creating {
    isCanBeResolved = true
    isCanBeConsumed = false
}

configurations.named("compileClasspath") { extendsFrom(common) }
configurations.named("runtimeClasspath") { extendsFrom(common) }
configurations.named("developmentFabric") { extendsFrom(common) }

val shadowBundle by configurations.creating {
    isCanBeResolved = true
    isCanBeConsumed = false
}

dependencies {
    modImplementation("net.fabricmc:fabric-loader:${rootProject.extra["fabric_loader_version"]}")

    // Fabric API. This is technically optional, but you probably want it anyway.
    modImplementation("net.fabricmc.fabric-api:fabric-api:${project.findProperty("fabric_api_version")}")

    // Fabric Mod Menu
    modImplementation("com.terraformersmc:modmenu:${project.findProperty("modmenu_version")}")
    // GeckoLib
    modImplementation("software.bernie.geckolib:geckolib-fabric-${project.findProperty("minecraft_version")}:${project.findProperty("geckolib_version")}")
    // too-many-shortcuts
    modImplementation("maven.modrinth:too-many-shortcuts:${project.findProperty("too_many_shortcuts_version")}")
    // Fabric Language Kotlin 它是too-many-shortcuts的前置，不是我们模组的前置，在这里只是为了让too-many-shortcuts正常加载
    modImplementation("net.fabricmc:fabric-language-kotlin:${project.findProperty("fabric_language_kotlin_version")}")
    // Ayame PaperDoll
    modImplementation("maven.modrinth:ayame-paperdoll:${project.findProperty("ayame_paperdoll_version")}-fabric")

    // Rhino库
    implementation("org.mozilla:rhino:${project.findProperty("rhino_version")}")
    include("org.mozilla:rhino:${project.findProperty("rhino_version")}")
    // Mocha 库
    implementation("team.unnamed:mocha:${project.findProperty("mocha_version")}")
    include("team.unnamed:mocha:${project.findProperty("mocha_version")}")

    // Mocha库额外依赖
    include("org.javassist:javassist:${project.findProperty("javassist_version")}")

    val commonDep = project(mapOf("path" to ":common", "configuration" to "namedElements"))
    add("common", commonDep)
    configurations.named("common") {
        withDependencies {
            find { it == commonDep }?.let {
                (it as? ModuleDependency)?.isTransitive = false
            }
        }
    }

    add("shadowBundle", project(mapOf("path" to ":common", "configuration" to "transformProductionFabric")))
}

tasks.named<ProcessResources>("processResources") {
    val placeholders: Map<String, Any?> = mapOf(
        "mod_license" to project.findProperty("mod_license"),
        "mod_version" to project.version,
        "mod_id" to project.findProperty("mod_id"),
        "mod_name" to project.findProperty("mod_name"),
        "mod_homepage_url" to project.findProperty("mod_homepage_url"),
        "mod_description" to project.findProperty("mod_description"),
        "geckolib_version" to project.findProperty("geckolib_version"),
        "mod_mixin_config" to project.findProperty("mod_mixin_config"),
        "mod_issues_url" to project.findProperty("mod_issues_url"),
        "mod_fabric_sources_url" to project.findProperty("mod_fabric_sources_url"),
        "fabric_loader_version" to project.findProperty("fabric_loader_version"),
        "fabric_minecraft_version_range" to project.findProperty("fabric_minecraft_version_range"),
        "too_many_shortcuts_version" to project.findProperty("too_many_shortcuts_version"),
        "ayame_paperdoll_version" to project.findProperty("ayame_paperdoll_version")
    )


    inputs.properties(placeholders)

    filesMatching("fabric.mod.json") {
        expand(placeholders)
    }
}

tasks.named<Jar>("sourcesJar") {
    val commonSources = project(":common").tasks.named<Jar>("sourcesJar")
    dependsOn(commonSources)
    from(commonSources.map { zipTree(it.archiveFile) })
    archiveClassifier.set("sources")
}


tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    configurations = listOf(shadowBundle)
    archiveClassifier.set("dev-shadow")
}

tasks.named<net.fabricmc.loom.task.RemapJarTask>("remapJar") {
    dependsOn("shadowJar")
    val shadowJarTask = tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar")
    inputFile.set(shadowJarTask.get().archiveFile)
    injectAccessWidener.set(true)
}

