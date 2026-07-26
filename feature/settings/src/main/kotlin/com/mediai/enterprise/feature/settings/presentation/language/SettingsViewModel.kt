package com.mediai.enterprise.feature.settings.presentation.language

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mediai.enterprise.core.data.prefs.UserPreferencesSerializer
import com.mediai.enterprise.core.ui.util.LocaleHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        val currentLang = LocaleHelper.getLanguageCode(context)
        _uiState.update { it.copy(selectedLanguage = currentLang) }
    }

    fun onLanguageSelected(context: Context, languageCode: String) {
        _uiState.update { it.copy(selectedLanguage = languageCode) }
        LocaleHelper.applyLocale(context, languageCode)

        // In a real implementation, we would also update the Proto DataStore
        // to persist this preference.
    }
}

data class SettingsUiState(
    val selectedLanguage: String = "en",
    val languages: List<LanguageOption> = listOf(
        LanguageOption("English", "en"),
        LanguageOption("Spanish", "es"),
        LanguageOption("Arabic", "ar")
    )
)

data class LanguageOption(val name: String, val code: String)
