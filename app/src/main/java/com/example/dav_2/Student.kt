package com.example.dav_2
class Student(
    nameInput: String?,
    emailInput: String?,
    dateInput: String?,
    optionInput: String?,
    isAgreedInput: Boolean?
) {
    val name: String = nameInput ?: ""
    val email: String = emailInput ?: ""
    val date: String = dateInput ?: ""
    val selectedOption: String = optionInput ?: ""
    val isAgreed: Boolean = isAgreedInput ?: false
}