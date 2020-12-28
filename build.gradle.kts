// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        //noinspection AndroidGradlePluginVersion
        classpath("com.android.tools.build:gradle:4.2.0-beta02")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlin}")
        classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.7.3")
        classpath("com.github.dcendents:android-maven-gradle-plugin:1.4.1")
        classpath("org.jetbrains.dokka:dokka-android-gradle-plugin:0.9.17")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}
