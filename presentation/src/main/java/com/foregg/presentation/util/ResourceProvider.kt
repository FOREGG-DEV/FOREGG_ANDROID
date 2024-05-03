package com.foregg.presentation.util

import android.content.Context
import android.database.Cursor
import android.net.Uri
import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourceProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getString(@StringRes stringResId: Int): String {
        return context.getString(stringResId)
    }

    fun getString(@StringRes stringResId: Int, vararg formatArgs: Any?): String {
        return context.getString(stringResId, *formatArgs)
    }

    fun getColor(@ColorRes colorResId: Int): Int {
        return ContextCompat.getColor(context, colorResId)
    }

    fun getCursor(uri: Uri, proj: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        return context.contentResolver.query(uri, proj, selection, selectionArgs, sortOrder)
    }

    fun getStringArray(@ArrayRes arrayResId: Int): Array<String> {
        return context.resources.getStringArray(arrayResId)
    }
}