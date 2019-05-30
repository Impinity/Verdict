package com.impinity.verdict.com.impinity.verdict

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OptionDao {
    @Query("SELECT option_name FROM Option WHERE trial_name = (:trialName)")
    fun getTrialOptions(trialName: String): List<String>

    @Insert
    fun insertAll(vararg options: Option)

    @Delete
    fun delete(optionName: Option)
}