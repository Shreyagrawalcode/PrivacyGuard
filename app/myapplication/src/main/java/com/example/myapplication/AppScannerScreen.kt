package com.example.myapplication

import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppScannerScreen(
    modifier: Modifier = Modifier,
    onAppClick: (String, List<String>) -> Unit
) {
    val context = LocalContext.current

    val apps = remember {
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        context.packageManager
            .queryIntentActivities(intent, 0)
            .map {
                val label = it.loadLabel(context.packageManager).toString()
                val packageName = it.activityInfo.packageName
                val permissions = try {
                    context.packageManager
                        .getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
                        .requestedPermissions
                        ?.toList() ?: emptyList()
                } catch (e: Exception) {
                    emptyList()
                }
                Triple(label, packageName, permissions)
            }
            .sortedBy { it.first }
    }

    LazyColumn(modifier = modifier) {
        items(apps) { app ->
            val (label, packageName, permissions) = app
            val risk = getAppRiskLevel(permissions)

            val (riskLabel, riskColor) = when (risk) {
                RiskLevel.HIGH   -> "High Risk" to Color(0xFFE24B4A)
                RiskLevel.MEDIUM -> "Medium"    to Color(0xFFEF9F27)
                RiskLevel.LOW    -> "Low"       to Color(0xFF1D9E75)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAppClick(label, permissions) }
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = label, fontSize = 15.sp, modifier = Modifier.weight(1f))

                Text(
                    text = riskLabel,
                    color = riskColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .background(riskColor.copy(alpha = 0.15f), RoundedCornerShape(20.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
        }
    }
}