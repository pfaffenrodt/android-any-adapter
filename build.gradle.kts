
extra["minSdkVersion"] = 14
extra["targetSdkVersion"] = 33
extra["compileSdkVersion"] = 33


plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.dokka) apply false
}