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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.sunsational.WeatherViewModel
import com.example.sunsational.api.NetworkResponse
import com.example.sunsational.assets.EmptyStateCard
import com.example.sunsational.assets.ErrorCard
import com.example.sunsational.assets.LoadingIndicator
import com.example.sunsational.assets.WeatherInfoCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherPage(
    viewModel: WeatherViewModel
) {
    var city by remember { mutableStateOf("") }
    val weatherResult = viewModel.weatherResult.observeAsState()

    // Fairy-type gradient colors (deep purple to pink)
    val gradientColors = listOf(
        Color(0xFF5E35B1), // Deep Purple
        Color(0xFF9C27B0), // Purple
        Color(0xFFFF66B2)  // Pink
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
            // App title with minimalist style
            Text(
                text = "SunSational",
                color = Color.White,
                fontFamily = FontFamily.Default,
                fontSize = 28.sp,
                fontWeight = FontWeight.Light,
                letterSpacing = 4.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Search card with minimalist design
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding(bottom=24.dp)
                    .shadow(
                        elevation=4.dp,
                        spotColor=Color(0x40FFFFFF),
                        shape=RoundedCornerShape(20.dp)
                    ),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0x25FFFFFF)
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
                        tint = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.padding(end = 8.dp)
                    )

                    TextField(
                        value = city,
                        onValueChange = { city = it },
                        placeholder = { Text("Enter city", color = Color.White.copy(alpha = 0.5f)) },
                        singleLine = true,
                        textStyle = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        modifier = Modifier
                            .weight(1f)
                    )

                    FloatingActionButton(
                        onClick = { viewModel.getData(city) },
                        shape = CircleShape,
                        containerColor = Color(0xFFE1BEE7),
                        contentColor = Color(0xFF4A148C),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search",
                            modifier = Modifier.size(18.dp)
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
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(0.85f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Temperature display with minimalist style
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom=20.dp),
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0x30FFFFFF)
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text(
                                    text = "now",
                                    color = Color(0xFFE1BEE7),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Light,
                                    letterSpacing = 2.sp
                                )

                                Row {
                                    Spacer(modifier=Modifier.height(16.dp))

                                    Text(
                                        text="${result.data.current.temp_c}",
                                        color=Color.White,
                                        fontSize=72.sp,
                                        fontWeight=FontWeight.Thin,
                                        letterSpacing=(-2).sp
                                    )
                                    Text(
                                        text="°C",
                                        color=Color.White,
                                        fontSize = 28.sp,
                                        fontWeight=FontWeight.Thin,
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Row {
                                    Text(
                                        text = "Feels Like",
                                        color = Color(0xFFE1BEE7),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Light,
                                        letterSpacing = 2.sp
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text(
                                        text = "${result.data.current.feelslike_c} °C",
                                        color = Color.White.copy(alpha = 0.8f),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Normal
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Row {
                                    Text(
                                        text = "Precipitation",
                                        color = Color(0xFFE1BEE7),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Light,
                                        letterSpacing = 2.sp
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text(
                                        text = "${result.data.current.precip_mm} mm",
                                        color = Color.White.copy(alpha = 0.8f),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Normal
                                    )
                                }


                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    AsyncImage(
                                        model = "https:${result.data.current.condition.icon}".replace("64x64", "128x128"),
                                        contentDescription = "Weather condition",
                                        modifier = Modifier.size(80.dp)
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text(
                                        text = "${result.data.current.condition.text}",
                                        color = Color.White.copy(alpha = 0.8f),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Normal
                                    )
                                }

                            }
                        }

                        // Additional weather info in smaller cards
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Humidity Card
                            WeatherInfoCard(
                                title = "Humidity",
                                value = "${result.data.current.humidity}%",
                                modifier = Modifier.weight(1f)
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            // Wind Card
                            WeatherInfoCard(
                                title = "Wind",
                                value = "${result.data.current.wind_kph} km/h",
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Humidity Card
                            WeatherInfoCard(
                                title = "Wind Direction",
                                value = "${result.data.current.wind_dir}",
                                modifier = Modifier.weight(1f)
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            // Wind Card
                            WeatherInfoCard(
                                title = "Air Pressure",
                                value = "${result.data.current.pressure_mb} mb",
                                modifier = Modifier.weight(1f)
                            )
                        }

                    }
                }
                null -> {
                    EmptyStateCard()
                }
            }
        }
    }
}










@Preview(showBackground = true)
@Composable
fun WeatherPagePreview() {
    WeatherPage(WeatherViewModel())
}