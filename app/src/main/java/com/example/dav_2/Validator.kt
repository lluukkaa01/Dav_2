package com.example.dav_2

class Validator {
    fun validateForm(model: Student): Boolean {
        if (model.name.trim().isEmpty() ||
            model.email.trim().isEmpty() ||
            model.date.trim().isEmpty()) {
            return false
        }

        if (model.selectedOption.isEmpty()) {
            return false
        }

        if (!model.isAgreed) {
            return false
        }
        return true
    }
}