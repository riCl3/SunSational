import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.sunsational.WeatherViewModel
import com.example.sunsational.api.NetworkResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherPage(
    viewModel: WeatherViewModel
) {
    var city by remember { mutableStateOf("") }
    val weatherResult = viewModel.weatherResult.observeAsState()

    // Create gradient background colors
    val gradientColors = listOf(
        Color(0xFF2E8BC0),
        Color(0xFF145DA0)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush=Brush.verticalGradient(
                    colors=gradientColors
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top=48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // App title
            Text(
                text = "SunSational",
                color = Color.White,
                fontFamily = FontFamily.Cursive,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Search card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal=24.dp)
                    .shadow(8.dp, RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.95f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal=16.dp, vertical=8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = Color(0xFF2E8BC0),
                        modifier = Modifier.padding(end = 8.dp)
                    )

                    TextField(
                        value = city,
                        onValueChange = { city = it },
                        placeholder = { Text("Enter city", color = Color.Gray) },
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .weight(1f)
                    )

                    FloatingActionButton(
                        onClick = { viewModel.getData(city) },
                        shape = CircleShape,
                        containerColor = Color(0xFF2E8BC0),
                        contentColor = Color.White,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Weather results
            when (val result = weatherResult.value) {
                is NetworkResponse.Error -> {
                    ErrorCard(message = result.message)
                }
                NetworkResponse.Loading -> {
                    LoadingIndicator()
                }
                is NetworkResponse.Success -> {
                    // Using the same format as in the original code
                    Column(modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                        Text(
                            text="Temperature",
                            modifier=Modifier.padding(16.dp),
                            color=Color.White,
                            fontSize=40.sp,
                            fontWeight=FontWeight.Thin
                        )

                        Spacer(modifier=Modifier.height(8.dp))

                        Text(
                            text="${result.data.current.temp_c}°C",
                            modifier=Modifier.padding(16.dp),
                            color=Color.White,
                            fontSize=80.sp,
                            fontWeight=FontWeight.ExtraBold
                        )
                    }
                }
                null -> {
                    EmptyStateCard()
                }
            }
        }
    }
}

@Composable
fun ErrorCard(message: String) {
    Card(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFDEFEF)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Oops!",
                color = Color(0xFFE57373),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                color = Color(0xFF757575)
            )
        }
    }
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier
            .padding(48.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        // Import required at the top of your file:
        // import com.airbnb.lottie.compose.*

        val composition by rememberLottieComposition(
            LottieCompositionSpec.Url("https://lottie.host/191b0ff1-bb7e-41e6-ad09-48ff698fea1e/3oz0mygDEP.lottie")
        )
        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever
        )

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(120.dp)
        )
    }
}

@Composable
fun EmptyStateCard() {
    Card(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.8f)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Enter a city to get weather",
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherPagePreview() {
    WeatherPage(WeatherViewModel())
}