package com.impinity.verdict.com.impinity.verdict

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.impinity.verdict.com.impinity.verdict.db.Option
import com.impinity.verdict.com.impinity.verdict.db.OptionDao

class OptionRepository (private val optionDao: OptionDao) {
    val allTrials: LiveData<List<String>> = optionDao.getAllTrials()

    @WorkerThread
    suspend fun insert(options: Option) {
        optionDao.insertAll(options)
    }

    @WorkerThread
    suspend fun delete(option: Option) {
        optionDao.delete(option)
    }

    fun getTrialOptions(trialName: String?) : List<String> {
        return optionDao.getTrialOptions(trialName)
    }

}