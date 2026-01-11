import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "ru.ikom.courses"

    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "ru.ikom.courses"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.retrofit)

    implementation(libs.cicerone)
    implementation(project(":core:navigation"))

    implementation(project(":core:common"))
    implementation(project(":core:network"))

    implementation(project(":core:courses-logic:courses-domain"))
    implementation(project(":core:courses-logic:courses-data"))

    implementation(project(":features:feature-root"))

    implementation(project(":features:auth:api"))
    implementation(project(":features:auth:impl"))

    implementation(project(":features:main-menu:api"))
    implementation(project(":features:main-menu:impl"))

    implementation(project(":features:courses:api"))
    implementation(project(":features:courses:impl"))
}