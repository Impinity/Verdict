package com.impinity.verdict.com.impinity.verdict

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.impinity.verdict.com.impinity.verdict.db.AppDatabase
import com.impinity.verdict.com.impinity.verdict.db.Option
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OptionViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: OptionRepository
    val allTrials: LiveData<List<String>>

    init {
        val optionDao = AppDatabase.getDatabase(application).optionDao()
        repository = OptionRepository(optionDao)
        allTrials = repository.allTrials
    }

    fun insert(option: Option) = viewModelScope.launch(Dispatchers.IO) {
        Log.v("OptionViewModel", "Inserting: ${option.optionName} ${option.trialName} ${option.optionID}")
        repository.insert(option)
    }


    fun getTrialOptions(trialName: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.getTrialOptions(trialName)
    }
}