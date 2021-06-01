package com.animsh.dobcalculator

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        btnDatePicker.setOnClickListener { view ->
            clickDataPicker(view)
        }
    }

    private fun clickDataPicker(view: View) {
        val c = Calendar.getInstance()
        val year =
            c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            this,
            { view, year, monthOfYear, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"

                tvSelectedDate.text = selectedDate
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDate)
                val selectedDateToMinutes = theDate!!.time / 60000
                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                val currentDateToMinutes = currentDate!!.time / 60000

                val differenceInMinutes = currentDateToMinutes - selectedDateToMinutes
                tvSelectedDateInMinutes.text = differenceInMinutes.toString()

                val differenceInHours = currentDateToMinutes / 60
                tvSelectedDateInHours.text = differenceInHours.toInt().toString()

                val differenceInDays = differenceInMinutes / 1440
                tvSelectedDateInDays.text = differenceInDays.toInt().toString()

                val differenceInMonths = differenceInMinutes / 43800
                tvSelectedDateInMonths.text = differenceInMonths.toInt().toString()

                val differenceInYears = differenceInMinutes / 525600
                tvSelectedDateInYears.text = differenceInYears.toInt().toString()
            },
            year,
            month,
            day
        )
        dpd.datePicker.maxDate = Date().time - 86400000
        dpd.show()
    }
}