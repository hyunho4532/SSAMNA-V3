package com.asetec.presentation.ui.feature.detail

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.asetec.domain.model.dto.CrewDTO
import dagger.hilt.android.qualifiers.ApplicationContext

@Composable
fun CrewDetailScreen(
    crewList: List<CrewDTO>,
    navController: NavController = rememberNavController(),
    context: Context
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        crewList.forEach {
            val imageName = it.picture.replace("R.drawable.", "")
            val imageResId = context.resources.getIdentifier(imageName, "drawable", context.packageName)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth(),
                    painter = painterResource(id = imageResId),
                    contentDescription = "크루 상세 이미지",
                    contentScale = ContentScale.Crop
                )
            }

            Box(
                modifier = Modifier
                    .padding(top = 12.dp, start = 6.dp)
            ) {
                Text(
                    text = it.title,
                    fontSize = 18.sp
                )
            }
        }
    }
}