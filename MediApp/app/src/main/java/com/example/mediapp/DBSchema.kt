package com.example.mediapp

import android.provider.BaseColumns

class DBSchema {

    class UserEntity : BaseColumns {
        companion object {
            val TABLE_NAME = "medicines"
            val COLUMN_USER_ID = "userid"
            val COLUMN_NAME = "medicine_name"
            val COLUMN_DOSAGE = "medicine_dosage"
            val COLUMN_DURATION = "medicine_duration"
            val COLUMN_INTERVAL = "medicine_interval"
            val COLUMN_BEGIN= "medicine_begin"

        }
    }

}