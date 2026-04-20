package com.newsreader.presentation.components
import androidx.compose.material3.MaterialTheme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newsreader.ui.theme.Primary
import com.newsreader.ui.theme.PrimaryDark
import com.newsreader.ui.theme.Surface
import com.newsreader.ui.theme.TextSecondary

sealed class BottomNavItem(val title: String, val icon: String, val route: String) {
    data object Home : BottomNavItem("Catatan", "📝", "home")
    data object Saved : BottomNavItem("Tambah", "➕", "saved")
    data object Settings : BottomNavItem("Pengaturan", "⚙️", "settings")
}

@Composable
fun BottomNavBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        shape = RoundedCornerShape(50),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val items = listOf(BottomNavItem.Home, BottomNavItem.Saved, BottomNavItem.Settings)

            items.forEach { item ->
                val selected = currentRoute == item.route
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .clickable { onNavigate(item.route) }
                        .background(if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) else Color.Transparent)
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = item.icon,
                            fontSize = if (selected) 20.sp else 22.sp,
                            color = if (selected) MaterialTheme.colorScheme.scrim else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        if (selected) {
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = item.title,
                                color = MaterialTheme.colorScheme.scrim,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
