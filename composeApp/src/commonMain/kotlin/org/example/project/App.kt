package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.ceil


enum class SizeCard(val widthHeight: Pair<Dp, Dp>) {
    SMALL(100.dp to 150.dp),
    MEDIUM(150.dp to 200.dp),
    LARGE(200.dp to 250.dp)
}

data class Card(
    val id: Int,
    val image: Any?,
    val title: String,
    val rating: Double,
    val size: SizeCard
)

const val TAG = "MainApp"
private const val COUNT_LINE = 3
private const val COUNT_CARDS = 200

private fun pr(str: String) = println("$TAG | $str")
private fun isEvenOrZero(int: Int) = int % COUNT_LINE == 0 || int == 0
private fun createEvenList(count: Int): List<Int> {
    val list = mutableListOf<Int>()
    for (i in 0 until count) if (isEvenOrZero(i)) list.add(i)
    return list
}

@Composable
@Preview
fun App() {
    MaterialTheme {
//        val greeting = remember { Greeting().greet() }
        val state = LazyListState()
        var showContent by remember { mutableStateOf(false) }
        val cardList by remember { mutableStateOf<List<Card>>(createCardList()) }
        val evenList by remember { mutableStateOf<List<Int>>(createEvenList(cardList.count())) }

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(!showContent) {
                LazyColumn(
                    state = state,
                    modifier = Modifier.fillMaxSize().border(2.dp, Color.Green)
                ) {
                    items(evenList.count()) { index ->
                        val evenInt = evenList[index]
                        CardMain(evenInt, cardList)
                    }
                }
            }
        }
    }
}

@Composable
private fun CardComponent(id: Int, width: Dp, height: Dp) {
    Column(
        modifier = Modifier
            .width(width)
            .height(height)
            .border(2.dp, Color.Red)
    ) {
        Text("$id")
    }
}


@Composable
private fun CardMain(startInt: Int, listCard: List<Card>) {
    val lastIndex = listCard.lastIndex
    val indexWithCountElement = startInt + COUNT_LINE
    val endInt = if (indexWithCountElement > lastIndex) listCard.count() else indexWithCountElement

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        for (i in startInt until endInt) {
            val width = listCard[i].size.widthHeight.first
            val height = listCard[i].size.widthHeight.second
            CardComponent(i, width, height)
        }
    }
}


private fun createCardList(count: Int = COUNT_CARDS): List<Card> {
    val list = mutableListOf<Card>()
    for (i in 0 until count) {
        list.add(createCard(i))
    }
    return list
}

private fun createCard(id: Int): Card {
    return Card(
        id = id,
        image = "Image#$id",
        title = "Title $id",
        rating = id.toDouble(),
        size = SizeCard.SMALL
    )
}




