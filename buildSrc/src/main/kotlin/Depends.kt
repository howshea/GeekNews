import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.project

/**
 * Created by haipo
 * on 2019-09-05.
 */
object Deps {
    object Support {
        const val appCompat = "androidx.appcompat:appcompat:${Versions.support}"
        const val constraint = "androidx.constraintlayout:constraintlayout:${Versions.constraint}"
        const val design = "com.google.android.material:material:${Versions.support}"
    }

    object Lifecycle {
        const val extensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    }

    object Retrofit {
        const val runtime = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
        const val rxjava2 = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    }

    const val okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpLoggingInterceptor}"
    const val rxKotlin2 = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlin2}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"

    object Test {
        const val junit = "junit:junit:${Versions.junit}"
        const val runner = "androidx.test:runner:${Versions.runner}"
        const val ext_unit = "androidx.test.ext:junit:${Versions.runner}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    }

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
        const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    }

    object Glide {
        const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
        const val compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    }

    object Arouter {
        const val api = "com.alibaba:arouter-api:${Versions.arouterApi}"
        const val compiler = "com.alibaba:arouter-compiler:${Versions.arouterCompiler}"
    }

    object Room {
        const val runtime = "androidx.room:room-runtime:${Versions.room}"
        const val compiler = "androidx.room:room-compiler:${Versions.room}"
        const val rxjava2 = "androidx.room:room-rxjava2:${Versions.room}"
    }

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"

    fun addRepos(handler: RepositoryHandler) {
        handler.apply {
            google()
            jcenter()
            maven {
                setUrl("https://jitpack.io")
            }
            mavenCentral()
        }
    }
}

fun DependencyHandlerScope.addCommonDeps(){
    //test
    "testImplementation"(Deps.Test.junit)
    "androidTestImplementation"(Deps.Test.runner)
    "androidTestImplementation"(Deps.Test.ext_unit)
    "androidTestImplementation"(Deps.Test.espresso)
    //module
    "implementation"(project(":basemodule"))
    "kapt"(Deps.Arouter.compiler)
    "kapt"(Deps.Room.compiler)
}