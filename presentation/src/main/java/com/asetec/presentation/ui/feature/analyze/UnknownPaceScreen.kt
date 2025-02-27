package com.asetec.presentation.ui.feature.analyze

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asetec.presentation.component.tool.Spacer
import com.asetec.presentation.component.util.analyzeRunningFeedback
import com.asetec.presentation.component.util.responsive.setUpWidth
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.double

@Composable
fun UnknownPaceScreen() {
    Column {
        Text(
            text = "이번 활동의 페이스 분석 - 꿀팁",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Spacer(
            width = setUpWidth(),
            height = 10.dp,
            isBottomBorder = true
        )

        Box(
            modifier = Modifier
                .padding(top = 12.dp)
        ) {
            Column {
                Text(
                    text = "1. 러닝 페이스 높이는 방법",
                    fontSize = 16.sp
                )

                Text(
                    modifier = Modifier
                        .padding(top = 12.dp),
                    text = buildAnnotatedString {
                        append("평소에 운동을 안하시면 무리하게 하지 마세요!\n")
                        withStyle(SpanStyle(fontSize = 8.sp)) {
                            append("\n")
                        }
                        append("꾸준한 훈련과 운동을 진행해주세요!\n")
                        withStyle(SpanStyle(fontSize = 8.sp)) {
                            append("\n")
                        }
                        append("걷기와 달리기를 번갈아 하는 인터벌 러닝을 하면 심폐 기능이 향상됩니다!\n")
                        withStyle(SpanStyle(fontSize = 8.sp)) {
                            append("\n")
                        }
                        append("항상 같은 페이스로 움직이면 활동이 급격히 정체되고 그만두게 됩니다\n")
                        withStyle(SpanStyle(fontSize = 8.sp)) {
                            append("\n")
                        }
                        append("달리기나 운동 시작하기 전 1시간 이내에 완숙 달걀과 과일 등 단백질과 탄수화물을 조합하여 섭취해보세요!")
                    },
                    fontSize = 12.sp
                )

                Text(
                    modifier = Modifier
                        .padding(top = 24.dp),
                    text = "2. 호흡 방법",
                    fontSize = 16.sp
                )
            }
        }
    }
}