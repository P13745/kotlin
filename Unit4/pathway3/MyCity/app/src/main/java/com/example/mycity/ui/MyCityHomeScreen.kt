package com.example.mycity.ui



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mycity.data.Category
import com.example.mycity.data.Location
import com.example.mycity.data.LocationData.locationList
import com.example.mycity.ui.utils.NavigationType

@Composable
fun HomeScreen(
    navigationType: NavigationType,
    uiState: MyCityUiState,
    onTabPressed: (Category) -> Unit,
    onCardPressed: (Location) -> Unit,
    modifier: Modifier = Modifier,
    backToHome: () -> Unit,
    backToEachCategory: () -> Unit
) {




    if (navigationType == NavigationType.THREE_ROWS_TYPE

    ) {
        val selectedDestinationLocation by remember { mutableStateOf(uiState.currentSelectedLocation) }
        PermanentNavigationDrawer(
            drawerContent = {
                PermanentDrawerSheet(Modifier.width(155.dp)) {

                    NavigationDrawerContent(
                        selectedDestination = uiState.currentCategory,
                        onTabPressed = onTabPressed,
                        //navigationItemContentList = navigationItemContentList,
                        modifier = Modifier
                            //.wrapContentWidth()
                            .fillMaxHeight()
                            .background(MaterialTheme.colorScheme.inverseOnSurface)
                            .padding(4.dp)
                    )

                }
            }
        ) {
            PermanentNavigationDrawer(
                drawerContent = {
                    PermanentDrawerSheet(Modifier.width(160.dp)) {

                        CategoryNavigationDrawerContent(
                            selectedCategory = uiState.currentCategory,
                            selectedDestinationLocation = selectedDestinationLocation,
                            onCardPressed = onCardPressed,
                            //navigationItemContentList = navigationItemContentList,
                            modifier = Modifier
                                //.wrapContentWidth()
                                .fillMaxHeight()
                                .background(MaterialTheme.colorScheme.inverseOnSurface)
                                .padding(4.dp)
                        )

                    }
                }
            ) {

                DetailsScreen(
                    navigationType = navigationType,
                    uiState = uiState,
                    onBackPressed = backToEachCategory,
                )
            }
        }
    } else {

        if (uiState.isShowingDetails) {
            DetailsScreen(
                navigationType = navigationType,
                uiState = uiState,
                onBackPressed = backToEachCategory,
                modifier = modifier,
            )

        } else if (uiState.isShowingCategory) {

            EachCategoryOnlyContent(
                uiState = uiState,
                onCardPressed = onCardPressed,
                modifier = Modifier
                    //.weight(1f)
                    .padding(horizontal = 16.dp),
                navigationType = navigationType,
                onBackButtonClicked = backToHome,
                sublist = locationList.filter { it.category == uiState.currentCategory}
            )
        } else {

            //これはカテゴリーが並んでる(初めの画面)
            CategoryListContent(
                onTabPressed = onTabPressed
            )

        }
    }
}




//RailとBarによるCategoryの選択は無しで
//いや、三列の時はRailで

@Composable
private fun NavigationDrawerContent(
    selectedDestination: Category,
    onTabPressed: ((Category) -> Unit),
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {

        for (navItem in Category.values()) {
            NavigationDrawerItem(
                selected = selectedDestination == navItem,
                label = {
                    Text(
                        text = navItem.toString(),
                        modifier = Modifier.padding(8.dp)
                    )
                },
                icon = {},
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = Color.Transparent
                ),
                onClick = { onTabPressed(navItem) }
            )
        }
    }
}


//選択したCategory内で
@Composable
fun CategoryNavigationDrawerContent(
    selectedCategory: Category,
    selectedDestinationLocation: Location,
    onCardPressed: (Location) -> Unit,
    modifier: Modifier) {
    Column(modifier = modifier) {

        for (navItem in locationList.filter { it.category == selectedCategory }) {
            NavigationDrawerItem(
                selected = selectedDestinationLocation == navItem,
                label = {
                    Text(
                        text = stringResource(id = navItem.nameId),
                        modifier = Modifier.padding(8.dp)
                    )
                },
                icon = {},
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = Color.Transparent
                ),
                onClick = { onCardPressed(navItem) }
            )
        }
    }

}

