package com.newsreader.presentation.settings
import androidx.compose.material3.MaterialTheme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Battery5Bar
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newsreader.platform.BatteryInfo
import com.newsreader.platform.DeviceInfo
import com.newsreader.ui.theme.*
import org.koin.compose.koinInject

@Composable
fun SettingsScreen(
    currentTheme: Int,
    isNewestFirst: Boolean,
    onBack: () -> Unit,
    onThemeChange: (Int) -> Unit,
    onSortChange: (Boolean) -> Unit
) {
    // ── [ADDED] Inject platform info via Koin ─────────────────────────────
    val deviceInfo: DeviceInfo = koinInject()
    val batteryInfo: BatteryInfo = koinInject()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {

        // ── TOP HEADER GRADIENT ───────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.verticalGradient(listOf(MaterialTheme.colorScheme.scrim, MaterialTheme.colorScheme.primary)))
                .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
        ) {
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .offset(x = 240.dp, y = (-50).dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.06f))
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp)
                    .padding(top = 14.dp, bottom = 20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .clickable(onClick = onBack)
                        .padding(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.20f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("←", color = Color.White, fontSize = 18.sp)
                    }
                    Spacer(Modifier.width(8.dp))
                    Text("Pengaturan", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column {
                Text(
                    text = "TEMA APLIKASI",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onThemeChange(0) }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("System Default", color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.SemiBold)
                    RadioButton(selected = currentTheme == 0, onClick = { onThemeChange(0) })
                }
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onThemeChange(1) }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Mode Terang (Light)", color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.SemiBold)
                    RadioButton(selected = currentTheme == 1, onClick = { onThemeChange(1) })
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onThemeChange(2) }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Mode Gelap (Dark)", color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.SemiBold)
                    RadioButton(selected = currentTheme == 2, onClick = { onThemeChange(2) })
                }

                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, modifier = Modifier.padding(horizontal = 16.dp))

                Text(
                    text = "URUTAN CATATAN",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Urutkan dari Terabaru", color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.SemiBold)
                    Switch(
                        checked = isNewestFirst,
                        onCheckedChange = onSortChange,
                        colors = SwitchDefaults.colors(checkedThumbColor = MaterialTheme.colorScheme.primary, checkedTrackColor = MaterialTheme.colorScheme.primaryContainer)
                    )
                }
            }
        }

        // ── [ADDED] Informasi Perangkat Card ─────────────────────────────────
        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column {
                Text(
                    text = "INFORMASI PERANGKAT",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                )

                DeviceInfoRow(label = "Nama Perangkat", value = deviceInfo.getDeviceName())
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, modifier = Modifier.padding(horizontal = 16.dp))
                DeviceInfoRow(label = "Versi OS", value = deviceInfo.getOsVersion())
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, modifier = Modifier.padding(horizontal = 16.dp))
                DeviceInfoRow(label = "Versi Aplikasi", value = deviceInfo.getAppVersion())
            }
        }

        // ── [ADDED] Battery Card ──────────────────────────────────────────────
        Spacer(Modifier.height(16.dp))

        BatteryCard(batteryInfo = batteryInfo)

        Spacer(Modifier.height(32.dp))
    }
}

// ── [ADDED] Helper composables ────────────────────────────────────────────────

@Composable
private fun DeviceInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun BatteryCard(batteryInfo: BatteryInfo) {
    val level = batteryInfo.getBatteryLevel()
    val charging = batteryInfo.isCharging()
    val iconColor = if (level < 20) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column {
            Text(
                text = "BATERAI",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (charging) Icons.Default.BatteryChargingFull else Icons.Default.Battery5Bar,
                        contentDescription = "Battery Icon",
                        tint = iconColor,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "$level%",
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            text = if (charging) "Sedang mengisi daya" else "Tidak mengisi daya",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}
