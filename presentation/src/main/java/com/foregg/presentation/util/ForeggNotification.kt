package com.foregg.presentation.util

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.foregg.domain.model.enums.NotificationType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

object ForeggNotification {
    val calendarNotificationStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val ledgerNotificationStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val todayRecordNotificationStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)

    suspend fun init(context: Context) {
        val preferences = context.dataStore.data.first()
        calendarNotificationStateFlow.update { preferences[PreferenceKeys.KEY_CALENDAR] ?: false }
        ledgerNotificationStateFlow.update { preferences[PreferenceKeys.KEY_LEDGER] ?: false }
        todayRecordNotificationStateFlow.update { preferences[PreferenceKeys.KEY_TODAY_RECORD] ?: false }
    }

    fun updateNoty(context: Context, type : NotificationType, value : Boolean) {
        val key = when(type){
            NotificationType.TODAY_RECORD_MALE -> PreferenceKeys.KEY_TODAY_RECORD
            NotificationType.CALENDAR -> PreferenceKeys.KEY_CALENDAR
            NotificationType.LEDGER -> PreferenceKeys.KEY_LEDGER
            else -> PreferenceKeys.KEY_NOTHING
        }
        CoroutineScope(Dispatchers.IO).launch {
            context.dataStore.edit { preferences ->
                preferences[key] = value
            }
        }
    }
}

object PreferenceKeys {
    val KEY_CALENDAR = booleanPreferencesKey("calendar_key")
    val KEY_LEDGER = booleanPreferencesKey("ledger_key")
    val KEY_TODAY_RECORD = booleanPreferencesKey("today_record_key")
    val KEY_NOTHING = booleanPreferencesKey("nothing")
}