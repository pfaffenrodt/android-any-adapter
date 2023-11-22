
extra["minSdkVersion"] = 14
extra["targetSdkVersion"] = 33
extra["compileSdkVersion"] = 33


plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.dokka) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.gradle.nexus.publish) apply true
}

val sonaTypeUrl = (properties["sonatypeUrl"] as String?)!!
val sonatypeSnapshotUrl = (properties["sonatypeSnapshotUrl"] as String?)!!
nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri(sonaTypeUrl))
            snapshotRepositoryUrl.set(uri(sonatypeSnapshotUrl))
            username.set(properties["sonatypeUsername"] as String?)
            password.set(properties["sonatypePassword"] as String?)
            stagingProfileId.set(properties["sonatypeStagingProfileId"] as String?)
        }
    }
}