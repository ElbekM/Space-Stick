package com.elbek.space_stick.api

class StickService(private val apiController: ApiController) {

    suspend fun checkConnection() = apiController.checkConnection()

    suspend fun switchMode(ssid: String, password: String) = apiController.switchMode(ssid, password)

    suspend fun setPattern(pattern: Int) = apiController.setPattern(pattern)

    suspend fun setSpeed(speed: Int) = apiController.setSpeed(speed)

    suspend fun setBrightness(brightness: Int) = apiController.setBrightness(brightness)

    suspend fun setColor(r: Int, g: Int, b: Int) = apiController.setPatternColor(r, g, b)
}
