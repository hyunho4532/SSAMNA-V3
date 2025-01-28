import java.util.Properties

plugins {
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    kotlin("plugin.serialization")
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.asetec.presentation"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.graphics:graphics-shapes:1.0.1")
    implementation("com.exyte:animated-navigation-bar:1.0.0")
    implementation("androidx.work:work-runtime-ktx:2.7.0")
    implementation ("com.github.PratikFagadiya:AnimatedSmoothBottomNavigation-JetpackCompose:1.1.2")
    implementation("com.google.maps.android:maps-compose:2.11.1")
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("com.google.accompanist:accompanist-permissions:0.37.0")

    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation("io.github.jan-tennert.supabase:compose-auth:2.4.2")
    implementation("io.github.jan-tennert.supabase:gotrue-kt:2.4.2")
    implementation("androidx.credentials:credentials:1.2.2")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("io.ktor:ktor-client-android:2.3.1")
    implementation("io.ktor:ktor-client-core:2.3.1")
    implementation("io.ktor:ktor-utils:2.3.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")
    implementation(platform("io.github.jan-tennert.supabase:bom:2.4.2"))
    implementation("io.github.jan-tennert.supabase:postgrest-kt")
    implementation("androidx.browser:browser:1.5.0")
    implementation("com.airbnb.android:lottie-compose:6.6.2")
    implementation("androidx.navigation:navigation-compose:2.8.5")
    implementation("androidx.navigation:navigation-ui:2.8.5")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(project(":data"))
    implementation(project(":domain"))
}