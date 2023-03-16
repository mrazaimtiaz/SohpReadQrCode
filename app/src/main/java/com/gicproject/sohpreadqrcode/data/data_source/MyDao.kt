package com.gicproject.sohpreadqrcode.data.data_source

import androidx.room.*
import com.gicproject.emojisurveyapp.domain.model.CustomerInput
import kotlinx.coroutines.flow.Flow

@Dao
interface MyDao {
    @Query("SELECT * FROM CustomerInput where isSaved = 0")
    fun getCustomerSupport(): Flow<List<CustomerInput>>

    @Query("SELECT * FROM CustomerInput WHERE id = :id")
    suspend fun getCustomerInputById(id: Int): CustomerInput?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomerSupport(customerInput: CustomerInput): Long?

    @Delete
    suspend fun deleteCustomer(customerInput: CustomerInput)
}