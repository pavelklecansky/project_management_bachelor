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
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}


