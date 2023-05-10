package com.example.firebasedemo

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText

class CustomTextWatcher(var editText: List<EditText>, var view: View) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable?) {
        var data = s.toString();
        when (view.id) {
            R.id.otp1 -> {
                if (data.length == 1) {
                    editText[1].requestFocus();
                }
            }
            R.id.otp2 -> {
                checkData(data, 2);
            }
            R.id.otp3 -> {
                checkData(data, 3);

            }
            R.id.otp4 -> {
                checkData(data, 4);

            }
            R.id.otp5 -> {
                checkData(data, 5);
            }
            R.id.otp6 -> {
                if (data.isEmpty()) {
                    editText[4].requestFocus();
                }
            }
            else -> {

            }
        }
    }

    private fun checkData(data: String, position: Int) {
        if (data.length == 1) {
            editText[position].requestFocus();
        } else if (data.isEmpty()) {
            editText[position - 2].requestFocus();
        }
    }
}