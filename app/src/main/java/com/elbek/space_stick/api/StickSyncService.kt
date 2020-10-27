package com.elbek.space_stick.api

class StickSyncService(private val apiController: ApiSyncController) {

    suspend fun checkConnection(url: String) =
        apiController.checkConnection("$url/api/check")

    suspend fun switchMode(url: String, ssid: String, password: String) =
        apiController.switchMode(url + "/api/switch/mode?ssid=${ssid}&password=${password}")

    suspend fun setPattern(url: String, pattern: Int) =
        apiController.setPattern(url + "/api/mode?pattern=${pattern}")

    suspend fun setSpeed(url: String, speed: Int) =
        apiController.setSpeed(url + "/api/mode/speed?s=${speed}")

    suspend fun setBrightness(url: String, brightness: Int) =
        apiController.setBrightness(url + "/api/mode/brightness?b=${brightness}")

    suspend fun setColor(url: String, r: Int, g: Int, b: Int) =
        apiController.setPatternColor(url + "/api/mode/pattern/color?r=${r}&g=${g}&b=${b}")
}
