package com.example.dav_2

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

val DarkBackground = Color(0xFF1A1A24)
val CardSurface = Color(0xFF252538)
val AccentTeal = Color(0xFF00E5FF)
val TextPrimary = Color(0xFFFFFFFF)
val TextSecondary = Color(0xFF9D9DBC)

@Composable
fun StudentForm() {
    val context = LocalContext.current
    val validator = Validator()
    
    var nameState by remember { mutableStateOf("") }
    var emailState by remember { mutableStateOf("") }
    var dateState by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf("") }
    var isAgreed by remember { mutableStateOf(false) }
    
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val formattedDay = String.format("%02d", dayOfMonth)
            val formattedMonth = String.format("%02d", month + 1)
            dateState = "$formattedDay/$formattedMonth/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .background(CardSurface, shape = RoundedCornerShape(24.dp))
                .border(2.dp, AccentTeal.copy(alpha = 0.3f), shape = RoundedCornerShape(24.dp))
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Student Form",
                    style = TextStyle(
                        color = AccentTeal,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            
            CustomInputField(
                label = "სახელი",
                value = nameState,
                onValueChange = { nameState = it },
                placeholder = "თქვენი სახელი"
            )

            CustomInputField(
                label = "ელექტრონული მისამართი",
                value = emailState,
                onValueChange = { emailState = it },
                placeholder = "gmail@example.com"
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "თარიღი",
                    style = TextStyle(color = TextSecondary, fontSize = 14.sp, fontWeight = FontWeight.Medium),
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(DarkBackground, shape = RoundedCornerShape(12.dp))
                        .border(1.dp, TextSecondary.copy(alpha = 0.5f), shape = RoundedCornerShape(12.dp))
                        .clickable { datePickerDialog.show() }
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = if (dateState.isEmpty()) "აირჩიეთ თარიღი" else dateState,
                        style = TextStyle(
                            color = if (dateState.isEmpty()) TextSecondary.copy(alpha = 0.6f) else AccentTeal,
                            fontSize = 16.sp
                        )
                    )
                }
            }


            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "თქვენი ფავორიტი მიმართულება:",
                    style = TextStyle(color = TextSecondary, fontSize = 14.sp, fontWeight = FontWeight.Medium),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                val platformOptions = listOf("Windows", "Linux", "MacOS")
                platformOptions.forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { selectedOption = option },
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .border(2.dp, if (selectedOption == option) AccentTeal else TextSecondary, RoundedCornerShape(4.dp))
                                .background(if (selectedOption == option) AccentTeal.copy(alpha = 0.2f) else Color.Transparent)
                                .padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            if (selectedOption == option) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(AccentTeal, RoundedCornerShape(2.dp))
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(text = option, style = TextStyle(color = TextPrimary, fontSize = 16.sp))
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "ვეთანხმები წესებს და პირობებს",
                    style = TextStyle(color = TextPrimary, fontSize = 14.sp),
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = isAgreed,
                    onCheckedChange = { isAgreed = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = AccentTeal,
                        checkedTrackColor = AccentTeal.copy(alpha = 0.4f),
                        uncheckedThumbColor = TextSecondary,
                        uncheckedTrackColor = DarkBackground
                    )
                )
            }

            Button(
                onClick = {
                    val currentForm = Student(
                        nameInput = nameState,
                        emailInput = emailState,
                        dateInput = dateState,
                        optionInput = selectedOption,
                        isAgreedInput = isAgreed
                    )

                    val isFormValid = validator.validateForm(currentForm)

                    if (isFormValid) {
                        Toast.makeText(context, "მონაცემები გაიგზავნა!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "შეავსეთ ყველა ველი!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentTeal,
                    contentColor = DarkBackground
                )
            ) {
                Text(
                    text = "Submit",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

@Composable
fun CustomInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = TextStyle(color = TextSecondary, fontSize = 14.sp, fontWeight = FontWeight.Medium),
            modifier = Modifier.padding(bottom = 6.dp)
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = placeholder, color = TextSecondary.copy(alpha = 0.5f)) },
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, TextSecondary.copy(alpha = 0.5f), shape = RoundedCornerShape(12.dp)),
            textStyle = TextStyle(color = TextPrimary, fontSize = 16.sp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = DarkBackground,
                unfocusedContainerColor = DarkBackground,
                disabledContainerColor = DarkBackground,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = AccentTeal
            ),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )
    }
}