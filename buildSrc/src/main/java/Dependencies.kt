object Versions {


    //app
    const val compileSdk = 28
    const val minSdk = 21
    const val targetSdk = 28
    const val appId = "com.hr.blackbox"

    const val GRADLE = "4.0.1"

    // Android libraries
    const val APP_COMPAT = "1.2.0"
    const val MATERIAL = "1.1.0"
    const val ANDROID_KTX = "1.1.0"
    const val CONSTRAINT_LAYOUT = "2.0.1"

    // Kotlin
    const val KOTLIN = "1.3.72"
    const val JUNIT = "4.12"

    const val LOCAL_BROADCAST = "1.0.0"

}

object Deps {
    val appCompat = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"
    val materialComponents = "com.google.android.material:material:${Versions.MATERIAL}"
    val androidKtx = "androidx.core:core-ktx:${Versions.ANDROID_KTX}"
    val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"
    val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.KOTLIN}"
    val agp = "com.android.tools.build:gradle:${Versions.GRADLE}"
    val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"
    val localBroadcast =
        "androidx.localbroadcastmanager:localbroadcastmanager:${Versions.LOCAL_BROADCAST}"
}

object TestDeps {
    val junit = "junit:junit:${Versions.JUNIT}"
}