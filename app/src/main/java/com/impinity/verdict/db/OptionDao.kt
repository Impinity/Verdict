package com.impinity.verdict.com.impinity.verdict.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OptionDao {
    @Query("SELECT trial_name FROM Option")
    fun getAllTrials(): LiveData<List<String>>

    @Query("SELECT option_name FROM Option WHERE trial_name = (:trialName)")
    fun getTrialOptions(trialName: String): LiveData<List<String>>

    @Insert
    suspend fun insertAll(vararg options: Option)

    @Delete
    fun delete(optionName: Option)
}