/*
    neoForge("net.neoforged:neoforge:${project.findProperty("neoforge_version")}")

    common(project(path: ':common', configuration: 'namedElements')) { transitive false }
    shadowBundle project(path: ':common', configuration: 'transformProductionNeoForge')

    // 不要使用modImplementation，否则IO会爆炸
    // GeckoLib
    implementation("software.bernie.geckolib:geckolib-neoforge-${project.findProperty("minecraft_version")}:${project.findProperty("geckolib_version")}")
    // Ayame PaperDoll
    implementation("maven.modrinth:ayame-paperdoll:${project.findProperty("ayame_paperdoll_version")}-neoforge")

    // Rhino库
    implementation("org.mozilla:rhino:${project.findProperty("rhino_version")}")
    forgeRuntimeLibrary("org.mozilla:rhino:${project.findProperty("rhino_version")}")
    include("org.mozilla:rhino:${project.findProperty("rhino_version")}")

    // Mocha 库
    implementation("team.unnamed:mocha:${project.findProperty("mocha_version")}")
    forgeRuntimeLibrary("team.unnamed:mocha:${project.findProperty("mocha_version")}")
    include("team.unnamed:mocha:${project.findProperty("mocha_version")}")

    // Mocha库额外依赖
    include("org.javassist:javassist:${project.findProperty("javassist_version")}")
 */

plugins {
    id("com.github.johnrengelman.shadow")
}

architectury {
    platformSetupLoomIde()
    neoForge()
}

loom {
    accessWidenerPath.set(project(":common").loom.accessWidenerPath)
}

configurations {
    create("common") {
        isCanBeResolved = true
        isCanBeConsumed = false
    }
    named("compileClasspath") {
        extendsFrom(configurations["common"])
    }
    named("runtimeClasspath") {
        extendsFrom(configurations["common"])
    }
    named("developmentNeoForge") {
        extendsFrom(configurations["common"])
    }
    create("shadowBundle") {
        isCanBeResolved = true
        isCanBeConsumed = false
    }
}

repositories {
    maven {
        name = "NeoForged"
        url = uri("https://maven.neoforged.net/releases")
    }
}

dependencies {
    add("neoForge", "net.neoforged:neoforge:${rootProject.extra["neoforge_version"]}")

    val commonDep = project(path = ":common", configuration = "namedElements")
    add("common", commonDep)
    configurations.named("common") {
        withDependencies {
            find { it == commonDep }?.let {
                (it as? ModuleDependency)?.isTransitive = false
            }
        }
    }

    add("shadowBundle", project(path = ":common", configuration = "transformProductionNeoForge"))

    // 不要使用modImplementation，否则IO会爆炸
    // GeckoLib
    implementation("software.bernie.geckolib:geckolib-neoforge-${project.findProperty("minecraft_version")}:${project.findProperty("geckolib_version")}")
    // Ayame PaperDoll
    implementation("maven.modrinth:ayame-paperdoll:${project.findProperty("ayame_paperdoll_version")}-neoforge")

    // Rhino库
    implementation("org.mozilla:rhino:${project.findProperty("rhino_version")}")
    forgeRuntimeLibrary("org.mozilla:rhino:${project.findProperty("rhino_version")}")
    include("org.mozilla:rhino:${project.findProperty("rhino_version")}")

    // Mocha 库
    implementation("team.unnamed:mocha:${project.findProperty("mocha_version")}")
    forgeRuntimeLibrary("team.unnamed:mocha:${project.findProperty("mocha_version")}")
    include("team.unnamed:mocha:${project.findProperty("mocha_version")}")

    // Mocha库额外依赖
    include("org.javassist:javassist:${project.findProperty("javassist_version")}")
}

tasks.named<ProcessResources>("processResources") {
    // 集中管理所有需要替换的占位符
    val placeholders: Map<String, Any?> = mapOf(
        "mod_license"                      to project.findProperty("mod_license"),
        "mod_version"                      to project.version,
        "mod_id"                           to project.findProperty("mod_id"),
        "mod_name"                         to project.findProperty("mod_name"),
        "mod_homepage_url"                 to project.findProperty("mod_homepage_url"),
        "mod_description"                  to project.findProperty("mod_description"),
        "geckolib_version"                 to project.findProperty("geckolib_version"),
        "ayame_paperdoll_version"          to project.findProperty("ayame_paperdoll_version"),
        "mod_mixin_config"                to project.findProperty("mod_mixin_config"),
        "mod_issues_url"                   to project.findProperty("mod_issues_url"),
        "neoforge_version"                 to project.findProperty("neoforge_version"),
        "neoforge_minecraft_version_range" to project.findProperty("neoforge_minecraft_version_range"),
        "version"                          to project.version // 添加 version 占位符
    )


    inputs.properties(placeholders)

    filesMatching("META-INF/neoforge.mods.toml") {
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
    configurations = listOf(project.configurations["shadowBundle"])
    archiveClassifier.set("dev-shadow")
}

tasks.named<net.fabricmc.loom.task.RemapJarTask>("remapJar") {
    dependsOn("shadowJar")
    val shadowJarTask = tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar")
    input.set(shadowJarTask.flatMap { it.archiveFile })
    injectAccessWidener.set(true)
    atAccessWideners.add(loom.accessWidenerPath.get().asFile.name)
}
