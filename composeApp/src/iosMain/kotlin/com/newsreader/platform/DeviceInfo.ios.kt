package com.newsreader.platform

import platform.UIKit.UIDevice

actual class DeviceInfo {
    actual fun getDeviceName(): String = UIDevice.currentDevice.name
    actual fun getOsVersion(): String = UIDevice.currentDevice.systemName + " " + UIDevice.currentDevice.systemVersion
    actual fun getAppVersion(): String = "1.0.0"
}
