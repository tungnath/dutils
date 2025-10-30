package screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex


// Navigation Item Data Class
data class NavigationItem(
    val icon: ImageVector, val label: String, val isSelected: Boolean = false
)

// Dashboard Tile Data Class
data class DashboardTile(
    val title: String,
    val subtitle: String = "",
    val icon: ImageVector,
    val background: Brush,
    val textColor: Color = Color.White,
    val onClick: () -> Unit,
    val isEnabled: Boolean = true
)

@Composable
fun HomeScreen(
    onNavigateToFileOps: () -> Unit, onLogout: () -> Unit
) {
    var selectedNavItem by remember { mutableStateOf("Home") }
    var isDrawerOpen by remember { mutableStateOf(false) }

    // Navigation Items
    val navigationItems = listOf(
        NavigationItem(Icons.Default.Home, "Home", selectedNavItem == "Home"),
        NavigationItem(Icons.Default.Folder, "Files", selectedNavItem == "Files"),
        NavigationItem(Icons.Default.Settings, "Settings", selectedNavItem == "Settings"),
        NavigationItem(Icons.Default.Person, "Profile", selectedNavItem == "Profile")
    )

    // Dashboard Tiles
    val dashboardTiles = listOf(
        DashboardTile(
            title = "File Operations",
            subtitle = "Rename & Organize",
            icon = Icons.Default.Folder,
            background = Brush.verticalGradient(
                colors = listOf(Color(0xffeadb3c), Color(0xfff3e87a))
            ),
            textColor = Color(0xFF333333),
            onClick = onNavigateToFileOps,
            isEnabled = true
        ),
        DashboardTile(
            title = "Weather",
            subtitle = "20°C\nSunny Day",
            icon = Icons.Default.Cloud,
            background = Brush.verticalGradient(
                colors = listOf(Color(0xFF2196F3), Color(0xFF1976D2))
            ),
            onClick = { /* TODO */ },
            isEnabled = false
        ),
        DashboardTile(
            title = "Calendar",
            subtitle = "Today's Events",
            icon = Icons.Default.DateRange,
            background = Brush.verticalGradient(
                colors = listOf(Color(0xfff64fe8), Color(0xfff386e1))
            ),
            onClick = { /* TODO */ },
            isEnabled = false
        ),
        DashboardTile(
            title = "Tasks",
            subtitle = "Pending Items",
            icon = Icons.Default.CheckCircle,
            background = Brush.verticalGradient(
                colors = listOf(Color(0xFF00BCD4), Color(0xFF0097A7))
            ),
            onClick = { /* TODO */ },
            isEnabled = false
        ),
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Main Content Area (always visible, full screen)
        Column(
            modifier = Modifier.fillMaxSize().background(Color(0xFFF8FAFF))
        ) {
            // Top Bar with Hamburger Menu
            TopBarWithHamburger(
                onMenuClick = { isDrawerOpen = !isDrawerOpen })

            // Welcome Section
            WelcomeSection()

            // Dashboard Grid
            DashboardGrid(tiles = dashboardTiles)
        }

        // Overlay (dimmed background when drawer is open)
        if (isDrawerOpen) {
            Box(
                modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)).clickable(
                    interactionSource = remember { MutableInteractionSource() }, indication = null
                ) {
                    isDrawerOpen = false
                }.zIndex(1f)
            )
        }

        // Floating Navigation Drawer
        AnimatedVisibility(
            visible = isDrawerOpen,
            enter = slideInHorizontally(initialOffsetX = { -it }),
            exit = slideOutHorizontally(targetOffsetX = { -it }),
            modifier = Modifier.zIndex(2f)
        ) {
            FloatingNavigationDrawer(
                navigationItems = navigationItems,
                selectedNavItem = selectedNavItem,
                onNavItemClick = { item ->
                    selectedNavItem = item
                    isDrawerOpen = false
                },
                onLogout = {
                    onLogout()
                    isDrawerOpen = false
                },
                onClose = { isDrawerOpen = false })
        }
    }
}

@Composable
private fun TopBarWithHamburger(
    onMenuClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().height(72.dp).padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left side - Hamburger Menu and Title
        Row(
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Hamburger Menu Icon
            IconButton(
                onClick = onMenuClick, modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = Color(0xFF333333),
                    modifier = Modifier.size(28.dp)
                )
            }

            Text(
                text = "Home",
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Search Bar
            OutlinedTextField(
                value = "",
                onValueChange = { },
                placeholder = { Text("Search...", color = Color.Gray) },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search, contentDescription = "Search", tint = Color.Gray
                    )
                },
                modifier = Modifier.width(280.dp).height(40.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.White,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color(0xFF1565C0)
                ),
                shape = RoundedCornerShape(20.dp)
            )
        }

        // Right side - Notifications and Profile
        Row(
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Notification Bell
            IconButton(
                onClick = { }) {
                Box {
                    Icon(
                        Icons.Default.Notifications, contentDescription = "Notifications", tint = Color(0xFF666666)
                    )
                    // Notification badge
                    Box(
                        modifier = Modifier.size(8.dp).background(Color.Red, CircleShape).align(Alignment.TopEnd)
                    )
                }
            }

            // Profile Avatar
            Box(
                modifier = Modifier.size(40.dp).background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFF1565C0), Color(0xFF1976D2))
                    ), shape = CircleShape
                ).clickable { }, contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
private fun FloatingNavigationDrawer(
    navigationItems: List<NavigationItem>,
    selectedNavItem: String,
    onNavItemClick: (String) -> Unit,
    onLogout: () -> Unit,
    onClose: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxHeight().width(280.dp).shadow(16.dp),
        elevation = 16.dp,
        shape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp),
        backgroundColor = Color(0xFF1565C0)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header with Close Button
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // App Logo/Icon
                Row(
                    verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier.size(48.dp).background(Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Apps,
                            contentDescription = "dUTILS",
                            tint = Color(0xFF1565C0),
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Text(
                        text = "dUTILS",
                        color = Color.White,
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Close Button
                IconButton(
                    onClick = onClose, modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close, contentDescription = "Close", tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Navigation Items
            navigationItems.forEach { item ->
                NavigationMenuItem(
                    item = item.copy(isSelected = item.label == selectedNavItem),
                    onClick = { onNavItemClick(item.label) })
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            // Logout Button
            OutlinedButton(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth(0.85f).padding(bottom = 16.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White
                ),
                border = BorderStroke(1.dp, Color.White)
            ) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = Color(0xFF1565C0)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Logout", color = Color(0xFF1565C0))
            }
        }
    }
}

@Composable
private fun NavigationMenuItem(
    item: NavigationItem, onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(0.85f).height(48.dp).background(
            color = if (item.isSelected) Color.White.copy(alpha = 0.2f) else Color.Transparent,
            shape = RoundedCornerShape(12.dp)
        ).clickable { onClick() }.padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.label,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = item.label,
            color = Color.White,
            style = MaterialTheme.typography.body1,
            fontWeight = if (item.isSelected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

@Composable
private fun WelcomeSection() {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp, vertical = 16.dp),
        elevation = 4.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "Welcome back!",
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Choose a utility from the options below to get started.",
                style = MaterialTheme.typography.body1,
                color = Color(0xFF666666)
            )
        }
    }
}

@Composable
private fun DashboardGrid(tiles: List<DashboardTile>) {
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
                        backgroundColor = Color(0xFFFF9800).copy(alpha = 0.9f),
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