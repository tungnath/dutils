package screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

data class UtilityTile(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val onClick: () -> Unit,
    val isEnabled: Boolean = true
)

@Composable
fun HomeScreen(
    onNavigateToFileOps: () -> Unit, onLogout: () -> Unit
) {

    val utilityTiles = listOf(
        UtilityTile(
            title = "File Operations",
            description = "Rename files and folders recursively",
            icon = Icons.Default.FavoriteBorder,
            onClick = onNavigateToFileOps
        ), UtilityTile(
            title = "Text Tools",
            description = "Text manipulation utilities",
            icon = Icons.Default.Edit,
            onClick = { /* TODO: Implement */ },
            isEnabled = false
        ), UtilityTile(
            title = "Image Tools",
            description = "Image processing utilities",
            icon = Icons.Default.Face,
            onClick = { /* TODO: Implement */ },
            isEnabled = false
        ), UtilityTile(
            title = "System Info",
            description = "View system information",
            icon = Icons.Default.Info,
            onClick = { /* TODO: Implement */ },
            isEnabled = false
        ), UtilityTile(
            title = "Network Tools",
            description = "Network utilities and diagnostics",
            icon = Icons.Default.Phone,
            onClick = { /* TODO: Implement */ },
            isEnabled = false
        ), UtilityTile(
            title = "Settings",
            description = "App settings and preferences",
            icon = Icons.Default.Settings,
            onClick = { /* TODO: Implement */ },
            isEnabled = false
        )
    )

    // Modern gradient background
    Box(
        modifier = Modifier.fillMaxSize().background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    MaterialTheme.colors.primary.copy(alpha = 0.05f), MaterialTheme.colors.background
                )
            )
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp)
        ) {
            // Header section
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {


                    Column {
                        Text(
                            text = "dUTILS",
                            style = MaterialTheme.typography.h4,
                            color = MaterialTheme.colors.onBackground,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Desktop Utils",
                            style = MaterialTheme.typography.subtitle1,
                            color = MaterialTheme.colors.onBackground.copy(alpha = 0.7f)
                        )
                    }
                }


                // Logout button
                OutlinedButton(
                    onClick = onLogout, colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colors.error
                    ),
//                    border = ButtonDefaults.outlinedButtonBorder.copy(
//                        brush = Brush.horizontalGradient(
//                            colors = listOf(
//                                MaterialTheme.colors.error,
//                                MaterialTheme.colors.error
//                            )
//                        )
//                    )
                    border = BorderStroke(
                        width = 1.dp, color = MaterialTheme.colors.error
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Logout")
                }
            }

            // Welcome message
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                elevation = 4.dp,
                backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.1f),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "Welcome back!",
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onBackground,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Choose a utility from the options below to get started.",
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onBackground.copy(alpha = 0.8f)
                    )
                }
            }

            // Utility tiles grid
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 280.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(utilityTiles) { tile ->
                    UtilityCard(tile = tile)
                }
            }
        }
    }
}

@Composable
private fun UtilityCard(tile: UtilityTile) {
    Card(
        modifier = Modifier.fillMaxWidth().height(160.dp).clip(RoundedCornerShape(16.dp)),
        elevation = if (tile.isEnabled) 8.dp else 2.dp,
        backgroundColor = if (tile.isEnabled) MaterialTheme.colors.surface
        else MaterialTheme.colors.surface.copy(alpha = 0.6f),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize().then(
                if (tile.isEnabled) {
                    Modifier.clickable { tile.onClick() }
                } else Modifier)) {
            Column(
                modifier = Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Icon
                Box(
                    modifier = Modifier.size(48.dp).background(
                        color = if (tile.isEnabled) MaterialTheme.colors.primary.copy(alpha = 0.1f)
                        else MaterialTheme.colors.onSurface.copy(alpha = 0.1f), shape = RoundedCornerShape(12.dp)
                    ), contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = tile.icon,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = if (tile.isEnabled) MaterialTheme.colors.primary
                        else MaterialTheme.colors.onSurface.copy(alpha = 0.4f)
                    )
                }

                // Content
                Column {
                    Text(
                        text = tile.title,
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.SemiBold,
                        color = if (tile.isEnabled) MaterialTheme.colors.onSurface
                        else MaterialTheme.colors.onSurface.copy(alpha = 0.4f)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = tile.description,
                        style = MaterialTheme.typography.body2,
                        color = if (tile.isEnabled) MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                        else MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
                        textAlign = TextAlign.Start
                    )
                }
            }

            // Coming soon badge for disabled tiles
            if (!tile.isEnabled) {
                Box(
                    modifier = Modifier.align(Alignment.TopEnd).padding(12.dp)
                ) {
                    Card(
                        backgroundColor = MaterialTheme.colors.secondary.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(8.dp),
                        elevation = 2.dp
                    ) {
                        Text(
                            text = "Soon",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.onSecondary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}