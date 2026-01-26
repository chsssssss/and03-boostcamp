import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt.root)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.parcelize)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.boostcamp.and03"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.boostcamp.and03"
        minSdk = 24
        targetSdk = 36
        versionCode = 6
        versionName = "1.0.5"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        var properties = Properties()
        properties.load(FileInputStream("local.properties"))
        val naverClientId = properties.getProperty("NAVER_CLIENT_ID")
        val naverClientSecret = properties.getProperty("NAVER_CLIENT_SECRET")
        val aladinTTBKey = properties.getProperty("ALADIN_TTB_KEY")

        buildConfigField (
            "String",
            "NAVER_CLIENT_ID",
            "\"$naverClientId\""
        )

        buildConfigField (
            "String",
            "NAVER_CLIENT_SECRET",
            "\"$naverClientSecret\""
        )

        buildConfigField (
            "String",
            "ALADIN_TTB_KEY",
            "\"$aladinTTBKey\""
        )
    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isDebuggable = true
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        compilerOptions{
            jvmTarget = JvmTarget.JVM_11
        }
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    // hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.foundation.layout)
    ksp(libs.hilt.compiler)

    // serialization
    implementation(libs.kotlinx.serialization.json)

    // viewModel
    implementation(libs.androidx.viewmodel)

    // navigation
    implementation(libs.androidx.navigation)

    // coil
    implementation(libs.coil)

    // immutable
    implementation(libs.kotlinx.immutable)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx)
    implementation(libs.converter.scalars)

    // Interceptor
    implementation(libs.logging.interceptor)

    // Paging
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    // firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.firestore)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}