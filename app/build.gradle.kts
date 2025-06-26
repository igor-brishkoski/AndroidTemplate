import com.android.build.api.dsl.ApkSigningConfig
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.android.build.gradle.internal.dsl.SigningConfig
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.baseapp"
    compileSdk = libs.versions.compileSdk.get().toInt()
    buildFeatures.buildConfig = true

    defaultConfig {
        applicationId = "com.example.baseapp"
        minSdk = libs.versions.minimumSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        createSigningConfig("staging")
        createSigningConfig("production")
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
        createFlavor("devel", signingConfigs)
        createFlavor("staging", signingConfigs)
        createFlavor("production", signingConfigs)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.fromTarget("17")
        }
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

fun NamedDomainObjectContainer<ApplicationProductFlavor>.createFlavor(
    name: String,
    signingConfigs:
    NamedDomainObjectContainer<SigningConfig>,
) {
    val propsList = listOf(
        "BUILD_CONFIG_BASE_URL",
    )

    val props = gradleLocalProperties(rootProject.file("properties/$name"), project.providers)

    create(name) {
        dimension = "environment"
        applicationIdSuffix = props.getProperty("APPLICATION_ID_SUFFIX")
        versionNameSuffix = props.getProperty("APPLICATION_VERSION_SUFFIX")

        for (propName in propsList) {
            buildConfigField(
                "String",
                propName,
                props.getProperty(propName),
            )
        }

        if (name != "devel") {
            signingConfig = signingConfigs[name]
        }
    }
}

fun NamedDomainObjectContainer<out ApkSigningConfig>.createSigningConfig(
    name: String,
) {
    val props = gradleLocalProperties(rootProject.file("properties/$name"), project.providers)

    create(name) {
        storeFile = file("${rootDir}/keys/${name}-keystore.jks")
        storePassword = props.getProperty("KEYSTORE_PASSWORD")
        keyAlias = props.getProperty("KEY_ALIAS")
        keyPassword = props.getProperty("KEY_PASSWORD")
    }
}

dependencies {
    implementation(project(":core:utils"))
    implementation(project(":core:analytics"))
    implementation(project(":core:network"))
    implementation(project(":core:ui"))
    implementation(project(":core:data"))

    implementation(project(":features:home"))
    implementation(project(":features:userdetails"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.androidx.navigation3.adaptive)
    implementation(libs.kotlinx.serialization.core)

    implementation(platform(libs.ktor.bom))
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.auth)

    // hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}