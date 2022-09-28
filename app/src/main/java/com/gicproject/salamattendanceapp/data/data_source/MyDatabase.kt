package com.gicproject.salamattendanceapp.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gicproject.emojisurveyapp.domain.model.CustomerInput
import com.gicproject.salamattendanceapp.data.data_source.MyDao


@Database(
    entities = [CustomerInput::class],
    version = 1
)
abstract class MyDatabase: RoomDatabase() {

    abstract val myDao: MyDao

    companion object {
        const val DATABASE_NAME = "survey_db"
    }
}