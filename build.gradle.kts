import com.github.breadmoirai.GithubReleaseExtension
import com.matthewprenger.cursegradle.CurseProject
import com.matthewprenger.cursegradle.CurseRelation
import com.matthewprenger.cursegradle.Options
import org.gradle.jvm.tasks.Jar
import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    kotlin("jvm") version "1.3.11"
    id("fabric-loom") version "0.2.0-SNAPSHOT"
    id("com.matthewprenger.cursegradle") version "1.1.2"
    id("com.github.breadmoirai.github-release") version "2.2.4"
}

val BUILD_NO: String? by project
val buildNo = BUILD_NO ?: "SNAPSHOT"

val project_version: String by project
val version_mc: String by project
val version_mc_jar: String by project
val version_mc_mappings: String by project
val version_fabric: String by project
val version_fabric_loader: String by project
val version_fabric_kotlin: String by project
val version_kotlin: String by project
val curse_api_key: String? by project
val project_curseforge_id: String by project
val changelog: String? by project
val github_release_token: String? by project

group = "com.hea3ven.dulcedeleche"
version = "$project_version-$buildNo"

base {
	archivesBaseName = "dulcedeleche"
}

tasks.compileJava   {
  sourceCompatibility = "1.8"
  targetCompatibility = "1.8"
}

repositories {
    maven(url="http://mribecky.com.ar/maven") {
        name = "Hea3veN"
    }
}

val shadow by configurations.creating {
	isTransitive = false
}

/* compileOnly.extendsFrom(configurations. */

dependencies {
	minecraft("com.mojang:minecraft:$version_mc_jar")
	mappings("net.fabricmc:yarn:$version_mc_mappings")
	modCompile("net.fabricmc:fabric-loader:$version_fabric_loader")

	modCompile("net.fabricmc:fabric:$version_fabric")

    modCompile("net.fabricmc:fabric-language-kotlin:$version_fabric_kotlin")
    compileOnly("net.fabricmc:fabric-language-kotlin:$version_fabric_kotlin")

    modCompile("com.hea3ven.tools.commonutils:h3nt-commonutils:3.0.0-fabric-1")
    shadow("com.hea3ven.tools.commonutils:h3nt-commonutils:3.0.0-fabric-1")

    compileOnly("com.google.code.findbugs:jsr305:3.0.2")
}

tasks.processResources {
    filesMatching("**/*.json") {
        filter<ReplaceTokens>("tokens" to mapOf(
                "version" to project.version.toString(),
                "version_fabric" to "$version_fabric".toString(),
                "version_kotlin" to "$version_kotlin".toString(),
                "version_fabric_kotlin" to "$version_fabric_kotlin".toString()
        ))
    }
}

tasks.withType<Jar> {
    from(configurations["shadow"].asFileTree.files.map { zipTree(it) })
}

curseforge {
    apiKey = curse_api_key ?: ""

    options(closureOf<Options> {
        forgeGradleIntegration = false
    })

    project(closureOf<CurseProject> {
        id = project_curseforge_id
        releaseType = "release"
        changelog = changelog ?: ""
        addGameVersion(version_mc)
		relations(closureOf<CurseRelation> {
			requiredDependency("fabric")
			requiredDependency("fabric-language-kotlin")
		})
    })
}

configure<GithubReleaseExtension> {
    token(github_release_token ?: "")
    owner.set("Hea3veN")
    repo.set("DulceDeLeche")
    targetCommitish.set("master")
    body.set(changelog ?: "")
    draft.set(false)
    prerelease.set(false)
    releaseAssets.setFrom(tasks.jar.get().outputs.files)
}
