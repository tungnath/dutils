package composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import screens.DashboardTile


@Composable
fun DashboardGrid(tiles: List<DashboardTile>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 300.dp),
        contentPadding = PaddingValues(32.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(tiles) { tile ->
            DashboardTileCard(tile = tile)
        }
    }
}

@Composable
private fun DashboardTileCard(tile: DashboardTile) {
    Card(
        modifier = Modifier.fillMaxWidth().height(200.dp).clickable(enabled = tile.isEnabled) {
            if (tile.isEnabled) tile.onClick()
        }, elevation = if (tile.isEnabled) 8.dp else 2.dp, shape = RoundedCornerShape(20.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(tile.background)
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top section - Icon
                Box(
                    modifier = Modifier.size(64.dp).background(
                        color = if (tile.textColor == Color.White) Color.White.copy(alpha = 0.2f)
                        else Color(0xFF1565C0).copy(alpha = 0.1f), shape = RoundedCornerShape(16.dp)
                    ), contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = tile.icon,
                        contentDescription = null,
                        tint = if (tile.textColor == Color.White) Color.White else Color(0xFF1565C0),
                        modifier = Modifier.size(32.dp)
                    )
                }

                // Bottom section - Content
                Column {
                    Text(
                        text = tile.title,
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        color = tile.textColor
                    )
                    if (tile.subtitle.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = tile.subtitle,
                            style = MaterialTheme.typography.body2,
                            color = tile.textColor.copy(alpha = 0.8f),
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // Coming Soon badge for disabled tiles
            if (!tile.isEnabled) {
                Box(
                    modifier = Modifier.align(Alignment.TopEnd).padding(16.dp)
                ) {
                    Card(
                        backgroundColor = Color(0xffffd31d).copy(alpha = 0.9f),
                        shape = RoundedCornerShape(12.dp),
                        elevation = 2.dp
                    ) {
                        Text(
                            text = "Soon",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.caption,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}