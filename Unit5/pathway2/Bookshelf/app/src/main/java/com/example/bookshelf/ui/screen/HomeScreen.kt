package com.example.bookshelf.ui.screen


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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R


@Composable
fun HomeScreen(
    bookshelfUiState: BookshelfUiState,
    modifier: Modifier = Modifier,
    retryAction: ()->Unit,
){
    when (bookshelfUiState) {
        is BookshelfUiState.Loading -> { LoadingScreen(modifier = modifier.fillMaxSize())}
        is BookshelfUiState.Success -> DescriptionListScreen(bookshelfUiState.data, modifier)
        is BookshelfUiState.Error -> { ErrorScreen(modifier = modifier.fillMaxSize(), retryAction = retryAction) }
    }



}

@Composable
fun LoadingScreen(modifier: Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = null
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
        Text(text ="loading_failed", modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(text= "retry")
        }
    }

}

@Composable
fun DescriptionListScreen(data: List<String>, modifier: Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp)
    ) {
        item {Text(text = data.size.toString()) }
        items(items = data, key = { datum -> datum.hashCode() }) {
                datum -> BookshelfCard(
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
fun BookshelfCard(datum: String, modifier: Modifier) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {


            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(datum.replace("http://", "https://"))
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


    }
}





