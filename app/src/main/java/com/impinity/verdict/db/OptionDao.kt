package com.impinity.verdict.com.impinity.verdict.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface OptionDao {
    @Query("SELECT DISTINCT trial_name FROM Option")
    fun getAllTrials(): LiveData<List<String>>

    @Query("SELECT option_name FROM Option WHERE trial_name = (:trialName)")
    fun getTrialOptions(trialName: String?): List<String>

    @Query("DELETE FROM Option WHERE trial_name = (:trialName)")
    fun deleteAllFromTrial(trialName: String?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg options: Option)

    @Delete
    fun delete(optionName: Option)
}