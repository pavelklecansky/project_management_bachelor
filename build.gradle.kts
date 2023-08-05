plugins {
    java
    id("com.diffplug.spotless") version "6.18.0"
}

repositories {
    mavenCentral()
}

allprojects {
    group = "cz.klecansky"
    version = "0.0.1"

    apply(plugin = "idea")
}

spotless {
    java {
        target("*/src/*/java/**/*.java")
        toggleOffOn()
        palantirJavaFormat()
        removeUnusedImports()
        trimTrailingWhitespace()
        endWithNewline()
    }

}

subprojects {
    apply(plugin = "java")

    tasks.withType<JavaCompile> {
        sourceCompatibility = JavaVersion.VERSION_17.toString()
        targetCompatibility = JavaVersion.VERSION_17.toString()
    }

    tasks.test {
        useJUnitPlatform()
    }

}


