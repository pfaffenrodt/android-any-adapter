
extra["minSdkVersion"] = 14
extra["targetSdkVersion"] = 31
extra["compileSdkVersion"] = 31


buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
        classpath("com.github.dcendents:android-maven-gradle-plugin:2.1")
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:1.6.21")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
