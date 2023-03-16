package com.gicproject.sohpreadqrcode.domain.use_case

import com.gicproject.emojisurveyapp.domain.model.CustomerInput
import com.gicproject.sohpreadqrcode.domain.repository.MyRepository

class GetCustomerInput(
    private val repository: MyRepository
) {

    suspend operator fun invoke(id: Int): CustomerInput? {
        return repository.getCustomerInputById(id)
    }
}