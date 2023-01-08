package com.gicproject.salamattendanceapp

sealed class Screen(val route: String) {
    object MainScreen: Screen("main_screen")
    object UserInputScreen: Screen("user_input_screen")
    object EmployeeInfoScreen: Screen("employee_info_screen")
    object TestCameraScreen: Screen("test_camera_screen")
    object DemoScreen: Screen("demo_screen")
    object QrReaderScreen: Screen("qr_reader")
    object SettingScreen: Screen("setting_screen")
    object InsertIdScreen: Screen("insert_id_screen")
    object InsertOtpScreen: Screen("insert_otp_screen")
}

