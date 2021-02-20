package com.gymondo.app

object Modules {
    val app = ":app"
    val data = ":data"
    val domain = ":Domain"
    val remote = ":remote"
    val presentation = ":presentation"
}

public object Configration {

    object Android {
        const val compileSdk = 30
        const val mindSdk = 21
        const val targetSdk = 30
        const val buildTools = "28.0.3"
        const val versionCode = 1
        const val versionName = "1.0"
        const val kotlinVersion = "1.4.10"
        const val applicationId = "com.gymondo.app"
    }

    public object ClassPath {
        private const val gradleVersion = "4.1.2"
        const val gradle = "com.android.tools.build:gradle:$gradleVersion"
        const val kotlinGradle =
            "org.jetbrains.kotlin:kotlin-gradle-plugin:${Android.kotlinVersion}"
    }

}

public object Libraries {
    object Network {
        private const val retrofitVersion = "2.9.0"
        private const val okhttpVersion = "3.12.0"

        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
        const val okhttpLoggingInterceptor =
            "com.squareup.okhttp3:logging-interceptor:$okhttpVersion"
        const val okhttpMockServer = "com.squareup.okhttp3:mockwebserver:$okhttpVersion"
    }

    object AndroidXSupport {
        private const val appCompact = "1.2.0"
        private const val materialVersion = "1.3.0"
        private const val constraintLayoutVersion = "2.0.4"
        private const val ktxVersion = "1.3.2"
        private const val lifecycleVersion = "2.0.0"


        const val coreKTX = "androidx.core:core-ktx:$ktxVersion"
        const val appcompat = "androidx.appcompat:appcompat:$appCompact"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion"

        const val material = "com.google.android.material:material:$materialVersion"

    }


    object Kotlin {
        const val kotlinStdlib =
            "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Configration.Android.kotlinVersion}"
        const val kotlinReflect =
            "org.jetbrains.kotlin:kotlin-reflect:${Configration.Android.kotlinVersion}"
    }

    object Coroutines {
        private const val coroutinesVersion = "1.3.5"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
    }

    object Koin {
        private const val koinVersion = "2.2.2"
        const val koin = "org.koin:koin-android:$koinVersion"
        const val koinTest = "org.koin:koin-test:$koinVersion"
    }

    object Testing {

        private const val junitVersion = "4.12"
        private const val junitExtVersion = "1.1.2"
        private const val kotlinMockito = "2.2.0"
        private const val espressoVersion = "3.1.0"

        const val junit = "junit:junit:$junitVersion"
        const val junitExt = "androidx.test.ext:junit:$junitExtVersion"
        const val mockito = "com.nhaarman.mockitokotlin2:mockito-kotlin:$kotlinMockito"
        const val espresso = "androidx.test.espresso:espresso-core:$espressoVersion"
    }
}