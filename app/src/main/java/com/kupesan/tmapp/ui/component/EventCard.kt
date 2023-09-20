package com.kupesan.tmapp.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kupesan.domain.model.EventModel
import com.kupesan.tmapp.R
import com.kupesan.tmapp.ui.theme.TMAppTheme
import com.kupesan.tmapp.ui.theme.cardBackgroundColor
import com.kupesan.tmapp.ui.theme.cardBorderColor

@Composable
fun EventCard(data: EventModel) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier =
        Modifier
            .fillMaxWidth()
            .height(120.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor),
        border = BorderStroke(1.dp, cardBorderColor),
        shape = RoundedCornerShape(12.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            AsyncImage(
                modifier = Modifier
                    .width(140.dp)
                    .fillMaxHeight()
                    .padding(end = 6.dp)
                    .clip(RoundedCornerShape(bottomStart = 4.dp, topStart = 4.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data.imageUrl)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.error_image),
                placeholder = painterResource(R.drawable.placeholder),
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier
                    .padding(all = 6.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = data.title,
                    Modifier.padding(bottom = 6.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
                if (!data.date.isNullOrEmpty())
                    Text(
                        text = data.date!!,
                        Modifier
                            .padding(bottom = 4.dp),
                        style = MaterialTheme.typography.labelSmall
                    )
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )
                if (!data.venue.isNullOrEmpty())
                    Text(
                        text = data.venue!!,
                        Modifier.padding(bottom = 4.dp),
                        style = MaterialTheme.typography.labelSmall
                    )
                if (!data.location.isNullOrEmpty())
                    Text(
                        text = data.location!!,
                        Modifier.padding(bottom = 4.dp),
                        style = MaterialTheme.typography.labelSmall
                    )
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun PreviewEventCard() {
    TMAppTheme {
        Surface {
            EventCard(
                EventModel(
                    id = "XYZ1",
                    title = "Hello there!",
                    date = "Sep 30",
                    null, "Location", "Venue"
                )
            )
        }
    }
}