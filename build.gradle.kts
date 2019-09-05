// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    apply(from = "versions.gradle")
    Deps.addRepos(repositories)
    dependencies {
        classpath(Deps.androidGradlePlugin)
        classpath(Deps.Kotlin.plugin)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
    repositories {
        google()
    }
}

allprojects {
    Deps.addRepos(repositories)
}

task("clean", Delete::class) {
    delete(rootProject.buildDir)
}