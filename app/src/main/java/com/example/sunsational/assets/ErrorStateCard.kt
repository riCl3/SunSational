package com.example.sunsational.assets

import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.sunsational.R

@Composable
fun ErrorCard(message: String) {
    Card(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth(0.85f),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0x40FFD8EC) // Lighter pink with a little more saturation
        )
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(), // Ensures the column takes full width
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Lottie Animation
            val composition by rememberLottieComposition(
                LottieCompositionSpec.Url("https://lottie.host/31a5707f-8e7d-490f-8c91-346a286ee38e/dsaX1bAmtR.json")
            )
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.size(240.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Oops!",
                color = Color.White,
                fontWeight = FontWeight.ExtraBold, // Made text bold to stand out
                fontSize = 30.sp  // Slightly larger for prominence
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = message,
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 20.sp,  // Increased font size for better readability
                textAlign = TextAlign.Center
            )
        }
    }
}
