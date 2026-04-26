package com.newsreader.platform

import platform.UIKit.UIDevice
import platform.UIKit.UIDeviceBatteryState

actual class BatteryInfo {
    actual fun getBatteryLevel(): Int {
        UIDevice.currentDevice.batteryMonitoringEnabled = true
        val level = UIDevice.currentDevice.batteryLevel
        return if (level < 0f) 100 else (level * 100).toInt()
    }

    actual fun isCharging(): Boolean {
        UIDevice.currentDevice.batteryMonitoringEnabled = true
        val state = UIDevice.currentDevice.batteryState
        return state == UIDeviceBatteryState.UIDeviceBatteryStateCharging ||
                state == UIDeviceBatteryState.UIDeviceBatteryStateFull
    }
}
