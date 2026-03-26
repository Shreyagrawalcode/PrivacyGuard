package com.example.myapplication
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import android.util.Log
@Composable
fun AppScannerScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val apps = remember {
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        context.packageManager
            .queryIntentActivities(intent, 0)
            .map { it.loadLabel(context.packageManager).toString() }
            .sorted()
    }
    LazyColumn(modifier = modifier) {
        items(apps) { appName ->
            Text(
                text = appName,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable{
                        Log.d("PG","TAPPED: $appName" )}
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            )
        }
    }
}