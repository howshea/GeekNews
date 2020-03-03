plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("android.extensions")
}


android {
    compileSdkVersion(Build.targetSdk)
    defaultConfig {
        applicationId = "com.howshea.geeknews"
        minSdkVersion(Build.minSdk)
        targetSdkVersion(Build.targetSdk)
        versionCode = Build.versionCode
        versionName = Build.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isZipAlignEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    dataBinding.isEnabled = true
    packagingOptions {
        exclude("META-INF/rxjava.properties")
    }
    lintOptions {
        isAbortOnError = false
    }
}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    //module
    implementation(project(":basemodule"))
    implementation(project(":home"))
    implementation(project(":data"))
    implementation(project(":read"))
    implementation(project(":personalcenter"))
    //test
    testImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.Test.runner)
    androidTestImplementation(Deps.Test.ext_unit)
    androidTestImplementation(Deps.Test.espresso)
}
