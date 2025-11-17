package composables

import androidx.compose.material.Text
import androidx.compose.runtime.Composable


@Composable
fun LoremIpsumCmp(a: Int=90) {

    Text(text = "Lorem Ipsum is simply dummy $a")

}