plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.dokka")
}

android {
    namespace = "com.sergio.rodriguez.testanimation"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sergio.rodriguez.testanimation"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {

        getByName("release") {
            isMinifyEnabled = false
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"https://www.google.com/\"")
        }

        getByName("debug"){
            applicationIdSuffix = ".debug"
            isDebuggable = true
            buildConfigField("String", "BASE_URL", "\"https://www.google.com/pruebas\"")
        }

        create("alpha"){
            initWith(getByName("debug"))// Crear un nuevo buildType a partir del buildType debug
            applicationIdSuffix = ".qa"
            signingConfig = signingConfigs.getByName("debug")
            buildConfigField("String", "BASE_URL", "\"https://www.google.com/test\"")
        }

        flavorDimensions += "version"
        flavorDimensions += "roles"

        productFlavors {
            create("free"){
                dimension = "version"
                applicationIdSuffix = ".free"
                versionNameSuffix = "-free"
                buildConfigField("Boolean", "SHOW_POPUP", "true")
            }
            create("premium"){
                dimension = "version"
                applicationIdSuffix = ".premium"
                versionNameSuffix = "-premium"
            }

            create("admin"){
                dimension = "roles"
                applicationIdSuffix = ".admin"
                versionNameSuffix = "-admin"
            }
            create("user"){
                dimension = "roles"
                applicationIdSuffix = ".user"
                versionNameSuffix = "-user"
            }
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

//val freeAdminDebugImplementation: Configuration by configurations.creating { description = "freeAdminDebug" }
//val alphaImplementation: Configuration by configurations.creating { description = "alphaImplementation" }

dependencies {

    dokkaPlugin("org.jetbrains.dokka:android-documentation-plugin:1.9.0")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    implementation("androidx.activity:activity-compose:1.8.0")
    implementation( platform("androidx.compose:compose-bom:2023.03.00") )
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

    "freeImplementation"("androidx.compose.ui:ui-tooling")
    
    //freeAdminDebugImplementation("androidx.compose.ui:ui-tooling")

}