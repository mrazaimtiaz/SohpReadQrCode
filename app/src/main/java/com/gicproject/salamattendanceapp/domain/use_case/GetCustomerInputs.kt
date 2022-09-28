package com.gicproject.salamattendanceapp.domain.use_case

import com.gicproject.emojisurveyapp.domain.model.CustomerInput
import com.gicproject.salamattendanceapp.domain.repository.MyRepository
import com.gicproject.salamattendanceapp.utils.CustomerInputOrder
import com.gicproject.salamattendanceapp.utils.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCustomerInputs(
    private val repository: MyRepository
) {

    operator fun invoke(
        noteOrder: CustomerInputOrder = CustomerInputOrder.Timestamp(OrderType.Descending)
    ): Flow<List<CustomerInput>> {
        return repository.getCustomerSupport().map { customerSupport ->
            when(noteOrder.orderType) {
                is OrderType.Ascending -> {
                    when(noteOrder) {
                        is CustomerInputOrder.Timestamp -> customerSupport.sortedBy { it.timestamp }
                        is CustomerInputOrder.InputId -> customerSupport.sortedBy { it.answerId.lowercase() }
                        is CustomerInputOrder.InputMessage -> customerSupport.sortedBy { it.value.lowercase() }
                        is CustomerInputOrder.Location -> customerSupport.sortedBy { it.location.lowercase() }
                        is CustomerInputOrder.MobileNumber -> customerSupport.sortedBy { it.mobileNumber.lowercase() }
                        is CustomerInputOrder.QuestionId -> customerSupport.sortedBy { it.questionId.lowercase() }
                        is CustomerInputOrder.IsSaved -> customerSupport.sortedBy { it.isSaved }

                    }
                }
                is OrderType.Descending -> {
                    when(noteOrder) {
                        is CustomerInputOrder.Timestamp -> customerSupport.sortedByDescending { it.timestamp }
                        is CustomerInputOrder.InputId -> customerSupport.sortedByDescending { it.answerId.lowercase() }
                        is CustomerInputOrder.InputMessage -> customerSupport.sortedByDescending { it.value.lowercase() }
                        is CustomerInputOrder.Location -> customerSupport.sortedByDescending { it.location.lowercase() }
                        is CustomerInputOrder.MobileNumber -> customerSupport.sortedByDescending { it.mobileNumber.lowercase() }
                        is CustomerInputOrder.QuestionId -> customerSupport.sortedByDescending { it.questionId.lowercase() }
                        is CustomerInputOrder.IsSaved -> customerSupport.sortedByDescending { it.isSaved }

                    }
                }
            }
        }
    }
}
