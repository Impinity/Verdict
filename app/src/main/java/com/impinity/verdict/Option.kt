package com.impinity.verdict.com.impinity.verdict

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Option (
    @PrimaryKey val optionID: Int,
    @ColumnInfo(name = "option_name") val optionName: String?,
    @ColumnInfo(name = "trial_name") val trialName: String?
)