package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    var currentScreen by remember { mutableStateOf("list") }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.List, contentDescription = "Danh sách số") },
                    label = { Text("Danh sách") },
                    selected = currentScreen == "list",
                    onClick = { currentScreen = "list" }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Kiểm tra tuổi") },
                    label = { Text("Tuổi") },
                    selected = currentScreen == "age",
                    onClick = { currentScreen = "age" }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Email, contentDescription = "Kiểm tra email") },
                    label = { Text("Email") },
                    selected = currentScreen == "email",
                    onClick = { currentScreen = "email" }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (currentScreen) {
                "list" -> NumberListScreen()
                "age" -> AgeCheckScreen()
                "email" -> EmailValidationScreen()
            }
        }
    }
}

@Composable
fun NumberListScreen() {
    var inputText by remember { mutableStateOf("") }
    var numberList by remember { mutableStateOf<List<Int>>(emptyList()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "THỰC HÀNH 02",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "UNIVERSITY OF TRANSPORT HCM CITY",
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Text(
            text = "Nhập vào số lượng",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = inputText,
            onValueChange = {
                inputText = it
                errorMessage = null
            },
            label = { Text("Nhập số") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )

        if (!errorMessage.isNullOrEmpty()) {
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Button(
            onClick = {
                if (inputText.isNotEmpty()) {
                    try {
                        val count = inputText.toInt()
                        if (count > 0) {
                            numberList = List(count) { it + 1 }
                            errorMessage = null
                        } else {
                            errorMessage = "Số phải lớn hơn 0"
                        }
                    } catch (e: NumberFormatException) {
                        errorMessage = "Dữ liệu bạn nhập không hợp lệ"
                    }
                } else {
                    errorMessage = "Vui lòng nhập số"
                }
            },
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Text("Tạo danh sách")
        }

        if (numberList.isNotEmpty()) {
            Text(
                text = "Danh sách số:",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                items(numberList) { number ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Text(
                            text = "Số $number",
                            modifier = Modifier.padding(16.dp),
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AgeCheckScreen() {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "BÀI TẬP VỀ NHÀ - TUẦN 02",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "KIỂM TRA ĐỘ TUỔI",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nhập tên") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )

        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Nhập tuổi") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )

        Button(
            onClick = {
                if (name.isNotEmpty() && age.isNotEmpty()) {
                    try {
                        val ageValue = age.toInt()
                        val category = when {
                            ageValue >= 65 -> "Người già"
                            ageValue >= 6 -> "Người lớn"
                            ageValue >= 2 -> "Trẻ em"
                            else -> "Em bé"
                        }
                        result = "Tên: $name\nTuổi: $ageValue\nPhân loại: $category"
                    } catch (e: NumberFormatException) {
                        result = "Tuổi phải là số hợp lệ"
                    }
                } else {
                    result = "Vui lòng nhập đầy đủ thông tin"
                }
            },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Kiểm tra")
        }

        if (result.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = result,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun EmailValidationScreen() {
    var email by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isValid by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "THỰC HÀNH 2",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "KIỂM TRA EMAIL",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                result = ""
                isValid = false
            },
            label = { Text("Nhập email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )

        Button(
            onClick = {
                when {
                    email.isBlank() -> {
                        result = "Email không hợp lệ"
                        isValid = false
                    }
                    !email.contains("@") -> {
                        result = "Email không đúng định dạng"
                        isValid = false
                    }
                    else -> {
                        result = "Bạn đã nhập email hợp lệ"
                        isValid = true
                    }
                }
            },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Kiểm tra email")
        }

        if (result.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isValid) MaterialTheme.colorScheme.primaryContainer
                    else MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(
                    text = result,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 16.sp,
                    color = if (isValid) MaterialTheme.colorScheme.onPrimaryContainer
                    else MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        AppNavigation()
    }
}

@Preview(showBackground = true)
@Composable
fun NumberListPreview() {
    MyApplicationTheme {
        NumberListScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun AgeCheckPreview() {
    MyApplicationTheme {
        AgeCheckScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun EmailValidationPreview() {
    MyApplicationTheme {
        EmailValidationScreen()
    }
}