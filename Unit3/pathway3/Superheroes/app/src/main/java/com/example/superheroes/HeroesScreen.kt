package com.example.superheroes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.superheroes.data.Hero
import com.example.superheroes.data.HeroesRepository.heroes

@Composable
fun HeroCard(hero: Hero) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .shadow(2.dp,shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp,)
    ){
        Box(
            modifier= Modifier
            .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = stringResource(id = hero.nameRes),
                        style = MaterialTheme.typography.displaySmall
                    )
                    Text(
                        text = stringResource(id = hero.descriptionRes),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Image(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(corner = CornerSize(8.dp))),
                    painter = painterResource(id = hero.imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )

            }
        }
    }

}


@Preview
@Composable
fun PreviewCard(){
    Surface(modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background){
        HeroCard(heroes[0])
    }
}
