package com.example.mycity.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mycity.R
import com.example.mycity.data.Location
import com.example.mycity.ui.utils.NavigationType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCityApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    val viewModel: MyCityViewModel = viewModel()
    //val uiState = viewModel.uiState.collectAsState().value
    val uiState by viewModel.uiState.collectAsState()


    val navigationType = when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            NavigationType.PAGE_TYPE
        }
        WindowWidthSizeClass.Medium -> {
            NavigationType.THREE_ROWS_TYPE
        }
        WindowWidthSizeClass.Expanded -> {
            NavigationType.THREE_ROWS_TYPE
        }
        else -> {
            NavigationType.PAGE_TYPE
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CenterAlignedTopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.displayLarge
                    )
                }
            },
            modifier = modifier
        )

        Spacer(modifier = Modifier.height(16.dp))
        HomeScreen(
            navigationType = navigationType,
            uiState = uiState,
            onCardPressed = { location: Location ->
                viewModel.updateDetailsScreenStates(location)
                //これはlocationもcategoryもup dataする
            },
            //onDetailScreenBackPressed = {
            //        viewModel.updateCategoryScreenStates(uiState.currentCategory)
                    //categoryのなかで一番上のものにして初期化
            //},
            modifier = modifier,
            onTabPressed = { category ->
                viewModel.updateCategoryScreenStates(category)
            },
            backToEachCategory = { viewModel.backToEachCategory() },
            backToHome = { viewModel.backToHome() }

        )
    }
}
