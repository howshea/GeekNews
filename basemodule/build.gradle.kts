plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(Build.targetSdk)
    defaultConfig {
        minSdkVersion(Build.minSdk)
        targetSdkVersion(Build.targetSdk)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildTypes {
        getByName("release") {
            consumerProguardFiles("proguard-rules.pro")
        }
    }
    dataBinding.isEnabled = true
    kapt {
        arguments {
            arg("AROUTER_MODULE_NAME", project.name)
        }
    }
    lintOptions {
        isAbortOnError = false
    }
}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    api(Deps.Support.appCompat)
    api(Deps.Support.design)
    api(Deps.Support.constraint)
    api(Deps.Kotlin.stdlib)
    api(Deps.Retrofit.runtime)
    api(Deps.Retrofit.gson)
    api(Deps.Retrofit.rxjava2)
    api(Deps.okHttpLoggingInterceptor)
    api(Deps.rxKotlin2)
    api(Deps.rxAndroid)
    api(Deps.Lifecycle.extensions)
    api(Deps.Glide.glide)
    api(Deps.Arouter.api)
    kapt(Deps.Arouter.compiler)
    api(Deps.Room.runtime)
    api(Deps.Room.rxjava2)
    kapt(Deps.Room.compiler)
    testImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.Test.runner)
    androidTestImplementation(Deps.Test.ext_unit)
    androidTestImplementation(Deps.Test.espresso)
}