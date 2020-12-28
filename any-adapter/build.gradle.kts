plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(Version.compileSdkVersion)
    defaultConfig {
        minSdkVersion(Version.minSdkVersion)
        targetSdkVersion(Version.targetSdkVersion)
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
    implementation(Dependencies.Android.recyclerview)
    testImplementation(Dependencies.Test.junit)
    compileOnly(Dependencies.kotlin)

    testImplementation(Dependencies.Test.kotlinReflect)

    androidTestImplementation("androidx.test:runner:1.3.0")
    androidTestImplementation(Dependencies.Test.espresso)
    testImplementation("org.mockito:mockito-core:2.22.0")
    testImplementation("com.nhaarman:mockito-kotlin:1.5.0")
    testImplementation(Dependencies.Test.googleTruth)
}
apply {
    from("publish.gradle")
}