plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(Version.compileSdkVersion)
    defaultConfig {
        applicationId("de.pfaffenrodt.adapter.sample")
        minSdkVersion(Version.minSdkVersion)
        targetSdkVersion(Version.targetSdkVersion)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    androidTestImplementation(Dependencies.Test.espresso)
    implementation(Dependencies.Android.recyclerview)
    implementation(Dependencies.Android.appCompat)
    implementation(Dependencies.Android.lifecycleRuntime)
    implementation(Dependencies.Android.Paging.runtime)
    implementation(Dependencies.Android.Paging.rxjava3)
    implementation(Dependencies.rxjava3)
    testImplementation(Dependencies.Test.junit)

    implementation(Dependencies.kotlin)
    implementation(project(":any-adapter"))
}
