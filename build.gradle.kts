plugins {
    java
}

repositories {
    mavenCentral()
}

allprojects {
    group = "cz.klecansky"
    version = "0.0.1"

    apply(plugin = "idea")
}

subprojects {
    apply(plugin = "java")

    tasks.withType<JavaCompile> {
        sourceCompatibility = JavaVersion.VERSION_16.toString()
        targetCompatibility = JavaVersion.VERSION_16.toString()
    }

    tasks.test {
        useJUnitPlatform()
    }

}


