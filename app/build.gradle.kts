plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.2")

    defaultConfig {
        applicationId = "com.ashkay.prody"
        minSdkVersion(22)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    /* ====================================================
    *                       Basic
    * ===================================================*/

    // Kotlin
    implementation(Dependencies.kotlin)

    // Android
    implementation(Android.appcompat)
    implementation(Android.activityKtx)
    implementation(Android.coreKtx)
    implementation(Android.constraintLayout)
    implementation(Android.swipeRefreshLayout)
    implementation(Android.fragmentKtx)

    /* ====================================================
    *                    Utils/Others
    * ===================================================*/

    // Hilt + Dagger
    implementation(Hilt.hiltAndroid)
    implementation(Hilt.hiltViewModel)
    kapt(Hilt.daggerCompiler)
    kapt(Hilt.hiltCompiler)

    //RxJava
    implementation(RxJava.rxAndroid)
    implementation(RxJava.rxjava2)

    // Timber
    implementation(Timber.timber)

    /* ====================================================
    *                       UI
    * ===================================================*/

    //NavigationComponent
    implementation(NavigationComponent.ui)
    implementation(NavigationComponent.fragment)

    // Material Design
    implementation(Dependencies.materialDesign)

    //ViewPager2
    implementation(ViewPager.viewPager2)

    //Test
    testImplementation(Test.junit)
    androidTestImplementation(Test.junitImpl)
    androidTestImplementation(Test.espresso)
}