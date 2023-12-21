package com.example.mycity.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycity.R
import com.example.mycity.data.Category
import com.example.mycity.data.Location
import com.example.mycity.data.LocationData.locationList
import com.example.mycity.ui.utils.NavigationType

@Composable
fun EachCategoryOnlyContent(
    uiState: MyCityUiState,
    onCardPressed: (Location) -> Unit,
    modifier: Modifier = Modifier,
    sublist: List<Location> = locationList,
    onBackButtonClicked: () -> Unit,
    navigationType: NavigationType
) {

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        item {
            EachCategoryScreenTopBar(
                onBackButtonClicked = onBackButtonClicked,
                uiState = uiState,
                modifier = modifier,
                navigationType = navigationType
            )
        }

        items(
            count = sublist.size,
            key = { index -> sublist[index].hashCode()}
        ) { index ->
            LocationListItem(
                location = sublist[index],
                selected = false,
                onCardClick = {
                    onCardPressed(sublist[index])
                }
            )

        }
    }
}


//これはlistで表示する用、画像は小さく説明は短い版を
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationListItem(
    location: Location,
    selected: Boolean,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = if (selected)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.secondaryContainer
        ),
        onClick = onCardClick
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(72.dp)
                    .padding(8.dp)
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = location.photoId),
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {


                Text(
                    text = stringResource(id = location.nameId),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(
                        top = 12.dp,
                        bottom = 8.dp
                    ),
                )
                Text(
                    text = stringResource(id = location.shortDescriptionId),
                    style = MaterialTheme.typography.bodyMedium,
                    //maxLines = 2,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@Composable
fun CategoryListContent(
    onTabPressed: (Category) -> Unit,
    modifier: Modifier = Modifier
){


    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {



        items(
            count = Category.values().size,
            key = { index -> Category.values()[index]}
        ) { index ->
            CategoryListItem(
                category = Category.values()[index],
                selected = false,
                onTabClick = {
                    onTabPressed(Category.values()[index])
                }
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryListItem(
    category: Category,
    selected: Boolean,
    onTabClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = if (selected)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.secondaryContainer
        ),
        onClick = onTabClick
    ) {
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            
            Text(
                text = category.toString(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(
                    top = 12.dp,
                    bottom = 8.dp
                ),
            )

        }

    }
}

@Composable
private fun EachCategoryScreenTopBar(
    onBackButtonClicked: () -> Unit,
    uiState: MyCityUiState,
    modifier: Modifier = Modifier,
    navigationType: NavigationType
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {

        if(navigationType== NavigationType.PAGE_TYPE ) {
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
                text = stringResource(R.string.Category) +  " : " + uiState.currentCategory.toString(),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}



@Preview
@Composable
fun PreviewEachCategoryOnlyContent(){
    EachCategoryOnlyContent(
        uiState = MyCityUiState(),
        onCardPressed = {},
        modifier= Modifier,
        sublist = locationList,
        navigationType = NavigationType.PAGE_TYPE,
        onBackButtonClicked = {}

    )
}

