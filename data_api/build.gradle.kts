import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

fun loadLocalProperties(): Properties{
    val localPropertiesFile = File(rootProject.rootDir,"local.properties")

    if (!localPropertiesFile.exists()) {
        throw GradleException("Error: file local.properties doesn't exist, please create file and add api key")
    }

    val properties = Properties().apply {
        localPropertiesFile.inputStream().use { load(it) }
    }

    if (properties.getProperty("PEXELS_API_KEY").isNullOrBlank()) {
        throw GradleException("Error: can't find PEXELS_API_KEY in local.properties")
    }

    return properties
}


val properties = loadLocalProperties()

android {
    namespace = "com.example.data_api"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField ("String", "PEXELS_API_KEY",
            "\"${properties.getProperty("PEXELS_API_KEY")}\"")
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
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.espresso.core)
}