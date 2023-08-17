import com.github.gradle.node.npm.task.NpmTask
import com.github.gradle.node.pnpm.task.PnpmTask


plugins {
    java
    id("com.github.node-gradle.node") version "5.0.0"
}

node {
    download.set(true)
    version.set("18.16.0")
}

val lintTask = tasks.register<PnpmTask>("lintWebapp") {
    args.set(listOf("run", "lint"))
    dependsOn(tasks.pnpmInstall)
    inputs.dir("src")
    outputs.upToDateWhen { true }
}

val formatTask = tasks.register<PnpmTask>("formatWebapp") {
    args.set(listOf("run", "format"))
    dependsOn(tasks.pnpmInstall)
    inputs.dir("src")
    outputs.upToDateWhen { true }
}

val buildTask = tasks.register<PnpmTask>("buildWebapp") {
    args.set(listOf("run", "build"))
    dependsOn(tasks.pnpmInstall)
    inputs.dir(project.fileTree("src").exclude("**/*.spec.ts"))
    outputs.dir("${project(":backend").buildDir}/webapp")
}

val testTask = tasks.register<PnpmTask>("testFrontend") {
    args.set(listOf("run", "test"))
    dependsOn(tasks.pnpmInstall, lintTask)
    inputs.dir("src")
    outputs.upToDateWhen { true }
}

sourceSets {
    java {
        main {
            resources {
                // This makes the processResources task automatically depend on the buildWebapp one
                srcDir(buildTask)
            }
        }
    }
}

tasks.test {
    dependsOn(lintTask, testTask)
}