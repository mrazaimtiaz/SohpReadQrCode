package com.gicproject.sohpreadqrcode

sealed class Screen(val route: String) {
    object MainScreen: Screen("main_screen")
    object MessageInfoScreen: Screen("message_info_screen")
    object SettingScreen: Screen("setting_screen")
}

