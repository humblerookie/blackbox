object Versions {


    //app
    const val compileSdk =  33
    const val minSdk = 23
    const val targetSdk =  33
    const val appId = "dev.anvith.blackbox"

    const val GRADLE = "7.2.0"

    // Android libraries
    const val APP_COMPAT = "1.2.0"
    const val MATERIAL = "1.1.0"
    const val ANDROID_KTX = "1.1.0"
    const val CONSTRAINT_LAYOUT = "2.0.1"

    // Kotlin
    const val KOTLIN = "1.4.0"
    const val JUNIT = "4.12"

    const val LOCAL_BROADCAST = "1.0.0"
    const val PUBLISH = "0.14.2"
    const val KT_LINT="0.38.1"
    const val DOKKA="1.4.0"
}

object Deps {
    const val appCompat = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"
    const val materialComponents = "com.google.android.material:material:${Versions.MATERIAL}"
    const val androidKtx = "androidx.core:core-ktx:${Versions.ANDROID_KTX}"
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.KOTLIN}"
    const val agp = "com.android.tools.build:gradle:${Versions.GRADLE}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"
    const val localBroadcast =
        "androidx.localbroadcastmanager:localbroadcastmanager:${Versions.LOCAL_BROADCAST}"
    const val mavenPublish = "com.vanniktech:gradle-maven-publish-plugin:${Versions.PUBLISH}"
    const val dokka ="org.jetbrains.dokka:dokka-gradle-plugin:${Versions.DOKKA}"

}

object TestDeps {
    const val junit = "junit:junit:${Versions.JUNIT}"
}