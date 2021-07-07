package com.riuta.horalife.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.riuta.horalife.R
import java.time.LocalDate
import java.time.Period
import java.util.*

class BirthDayPicker: DialogFragment(), DatePickerDialog.OnDateSetListener{

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val dialog = DatePickerDialog(this.requireContext(),
            R.style.DatePickerTheme,
            this, year, month, day)
        dialog.setMessage("生年月日を選択")
        return dialog
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        Log.d("debug", year.toString() + (month + 1).toString())
        val choose = LocalDate.of(year, month, dayOfMonth)

         fun calcAge(birthday: LocalDate): Int {
            val today = LocalDate.now()
            return Period.between(LocalDate.parse(birthday.toString()), today).getYears()
        }
        Log.d("age", calcAge(choose).toString())

    }

}