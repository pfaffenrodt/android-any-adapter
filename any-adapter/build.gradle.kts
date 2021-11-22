plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("org.jetbrains.dokka")
    id("maven-publish")
    id("signing")
}

group = "de.pfaffenrodt"
version  = "1.5.2"
val siteUrl = "https://github.com/pfaffenrodt/android-any-adapter"
val gitUrl = "https://github.com/pfaffenrodt/android-any-adapter.git"

android {
    val compileSdkVersion: Int by rootProject.extra
    val minSdkVersion: Int by rootProject.extra
    val targetSdkVersion: Int by rootProject.extra
    compileSdk = compileSdkVersion
    defaultConfig {
        minSdk = minSdkVersion
        targetSdk = targetSdkVersion
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
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    testImplementation("junit:junit:4.13.2")
    compileOnly("androidx.paging:paging-runtime:3.0.1")
    compileOnly("androidx.paging:paging-common:3.0.1")

    testImplementation("org.jetbrains.kotlin:kotlin-reflect:1.5.31")

    androidTestImplementation("androidx.test:runner:1.4.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    testImplementation("org.mockito:mockito-core:4.1.0")
    testImplementation("com.nhaarman:mockito-kotlin:1.6.0")
    testImplementation("com.google.truth:truth:1.1.3")
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
val sonaTypeUrl = (properties["sonatypeUrl"] as String?)!!

afterEvaluate {

    publishing {
        repositories {
            maven {
                name = "OSSRH"
                url = uri(sonaTypeUrl)
                credentials {
                    username = properties["sonatypeUsername"] as String?
                    password = properties["sonatypePassword"] as String?
                }
            }
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