plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    kotlin("android.extensions")
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
}

dependencies {
    addCommonDeps()
}
