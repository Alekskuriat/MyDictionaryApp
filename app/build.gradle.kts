
plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("parcelize")
    kotlin("android-extensions")
    kotlin("kapt") version "1.6.0"
}

android {

    compileSdk = 30
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.example.mydictionaryapp"
        minSdk = 24
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("Release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

}

dependencies {

    testImplementation(Dependencies.JUNIT_DEP)
    androidTestImplementation(Dependencies.JUNIT_TEST_DEP)
    androidTestImplementation(Dependencies.ESPRESSO_DEP)

    implementation(Dependencies.CORE_DEP)
    implementation(Dependencies.APP_COMPAT_DEP)
    implementation(Dependencies.MATERIAL_DEP)
    implementation(Dependencies.CONSTRAINT_LAYOUT_DEP)
    implementation(Dependencies.LEGACY_SUPPORT_DEP)
    implementation(Dependencies.RECYCLER_VIEW_DEP)

    //ViewBinding
    implementation(Dependencies.VIEW_BINDING_DELEGATE_DEP)
    implementation(Dependencies.VIEW_BINDING_NOREFLECTION_DEP)

    //Kotlin
    implementation(Dependencies.KOTLIN_STD_DEP)

    // Rx-Java
    implementation(Dependencies.RX_JAVA_ANDROID_DEP)
    implementation(Dependencies.RX_JAVA_JAVA_DEP)

    // Retrofit 2
    implementation(Dependencies.RETROFIT_DEP)
    implementation(Dependencies.RETROFIT_CONVERTER_JSON_DEP)
    implementation(Dependencies.RETROFIT_LOGGING_INTERCEPTOR_DEP)
    implementation(Dependencies.RETROFIT_ADAPTER_RX_JAVA_3_DEP)

    //Dagger 2
    implementation(Dependencies.DAGGER_DEP)
    implementation(Dependencies.DAGGER_ANDROID_DEP)
    implementation(Dependencies.DAGGER_ANDROID_SUPPORT_DEP)
    kapt(Dependencies.DAGGER_COMPILER_DEP)
    kapt(Dependencies.DAGGER_ANDROID_PROCESSOR)

    //Cicerone
    implementation(Dependencies.CICERONE_DEP)

    //Room
    implementation(Dependencies.ROOM_RUNTIME_DEP)
    implementation(Dependencies.ROOM_RX_JAVA_3_DEP)
    implementation(Dependencies.ROOM_KTX_DEP)
    kapt(Dependencies.ROOM_COMPILER_DEP)

    //Koin
    implementation(Dependencies.KOIN_CORE_DEP)
    implementation(Dependencies.KOIN_ANDROID_DEP)
    implementation(Dependencies.KOIN_COMPAT_DEP)

    //Coroutines
    implementation(Dependencies.COROUTINES_CORE_DEP)
    implementation(Dependencies.COROUTINES_ANDROID_DEP)
    implementation(Dependencies.COROUTINES_RETROFIT_ADAPTER_DEP)

    //Glide
    implementation(Dependencies.GLIDE_DEP)
    kapt(Dependencies.GLIDE_KAPT_DEP)

    //Coil
    implementation(Dependencies.COIL_DEP)

}
