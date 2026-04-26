package com.newsreader.platform

expect class BatteryInfo() {
    fun getBatteryLevel(): Int
    fun isCharging(): Boolean
}
