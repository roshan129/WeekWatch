import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("de.mannodermaus.android-junit5") version "1.9.3.0"
}

android {
    namespace = "com.roshanadke.weekwatch"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.roshanadke.weekwatch"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        /*val key = gradleLocalProperties(rootDir).getProperty("TMDB_API_KEY")
        val token = gradleLocalProperties(rootDir).getProperty("AUTH_TOKEN")
        buildConfigField("String", "API_KEY", key)
        buildConfigField("String", "AUTH_TOKEN", token)*/

        val authToken = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2NTM1YTE0MWRiNzhjYTAwYjc2NDcyMzQ4NTBhYTNhOCIsInN1YiI6IjY1ODZjYjVmMDcyMTY2Njg1YWE1M2MzNyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Klac7M6KH2XrBV4ntooL9sxgpQXSQkM95klwmrlFKQs"
        buildConfigField("String", "AUTH_TOKEN", "\"$authToken\"")
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
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))

    //hilt
    implementation("com.google.dagger:hilt-android:2.48.1")
    kapt("com.google.dagger:hilt-android-compiler:2.48.1")

    //hilt navigation compose
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0-alpha01")

    //room db
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    // Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.6.1")

    //compose navigation
    implementation("androidx.navigation:navigation-compose:2.7.6")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    //coil
    implementation("io.coil-kt:coil-compose:2.2.2")

    //icons
    implementation("androidx.compose.material:material-icons-extended:1.5.4")

    //material compose
    implementation("androidx.compose.material:material:1.5.4")

    //logging
    implementation("com.jakewharton.timber:timber:5.0.1")

    //testing
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.3")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.3")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    //turbine- flow testing
    testImplementation("app.cash.turbine:turbine:0.7.0")

    //assertion
    testImplementation("com.willowtreeapps.assertk:assertk:0.26.1")

    androidTestImplementation("androidx.navigation:navigation-testing:2.7.6")

    //mockito
    testImplementation("org.mockito:mockito-core:5.9.0")
    //testImplementation("org.mockito.kotlin:mockito-kotlin:5.3.0")

    //mockk
    testImplementation("io.mockk:mockk:1.13.9")
    androidTestImplementation("io.mockk:mockk-android:1.13.9")


}