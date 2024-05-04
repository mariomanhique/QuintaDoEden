plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.gms.google-services")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

extra["versionMajor"] = 1
extra["versionMinor"] = 0
extra["versionPatch"] = 0
extra["versionClassifier"] = null
extra["isSnapshot"] = false
extra["minSdkVersion"] = 29

val versionMajor = extra["versionMajor"] as Int
val versionMinor = extra["versionMinor"] as Int
val versionPatch = extra["versionPatch"] as Int
var versionClassifier = extra["versionClassifier"] as String?
val isSnapshot = extra["isSnapshot"] as Boolean
val minSdkVersion = extra["minSdkVersion"] as Int

fun generateVersionCode(): Int {
    return minSdkVersion * 10000000 + versionMajor * 10000 +
            versionMinor * 100 + versionPatch
}

fun generateVersionName(): String {
    var versionName = "$versionMajor.$versionMinor.$versionPatch"

    if (versionClassifier == null) {
        if (isSnapshot) {
            versionClassifier = "SNAPSHOT"
        }
    }

    versionClassifier?.let {
        versionName += "-$versionClassifier"
    }

    return versionName
}


android {
    namespace = "com.mariomanhique.quintadoeden"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mariomanhique.quintadoeden"
        minSdk = 29
        targetSdk = 34
        versionCode = generateVersionCode()
        versionName = generateVersionName()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {

        create("release") {
            storeFile = file("../fastlane/keystore1.jks")
            storePassword = "mmanhique"
            keyPassword = "mmanhique"
            keyAlias = "upload"
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
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

    implementation(libs.androidx.core.ktx)
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

    implementation("androidx.compose.material:material-icons-extended")

    implementation("com.google.dagger:hilt-android:2.48.1")
    ksp("com.google.dagger:hilt-compiler:2.48.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    implementation(platform("com.google.firebase:firebase-bom:32.8.1"))

    implementation("com.google.firebase:firebase-analytics")

    implementation("com.google.firebase:firebase-storage")
    implementation ("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")

    //Coil
    //implementation("io.coil-kt:coil-compose:2.4.0")

    //Google Auth
    //implementation("com.google.android.gms:play-services-auth")

    //Splash API
    implementation("androidx.core:core-splashscreen:1.0.1")

    //Compose Navigation
    implementation("androidx.navigation:navigation-compose:2.7.6")


    implementation("androidx.compose.material3:material3-window-size-class")
    implementation("androidx.tracing:tracing-ktx:1.1.0")


    //Coroutine lifecycle scope
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

    // Date-Time Picker
    implementation ("com.maxkeppeler.sheets-compose-dialogs:core:1.0.2")

    // CALENDAR
    implementation ("com.maxkeppeler.sheets-compose-dialogs:calendar:1.0.2")

    // CLOCK
    implementation ("com.maxkeppeler.sheets-compose-dialogs:clock:1.0.2")


    //Number Picker
    implementation("com.chargemap.compose:numberpicker:1.0.3")

    implementation("com.google.firebase:firebase-messaging")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    implementation("com.kizitonwose.calendar:compose:2.5.0")

}