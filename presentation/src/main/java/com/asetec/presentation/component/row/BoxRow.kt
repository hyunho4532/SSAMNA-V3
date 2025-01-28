package com.asetec.presentation.component.row

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asetec.domain.model.state.Activate
import com.asetec.domain.model.state.Running
import com.asetec.presentation.R
import com.asetec.presentation.viewmodel.ActivityLocationViewModel
import com.asetec.presentation.viewmodel.SensorManagerViewModel

@SuppressLint("DiscouragedApi")
@Composable
fun BoxRow(
    context: Context,
    data: List<Any>,
    sensorManagerViewModel: SensorManagerViewModel = hiltViewModel(),
    userLocationViewModel: ActivityLocationViewModel = hiltViewModel()
) {

    var selectedIndex by remember {
        mutableStateOf<String?>("1")
    }

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.Start
    ) {
        data.forEach { items ->
            if (items is Activate) {
                Text(
                    text = items.activateName
                )
            }
            else if (items is Running) {
                val imageName = items.assets.replace("R.drawable.", "")
                val imageResId = context.resources.getIdentifier(imageName, "drawable", context.packageName)

                Box(
                    modifier = Modifier
                        .width(72.dp)
                        .height(70.dp)
                        .clickable(
                            interactionSource = remember {
                                MutableInteractionSource()
                            },
                            indication = rememberRipple(
                                color = Color.Gray,
                                bounded = true
                            )
                        ) {
                            selectedIndex = items.index
                            userLocationViewModel.statusClick(
                                name = items.status,
                                resId = imageResId
                            )
                        }
                ) {

                    if (selectedIndex == items.index) {
                        Image(
                            modifier = Modifier
                                .size(32.dp)
                                .align(Alignment.TopStart),
                            painter = painterResource(id = R.drawable.emoji_check),
                            contentDescription = "체크 아이콘"
                        )
                    }

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomCenter),
                    ) {
                        Text(
                            text = items.status
                        )

                        Image(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .size(24.dp),
                            painter = painterResource(id = imageResId),
                            contentDescription = "이모티콘 종류 아이콘"
                        )
                    }
                }
            }
        }
    }
}