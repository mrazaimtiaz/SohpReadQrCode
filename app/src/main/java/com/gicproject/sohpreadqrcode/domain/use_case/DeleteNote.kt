package com.gicproject.sohpreadqrcode.domain.use_case

import com.gicproject.emojisurveyapp.domain.model.CustomerInput
import com.gicproject.sohpreadqrcode.domain.repository.MyRepository

class DeleteCustomerInput(
    private val repository: MyRepository
) {

    suspend operator fun invoke(customerInput: CustomerInput) {
        repository.deleteCustomer(customerInput = customerInput)
    }
}