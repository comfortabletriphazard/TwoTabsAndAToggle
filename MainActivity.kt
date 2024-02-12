package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TabsScreen()
                }
            }
        }
    }
}

@Composable
fun TabContent1(onCalculate: (String, Int, Double) -> Unit) {
    var name by remember { mutableStateOf("") }
    var unitAmount by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var multiplyPriceBy100 by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = unitAmount,
            onValueChange = { unitAmount = it },
            label = { Text("Unit Amount") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { /* Handle next action */ })
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                val finalPrice = if (multiplyPriceBy100) price.toDoubleOrNull()?.times(100) ?: 0.0 else price.toDoubleOrNull() ?: 0.0
                onCalculate(name, unitAmount.toIntOrNull() ?: 0, finalPrice)
                name = ""
                unitAmount = ""
                price = ""
            })
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Multiply Price by 100")
            Spacer(modifier = Modifier.width(8.dp))
            Switch(checked = multiplyPriceBy100, onCheckedChange = { multiplyPriceBy100 = it })
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val finalPrice = if (multiplyPriceBy100) price.toDoubleOrNull()?.times(100) ?: 0.0 else price.toDoubleOrNull() ?: 0.0
            onCalculate(name, unitAmount.toIntOrNull() ?: 0, finalPrice)
            name = ""
            unitAmount = ""
            price = ""
        }) {
            Text("Calculate")
        }
    }
}

@Composable
fun TabContent2(results: List<String>) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("Results:", color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        results.forEach { result ->
            Text(result)
        }
    }
}

@Composable
fun TabsScreen() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val results = remember { mutableStateListOf<String>() }

    Column {
        TabRow(selectedTabIndex = selectedTabIndex) {
            Tab(selected = selectedTabIndex == 0, onClick = { selectedTabIndex = 0 }) {
                Text("Tab 1")
            }
            Tab(selected = selectedTabIndex == 1, onClick = { selectedTabIndex = 1 }) {
                Text("Tab 2")
            }
        }
        when (selectedTabIndex) {
            0 -> TabContent1 { name, unitAmount, price ->
                results.add("$name - $unitAmount - $price")
            }
            1 -> TabContent2(results)
        }
    }
}
