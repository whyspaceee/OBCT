plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.kapt)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.gms.google.services)
}

android {
    namespace = "com.obcteam.obct"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.obcteam.obct"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            buildConfigField(
                "String",
                "BASE_URL",
                "\"http://34.101.108.25/api/\""
            )
            buildConfigField(
                "String",
                "WEB_CLIENT_ID",
                "\"280138413711-754tb9aiavchqibpcij9s3vhe6dpdtrh.apps.googleusercontent.com\""
            )
        }
        release {
            buildConfigField(
                "String",
                "BASE_URL",
                "\"http://34.101.108.25/api/\""
            )
            buildConfigField(
                "String",
                "WEB_CLIENT_ID",
                "\"280138413711-754tb9aiavchqibpcij9s3vhe6dpdtrh.apps.googleusercontent.com\""
            )
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        buildConfig = true
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.retrofit2.retrofit)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.dagger.hilt)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.graphics.shapes.android)
    kapt(libs.dagger.hilt.android.compiler)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.gms.play.services.auth)

    implementation("androidx.credentials:credentials:1.2.2")
    implementation( "androidx.credentials:credentials-play-services-auth:1.2.2")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.0")
    implementation("io.coil-kt:coil-compose:2.6.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}