package com.example.a30daysofwellnes

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.a30daysofwellnes.data.Music

@Composable
fun musicCard(music: Music, index: Int){
    Card(
        modifier = Modifier
            .padding(16.dp)
            .shadow(2.dp, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp,)
    ){
        var expanded by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
        ) {
            Box(modifier = Modifier.padding(4.dp)) {
                Text(
                    text = "Day $index",
                    style = MaterialTheme.typography.headlineLarge
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Box(modifier = Modifier.padding(4.dp)) {
                Text(
                    text = "Listen " + stringResource(id = music.nameRes) + " composed by " + music.composer,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.height(4.dp))

            var variableHeight = if (expanded) {
                300.dp
            } else {
                150.dp
            }

            Image(
                modifier = Modifier
                    .height(variableHeight)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(corner = CornerSize(8.dp))),
                painter = painterResource(id = music.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Spacer(modifier = Modifier)
                HiddenContentButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded },
                    modifier = Modifier
                )
            }

            if (expanded) {
                Column(modifier = Modifier.padding(4.dp)) {
                    Text(
                        text = "detail:",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "This music is Composed by ${music.composer} in " + music.year + ".",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = stringResource(id = music.descriptionRes),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

            }


        }
    }

}


@Composable
private fun HiddenContentButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = stringResource(R.string.expand_button_content_description),
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}