import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") // version from main build.gradle.kts
    `java-gradle-plugin`
    alias(libs.plugins.gradlePluginPublish)
}


repositories {
    mavenCentral()
    google()
    maven { setUrl("https://jitpack.io") }
}


dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation(project(":i18n4k-core"))
    implementation(project(":i18n4k-generator"))

    compileOnly(gradleApi())
    compileOnly(libs.kotlinGradlePlugin)
    compileOnly(libs.androidGradlePlugin)

    testImplementation(libs.junit)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_11
    }
}

gradlePlugin {
    website = "https://comahe-de.github.io/i18n4k"
    vcsUrl = "https://github.com/comahe-de/i18n4k"

    plugins {
        create("i18n4k") {
            id = "de.comahe.i18n4k"
            implementationClass = "de.comahe.i18n4k.gradle.plugin.I18n4kPlugin"
            displayName = "i18n4k"
            description = "i18n4k - Internationalization for Kotlin - Code Generator"
            tags = listOf(
                "kotlin", "kotlin/multiplatform", "kotlin/js", "kotlin/jvm", "kotlin/native",
                "i18n", "internationalization", "code generator"
            )
        }
    }
}



java {
    withSourcesJar()
    withJavadocJar()
}


// Maven publications are added automatically during evaluation (pluginBundle)
// So call avoidDuplicatePublications() after evaluation
afterEvaluate {
    publishing {
        publications {
            // avoid duplicate publication for multi platform builds
            BuildTools.avoidDuplicatePublications(project, this)
        }
    }
}
