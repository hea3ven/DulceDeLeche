import com.github.breadmoirai.GithubReleaseExtension
import com.matthewprenger.cursegradle.CurseProject
import com.matthewprenger.cursegradle.CurseRelation
import com.matthewprenger.cursegradle.Options
import org.gradle.jvm.tasks.Jar
import org.apache.tools.ant.filters.ReplaceTokens
import org.ajoberstar.grgit.Grgit

plugins {
    kotlin("jvm") version "1.3.30"
    id("fabric-loom") version "0.2.3-SNAPSHOT"
    id("com.matthewprenger.cursegradle") version "1.1.2"
    id("com.github.breadmoirai.github-release") version "2.2.4"
    id("org.ajoberstar.grgit") version "3.0.0"
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
val version_h3nt_commonutils: String by project
val curse_api_key: String? by project
val project_curseforge_id: String by project
/* val changelog: String? by project */
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
    mavenLocal()
    maven(url="http://mribecky.com.ar/maven") {
        name = "Hea3veN"
    }
    maven(url = "https://kotlin.bintray.com/kotlinx") {
        name = "Kotlin X"
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

	modCompile("net.fabricmc.fabric-api:fabric-lib:0.1.0")
	modCompile("net.fabricmc.fabric-api:fabric-events-interaction:0.1.0")
	modCompile("net.fabricmc.fabric-api:fabric-resource-loader-v0:0.1.1+eff4f58d")

    modCompile("net.fabricmc:fabric-language-kotlin:$version_fabric_kotlin")
    compileOnly("net.fabricmc:fabric-language-kotlin:$version_fabric_kotlin")
    /* compileOnly("org.jetbrains.kotlin:kotlin-stdlib:$version_kotlin") */

    modCompile("com.hea3ven.tools.commonutils:h3nt-commonutils:$version_h3nt_commonutils")
    shadow("com.hea3ven.tools.commonutils:h3nt-commonutils:$version_h3nt_commonutils")

    modCompile("com.hea3ven.unstainer:unstainer:0.0.1-alpha-SNAPSHOT")

	modCompile("net.fabricmc.fabric-api:fabric-events-lifecycle:0.1.0")
    api("org.hamcrest:hamcrest:2.1")

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

val versionRegex = Regex("v[0-9].*")
val versionExcludeRegex = Regex("v1.(9(.4)?|10)-.*")
var repo = Grgit.open()
val lastVersionTag = repo.tag.list()
        .map { it.fullName.substring(10) }
        .filterNot(versionExcludeRegex::matches)
        .filter(versionRegex::matches)
        .sortedWith({a, b -> -(a.split('.').zip(b.split('.')).map {  it.first.compareTo(it.second) }.filter { it != 0 }.first() ?: 0) })
        .first()
val changes = repo.log {
    range(lastVersionTag, "HEAD")
}.filter {
    it.parentIds.size > 1
}.map {
    it.fullMessage.removeRange(0, it.fullMessage.indexOf('\n')).trim()
}.joinToString("\r\n")

curseforge {
    apiKey = curse_api_key ?: ""

    options(closureOf<Options> {
        forgeGradleIntegration = false
        debug = true
    })

    project(closureOf<CurseProject> {
        id = project_curseforge_id
        releaseType = "release"
        changelog = changes
        addGameVersion(version_mc)
		relations(closureOf<CurseRelation> {
			requiredDependency("fabric")
			requiredDependency("fabric-language-kotlin")
		})
    })
}
afterEvaluate {
    tasks.named("curseforge$project_curseforge_id") {
        dependsOn(tasks.getByName("remapJar"))
    }
}

configure<GithubReleaseExtension> {
    token(github_release_token ?: "")
    owner.set("Hea3veN")
    repo.set("DulceDeLeche")
    targetCommitish.set("master")
    body.set(changes)
    draft.set(false)
    prerelease.set(false)
    releaseAssets.setFrom(tasks.jar.get().outputs.files)
}

tasks.named("githubRelease") {
    dependsOn(tasks.getByName("remapJar"))
}


