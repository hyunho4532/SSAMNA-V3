package com.asetec.presentation.component.row.tab.crew

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asetec.domain.model.dto.ActivateNotificationDTO
import com.asetec.presentation.component.util.responsive.setUpWidth
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Composable
fun <T> Notification(
    crewId: Int,
    notificationList: List<T>
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        notificationList.forEach { notification ->
            if (notification is ActivateNotificationDTO) {
                val idxArray = notification.crewId["idx"]?.jsonArray

                val matchingIdx = idxArray!!.firstOrNull {
                    it.jsonObject["id"]?.jsonPrimitive?.int == crewId
                }

                if (matchingIdx != null) {
                    Box(
                        modifier = Modifier
                            .padding(top = 12.dp, start = 6.dp)
                    ) {
                        Text(
                            text = notification.feed.toString(),
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}