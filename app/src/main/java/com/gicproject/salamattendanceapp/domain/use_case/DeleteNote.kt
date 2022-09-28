package com.gicproject.salamattendanceapp.domain.use_case

import com.gicproject.emojisurveyapp.domain.model.CustomerInput
import com.gicproject.salamattendanceapp.domain.repository.MyRepository

class DeleteCustomerInput(
    private val repository: MyRepository
) {

    suspend operator fun invoke(customerInput: CustomerInput) {
        repository.deleteCustomer(customerInput = customerInput)
    }
}