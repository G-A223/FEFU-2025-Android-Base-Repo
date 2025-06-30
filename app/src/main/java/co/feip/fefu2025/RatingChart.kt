package co.feip.fefu2025

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Icon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import co.feip.fefu2025.data.api.ScoreStat


@Composable
fun RatingChart(ratings: List<ScoreStat>) {
    val colors = listOf(
        Color(0xFFFF0000),
        Color(0xFFFF6F00),
        Color(0xFFFFA200),
        Color(0xFFFFD500),
        Color(0xFFEEFF00),
        Color(0xFFC8FF00),
        Color(0xFFA6FF00),
        Color(0xFF88FF00),
        Color(0xFF62FF00),
        Color(0xFF00FF07)
    )

    val sumOfVoters = ratings.sumOf { it.votes }.toFloat()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "User reviews",
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier
                .padding(bottom = 10.dp)
        )

        ratings.forEach { stat ->
            val frac = if (sumOfVoters > 0) stat.votes.toFloat() / sumOfVoters else 0f

            Row(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .padding(end = 10.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${stat.score}",
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(end = 4.dp)
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.star),
                            contentDescription = null,
                            tint = Color(0xFFFFC107),
                            modifier = Modifier
                                .size(24.dp)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 6.dp)
                        .height(20.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(50.dp))
                            .background(Color.LightGray)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(fraction = frac)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(50.dp))
                            .background(colors[stat.score - 1])
                    )

                }

                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .padding(end = 10.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${stat.votes} (${"%.1f".format(stat.percentage)}%)",
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(end = 4.dp)
                        )
                    }
                }
            }
        }
    }
}


