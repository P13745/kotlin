package com.example.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.cupcake.ui.SelectOptionScreen
import org.junit.Rule
import org.junit.Test
import com.example.cupcake.R
import com.example.cupcake.ui.StartOrderScreen
import com.example.cupcake.data.DataSource
import com.example.cupcake.data.OrderUiState
import com.example.cupcake.ui.OrderSummaryScreen

class CupcakeOrderScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val testState = OrderUiState(
        quantity = 6,
        flavor = "Vanilla",
        date = "Wed Jul 21",
        price = "$100",
        pickupOptions = listOf()
    )
    @Test
    fun selectOptionScreen_verifyContent() {

        // Given list of options
        val flavors = listOf("Vanilla", "Chocolate", "Hazelnut", "Cookie", "Mango")
        // And subtotal
        val subtotal = "$100"

        // When SelectOptionScreen is loaded
        composeTestRule.setContent {
            SelectOptionScreen(subtotal = subtotal, options = flavors)
        }

        // Then all the options are displayed on the screen.
        flavors.forEach { flavor ->
            composeTestRule.onNodeWithText(flavor).assertIsDisplayed()
        }

        // And then the subtotal is displayed correctly.
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                R.string.subtotal_price,
                subtotal
            )
        ).assertIsDisplayed()

        // And then the next button is disabled
        composeTestRule.onNodeWithStringId(R.string.next).assertIsNotEnabled()

    }

    @Test
    fun startOrderScreen_verifyContent() {

        composeTestRule.setContent {
            StartOrderScreen(
                quantityOptions = DataSource.quantityOptions,
                onNextButtonClicked = {}
            )
        }

        DataSource.quantityOptions.forEach {
            composeTestRule.onNodeWithStringId(it.first).assertIsDisplayed()
        }
    }

    @Test
    fun selectOptionScreen_optionSelected_NextButtonEnabled() {

        val flavours = listOf("Vanilla", "Chocolate", "Hazelnut", "Cookie", "Mango")

        val subTotal = "$100"

        composeTestRule.setContent {
            SelectOptionScreen(subtotal = subTotal, options = flavours)
        }

        composeTestRule.onNodeWithText("Vanilla").performClick()

        composeTestRule.onNodeWithStringId(R.string.next).assertIsEnabled()
    }

    @Test
    fun summaryScreen_verifyContentDisplay() {
        // When Summary Screen is loaded
        composeTestRule.setContent {
            OrderSummaryScreen(
                orderUiState = testState ,
                onCancelButtonClicked = {},
                onSendButtonClicked = { _, _ -> },
            )
        }

        // Then the UI is updated correctly.
        composeTestRule.onNodeWithText(testState .flavor).assertIsDisplayed()
        composeTestRule.onNodeWithText(testState .date).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                R.string.subtotal_price,
                testState .price
            )
        ).assertIsDisplayed()
    }
}






