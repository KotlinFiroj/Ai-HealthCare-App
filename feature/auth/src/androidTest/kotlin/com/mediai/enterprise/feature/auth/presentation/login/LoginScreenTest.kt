package com.mediai.enterprise.feature.auth.presentation.login

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.mediai.enterprise.core.designsystem.theme.MediAITheme
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginScreen_displaysAllComponents() {
        composeTestRule.setContent {
            MediAITheme {
                LoginScreen(
                    uiState = LoginUiState(),
                    onEmailChange = {},
                    onPasswordChange = {},
                    onLoginClick = {},
                    onNavigateToRegister = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Welcome Back").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Login").assertIsDisplayed()
    }

    @Test
    fun loginScreen_showsError_whenErrorStateIsSet() {
        val errorMessage = "Invalid Credentials"
        composeTestRule.setContent {
            MediAITheme {
                LoginScreen(
                    uiState = LoginUiState(error = errorMessage),
                    onEmailChange = {},
                    onPasswordChange = {},
                    onLoginClick = {},
                    onNavigateToRegister = {}
                )
            }
        }

        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }
}
