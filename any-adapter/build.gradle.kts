plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.dokka)
    id("maven-publish")
    id("signing")
}

group = "de.pfaffenrodt"
version  = "1.6.1"
val siteUrl = "https://github.com/pfaffenrodt/android-any-adapter"
val gitUrl = "https://github.com/pfaffenrodt/android-any-adapter.git"

android {
    val compileSdkVersion: Int by rootProject.extra
    val minSdkVersion: Int by rootProject.extra
    compileSdk = compileSdkVersion
    defaultConfig {
        minSdk = minSdkVersion
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    dataBinding {
        enable = true
    }
    viewBinding {
        enable = true
    }
    namespace = "de.pfaffenrodt.adapter"
}

dependencies {
    implementation(libs.androidx.recyclerview)
    testImplementation(libs.junit)
    compileOnly(libs.androidx.paging.runtime)
    compileOnly(libs.androidx.paging.common)

    testImplementation(libs.kotlin.reflect)

    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.espresso.core)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.google.truth)
}
tasks.dokkaHtml.configure {
    dokkaSourceSets {
        named("main") {
            noAndroidSdkLink.set(false)
        }
    }
}
val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets["main"].java.srcDirs)
}
val javadocJar by tasks.registering(Jar::class) {
    dependsOn("dokkaJavadoc")
    archiveClassifier.set("javadoc")
    from("$buildDir/dokka/javadoc")
}

afterEvaluate {
    publishing {
        repositories {
            maven {
                name = "GitHub Packages"
                url = uri("https://maven.pkg.github.com/pfaffenrodt/android-any-adapter")
                credentials {
                    username = System.getenv("GITHUB_ACTOR")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
        }
        publications {
            create<MavenPublication>("release") {
                groupId = project.group as String
                artifactId = "any-adapter"
                version = project.version as String
                from(components["release"])
            }
            withType<MavenPublication> {
                artifact(sourcesJar)
                artifact(javadocJar)
                pom {
                    name.set("AnyAdapter")
                    description.set("android recyclerview adapter to support to pass any objects")
                    url.set(siteUrl)
                    licenses {
                        license {
                            name.set("The Apache Software License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                    developers {
                        developer {
                            id.set("pfaffenrodt")
                            name.set("Dimitri Pfaffenrodt")
                            email.set("dimitri@pfaffenrodt.de")
                        }
                    }
                    organization {
                        name.set("pfaffenrodt")
                        url.set("https://www.pfaffenrodt.de/")
                    }
                    scm {
                        connection.set(gitUrl)
                        developerConnection.set(gitUrl)
                        url.set(siteUrl)
                    }
                }
            }
        }
    }
}
signing {
    sign(publishing.publications)
}