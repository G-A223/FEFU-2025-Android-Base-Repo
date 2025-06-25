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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Icon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign


@Composable
fun RatingChart(people: List<Int>) {
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

    var sumOfVoters = 0
    people.forEach { num ->
        sumOfVoters = sumOfVoters + num
    }

    var stars = 10
    var frac = 0.5f

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Оценки пользователей",
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier
                .padding(bottom = 10.dp)
        )

        people.forEach { num ->
            frac = people[stars - 1].toFloat() / sumOfVoters.toFloat()

            Row(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .padding(end = 10.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "$stars",
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
                            .background(colors[stars - 1])
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
                            text = "${people[stars - 1]}",
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(end = 4.dp)
                        )
                    }
                }
            }

            stars -= 1
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ChartPreview() {
    RatingChart(listOf(100, 345, 121, 208, 99, 134, 54, 1000, 21, 33))
}


