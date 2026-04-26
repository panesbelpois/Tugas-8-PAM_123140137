package com.newsreader.platform

import android.os.Build
import org.example.project.BuildConfig

actual class DeviceInfo {
    actual fun getDeviceName(): String = Build.MODEL
    actual fun getOsVersion(): String = "Android " + Build.VERSION.RELEASE
    actual fun getAppVersion(): String = BuildConfig.VERSION_NAME
}
