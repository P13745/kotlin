package com.example.amphibians.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.amphibians.R
import com.example.amphibians.model.AmphibiansData


@Composable
fun HomeScreen(
    amphibiansUiState: AmphibiansUiState,
    retryAction: ()->Unit,
    modifier: Modifier = Modifier
){
    when (amphibiansUiState) {
        is AmphibiansUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is AmphibiansUiState.Success -> DescriptionListScreen(amphibiansUiState.data, modifier)
        is AmphibiansUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize(), retryAction = retryAction)
    }

}

@Composable
fun LoadingScreen(modifier: Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(modifier: Modifier, retryAction: () -> Unit) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) { 
            Text(stringResource(R.string.retry))
        }
    }

}
@Composable
fun DescriptionListScreen(data: List<AmphibiansData>, modifier: Modifier) {
    LazyColumn(
        //columns = GridCells.Adaptive(150.dp),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(items = data, key = { datum -> datum.hashCode() }) {
                datum -> AmphibiansCard(
            datum,
            modifier = modifier
                .padding(4.dp)
                .fillMaxWidth()
                //.aspectRatio(1.5f)//アスペクト比を渡す
        )
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
fun AmphibiansCard(datum: AmphibiansData, modifier: Modifier) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column() {
            Text(
                text = datum.name + "(" + datum.type + ")",
                modifier = Modifier.padding(4.dp),
                fontSize = 24.sp
            )
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(datum.imgSrc)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img)
            )
            Text(
                text = datum.description,
                modifier = Modifier.padding(4.dp)
            )

        }
    }
}





