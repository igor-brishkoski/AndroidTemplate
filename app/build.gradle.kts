plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.baseapp"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.example.baseapp"
        minSdk = libs.versions.minimumSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("rolling") {
            storeFile = file("${rootDir}/keys/rolling-keystore.jks")
            storePassword = "rolling-keystore"
            keyAlias = "rolling-key"
            keyPassword = "rolling"
        }

        create("staging") {
            storeFile = file("${rootDir}/keys/staging-keystore.jks")
            storePassword = "staging-keystore"
            keyAlias = "staging-key"
            keyPassword = "staging"
        }

        create("production") {
            storeFile = file("${rootDir}/keys/production-keystore.jks")
            storePassword = "production-keystore"
            keyAlias = "production-key"
            keyPassword = "production"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs["debug"]
        }
    }

    flavorDimensions += "environment"
    productFlavors {
        create("devel") {
            dimension = "environment"
            applicationIdSuffix = ".devel"
            versionNameSuffix = " Devel"
        }
        create("rolling") {
            dimension = "environment"
            applicationIdSuffix = ".rolling"
            versionNameSuffix = " Rolling"
            signingConfig = signingConfigs["rolling"]
        }
        create("staging") {
            dimension = "environment"
            applicationIdSuffix = ".staging"
            versionNameSuffix = " Staging"
            signingConfig = signingConfigs["staging"]
        }
        create("production") {
            dimension = "environment"
            signingConfig = signingConfigs["production"]
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

androidComponents {
    beforeVariants { variantBuilder ->
        // To check for a certain build type, use variantBuilder.buildType == "<buildType>"
        if (variantBuilder.buildType == "release" && variantBuilder.flavorName == "devel") {
            // Gradle ignores any variants that satisfy the conditions above.
            variantBuilder.enable = false
        }
    }
}

dependencies {
    implementation(project(":core:utils"))
    implementation(project(":core:analytics"))
    implementation(project(":core:network"))
    implementation(project(":core:ui"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(platform(libs.ktor.bom))
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.auth)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}