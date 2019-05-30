package com.impinity.verdict.com.impinity.verdict

import androidx.room.Database

@Database(entities = arrayOf(Option::class), version = 1)
abstract class AppDatabase {
    abstract fun OptionDao(): OptionDao
}