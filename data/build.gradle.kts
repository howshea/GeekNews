import Build.versionCode
import Build.versionName
import org.jetbrains.kotlin.kapt3.base.Kapt.kapt
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallArgument.DefaultArgument.arguments

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
        testInstrumentationRunner ="android.support.test.runner.AndroidJUnitRunner"
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
            arg("AROUTER_MODULE_NAME", project.getName())
        }
    }
}

dependencies {
    addCommonDeps()
}
