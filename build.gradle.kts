// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        //noinspection AndroidGradlePluginVersion
        classpath("com.android.tools.build:gradle:4.2.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlin}")
        classpath("com.github.dcendents:android-maven-gradle-plugin:1.4.1")
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:${Version.dokka}")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
