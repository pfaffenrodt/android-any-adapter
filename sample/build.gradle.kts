plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    val compileSdkVersion: Int by rootProject.extra
    val minSdkVersion: Int by rootProject.extra
    val targetSdkVersion: Int by rootProject.extra
    compileSdk = compileSdkVersion
    defaultConfig {
        applicationId = "de.pfaffenrodt.adapter.sample"
        minSdk = minSdkVersion
        targetSdk = targetSdkVersion
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
    namespace = "de.pfaffenrodt.adapter.sample"
}

dependencies {
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime:2.6.1")
    implementation("androidx.paging:paging-runtime:3.1.1")
    implementation("androidx.paging:paging-rxjava3:3.1.1")
    implementation("io.reactivex.rxjava3:rxjava:3.1.6")
    testImplementation("junit:junit:4.13.2")

    implementation(project(":any-adapter"))
}
