import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sunsational.WeatherViewModel
import com.example.sunsational.api.NetworkResponse


@Composable
fun WeatherPage(
    viewModel: WeatherViewModel
) {
    var city by remember { mutableStateOf("") }
    val weatherResult = viewModel.weatherResult.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Search bar and button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = city,
                label = { Text("Enter city") },
                singleLine = true,
                onValueChange = {
                    city = it
                },
                modifier = Modifier.weight(1f) // To make the text field take most of the space
            )
            IconButton(onClick = {
                viewModel.getData(city)
            }) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
        }

        // Below the search bar, show loading or data or error
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val result = weatherResult.value) {
                is NetworkResponse.Error -> {
                    Text(text = result.message)
                }
                NetworkResponse.Loading -> {
                    CircularProgressIndicator()
                }
                is NetworkResponse.Success -> {
                    Text(text = "Temperature: ${result.data.current.temp_c}°C")
                }
                null -> {
                    // Handle case when result is null (initial state)
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
