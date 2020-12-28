
object Dependencies {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Version.kotlin}"

    object Android {
        const val appCompat = "androidx.appcompat:appcompat:1.2.0"
        const val recyclerview = "androidx.recyclerview:recyclerview:1.1.0"
        const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime:${Version.lifecycleVersion}"
    }

    object Test {
        const val espresso = "androidx.test.espresso:espresso-core:3.3.0"
        const val junit = "junit:junit:4.12"

        const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${Version.kotlin}"

        const val googleTruth = "com.google.truth:truth:1.1"
    }
}