import org.gradle.api.artifacts.dsl.RepositoryHandler

/**
 * Created by haipo
 * on 2019-09-05.
 */
object Deps {
    object Support {
        val appCompat = "com.android.support:appcompat-v7:${Versions.support}"
        val constraint = "com.android.support.constraint:constraint-layout:${Versions.constraint}"
        val design = "com.android.support:design:${Versions.support}"
    }

    object Lifecycle {
        val extensions = "android.arch.lifecycle:extensions:${Versions.lifecycle}"
    }

    object Retrofit {
        val runtime = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
        val rxjava2 = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    }

    val okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpLoggingInterceptor}"
    val rxKotlin2 = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlin2}"
    val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"

    object Test {
        val junit = "junit:junit:${Versions.junit}"
        val runner = "com.android.support.test:runner:${Versions.runner}"
        val espresso = "com.android.support.test.espresso:espresso-core:${Versions.espresso}"
    }

    object Kotlin {
        val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
        val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    }

    object Glide {
        val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
        val compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    }

    object Arouter {
        val api = "com.alibaba:arouter-api:${Versions.arouterApi}"
        val compiler = "com.alibaba:arouter-compiler:${Versions.arouterCompiler}"
    }

    object Room {
        val runtime = "android.arch.persistence.room:runtime:${Versions.room}"
        val compiler = "android.arch.persistence.room:compiler:${Versions.room}"
        val rxjava2 = "android.arch.persistence.room:rxjava2:${Versions.room}"
    }

    val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"

    fun addRepos(handler: RepositoryHandler) {
        handler.google()
        handler.jcenter()
        handler.maven {
            setUrl("https://jitpack.io")
        }
        handler.mavenCentral()
    }
}