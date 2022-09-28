package com.gicproject.salamattendanceapp.domain.use_case

data class MyUseCases(
    val getAttendance: GetAttendance,
    val getCheckQrCode: GetCheckQrCode,
    val submitAnswer: SubmitAnswer,
    val getCustomerInputs: GetCustomerInputs,
    val deleteCustomerInput: DeleteCustomerInput,
    val addCustomerInput: AddCustomerInput,
    val getCustomerInput: GetCustomerInput,
    val getLocations: GetLocations,
    val getClinics: GetClinics
)
