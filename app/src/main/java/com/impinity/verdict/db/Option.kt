package com.impinity.verdict.com.impinity.verdict.db

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Option (
    @PrimaryKey(autoGenerate = true) @NonNull val optionID: Int,
    @ColumnInfo(name = "option_name") var optionName: String?,
    @ColumnInfo(name = "trial_name") var trialName: String?
)
{
    @Ignore
    constructor(optionName: String?, trialName: String?) : this(0, optionName, trialName)
}