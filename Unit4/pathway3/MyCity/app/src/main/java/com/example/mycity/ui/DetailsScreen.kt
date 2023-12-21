package com.example.mycity.ui


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mycity.R
import com.example.mycity.data.Category
import com.example.mycity.data.Location
import com.example.mycity.ui.utils.NavigationType

@Composable
fun DetailsScreen(
    uiState: MyCityUiState,
    onBackPressed: () -> Unit,
    //onDetailScreenBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    navigationType: NavigationType
) {
    BackHandler {
        onBackPressed()
    }
    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.inverseOnSurface)
                .padding(top = 24.dp)
        ) {
            item {
                DetailsScreenTopBar(
                    onBackPressed,
                    uiState,
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    navigationType = navigationType
                )
                DetailsCard(
                    location = uiState.currentSelectedLocation,
                    modifier = Modifier.padding(horizontal = 24.dp),
                    navigationType = navigationType
                )
            }
        }
    }
}

@Composable
private fun DetailsScreenTopBar(
    onBackButtonClicked: () -> Unit,
    uiState: MyCityUiState,
    modifier: Modifier = Modifier,
    navigationType:NavigationType
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {

        if(navigationType==NavigationType.PAGE_TYPE ) {
            IconButton(
                onClick = onBackButtonClicked,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .background(MaterialTheme.colorScheme.surface, shape = CircleShape),
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.navigation_back)
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp)
        ) {
            Text(
                text = stringResource(uiState.currentSelectedLocation.nameId),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun DetailsCard(
    location: Location,
    modifier: Modifier = Modifier,
    navigationType: NavigationType,
    //デバイスのサイズ→画像のサイズ
) {

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            val height: Dp
            val width: Dp

            when (navigationType) {
                NavigationType.PAGE_TYPE -> {
                    height = 256.dp
                    width = 256.dp
                }
                else -> {
                    height = 400.dp
                    width = 600.dp
                }


            }

            Box(modifier = modifier) {
                Image(
                    modifier = Modifier
                        .height(height)
                        .width(width)
                        .padding(8.dp)
                        .clip(MaterialTheme.shapes.small),
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = location.photoId),
                    contentDescription = null
                )
            }

            Text(
                text = stringResource(location.nameId),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
            )

            Text(
                text = stringResource(location.detailId),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

        }
    }
}
@Preview
@Composable
fun previewDetailsScreen() {
    DetailsScreen(
        uiState = MyCityUiState() ,
        onBackPressed = {},
        modifier = Modifier,
        navigationType = NavigationType.THREE_ROWS_TYPE
    )
}