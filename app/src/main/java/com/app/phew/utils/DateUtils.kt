package com.app.phew.utils

import android.annotation.SuppressLint
import android.text.format.DateFormat
import android.util.Log

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar


object DateUtils {

    fun getDate(time: Long): String {
        val cal = Calendar.getInstance()
        cal.timeInMillis = time
        Log.e("Time: ", System.currentTimeMillis().toString() + "")
        return DateFormat.format("yyyy-MM-dd HH:mm:ss", cal).toString()
    }

    fun getSecond(date: Date): Int {
        val cal = GregorianCalendar()
        cal.time = date
        return cal.get(Calendar.SECOND)
    }

    fun getMinute(date: Date): Int {
        val cal = GregorianCalendar()
        cal.time = date
        return cal.get(Calendar.MINUTE)
    }

    fun getHour(date: Date): Int {
        val cal = GregorianCalendar()
        cal.time = date
        return cal.get(Calendar.HOUR_OF_DAY)
    }

    fun getDay(date: Date): Int {
        val cal = GregorianCalendar()
        cal.time = date
        return cal.get(Calendar.DAY_OF_MONTH)
    }

    fun getMonth(date: Date): Int {
        val cal = GregorianCalendar()
        cal.time = date
        return cal.get(Calendar.MONTH)
    }

    fun getYear(date: Date): Int {
        val cal = GregorianCalendar()
        cal.time = date
        return cal.get(Calendar.YEAR)
    }

    @JvmOverloads
    fun newTimestamp(year: Int, month: Int, day: Int, hour: Int = 0, minute: Int = 0, second: Int = 0): Long {
        return GregorianCalendar(year, month, day, hour, minute, second).timeInMillis
    }

    fun newDate(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int): Date {
        return Date(
            newTimestamp(
                year,
                month,
                day,
                hour,
                minute,
                second
            )
        )
    }

    fun newDate(year: Int, month: Int, day: Int): Date {
        return Date(newTimestamp(year, month, day))
    }

    fun setClock(date: Date, hour: Int, minute: Int, second: Int): Date {
        val cal = GregorianCalendar()
        cal.time = date
        cal.set(Calendar.MILLISECOND, 0)
        if (hour > -1) {
            cal.set(Calendar.HOUR, hour)
        }
        if (minute > -1) {
            cal.set(Calendar.MINUTE, minute)
        }
        if (second > -1) {
            cal.set(Calendar.SECOND, second)
        }
        return cal.time
    }

    fun setDate(date: Date, month: Int, day: Int): Date {
        val cal = GregorianCalendar()
        cal.time = date
        if (month > -1) {
            cal.set(Calendar.MONTH, month)
        }
        if (day > -1) {
            cal.set(Calendar.DAY_OF_MONTH, day)
        }
        return cal.time
    }

    fun setDay(date: Date, day: Int): Date {
        return setDate(date, -1, day)
    }

    fun setWeekDay(date: Date, weekDay: Int): Date {
        val cal = GregorianCalendar()
        cal.time = date
        cal.set(Calendar.DAY_OF_WEEK, weekDay)
        return cal.time
    }

    fun setMonth(date: Date, month: Int): Date {
        return setDate(date, month, -1)
    }

    fun rollDays(date: Date, days: Int): Date {
        val cal = GregorianCalendar()
        cal.time = date
        cal.roll(Calendar.DAY_OF_MONTH, days)
        return cal.time
    }

    fun rollMonths(date: Date, months: Int): Date {
        val cal = GregorianCalendar()
        cal.time = date
        cal.roll(Calendar.MONTH, months)
        return cal.time
    }

    fun rollYears(date: Date, years: Int): Date {
        val cal = GregorianCalendar()
        cal.time = date
        cal.roll(Calendar.YEAR, years)
        return cal.time
    }

    fun rollHours(date: Date, hours: Int): Date {
        val cal = GregorianCalendar()
        cal.time = date
        cal.roll(Calendar.HOUR_OF_DAY, hours)
        return cal.time
    }

    fun rollMinutes(date: Date, minutes: Int): Date {
        val cal = GregorianCalendar()
        cal.time = date
        cal.roll(Calendar.MINUTE, minutes)
        return cal.time
    }

    fun rollSeconds(date: Date, seconds: Int): Date {
        val cal = GregorianCalendar()
        cal.time = date
        cal.roll(Calendar.SECOND, seconds)
        return cal.time
    }

    fun now(): Date {
        return Date()
    }

    @JvmOverloads
    fun today(hour: Int = 0, minute: Int = 0, second: Int = 0): Date {
        return setClock(Date(), hour, minute, second)
    }

    fun yesterday(): Date {
        val cal = GregorianCalendar()
        cal.time = today(0, 0, 0)
        cal.roll(Calendar.DAY_OF_MONTH, -1)
        return cal.time
    }

    fun yesterday(hour: Int, minute: Int, second: Int): Date {
        return setClock(
            yesterday(),
            hour,
            minute,
            second
        )
    }

    fun tomorrow(): Date {
        val cal = GregorianCalendar()
        cal.roll(Calendar.DAY_OF_MONTH, 1)
        return cal.time
    }

    fun tomorrow(hour: Int, minute: Int, second: Int): Date {
        return setClock(
            tomorrow(),
            hour,
            minute,
            second
        )
    }


    @SuppressLint("SimpleDateFormat")
    fun getTimeStamp(dateStr: Long?): String {
        val calendar = Calendar.getInstance()
        var today = calendar.get(Calendar.DAY_OF_MONTH).toString()

        CommonUtil.PrintLogE(
            java.lang.Long.toString(
                dateStr!!
            )
        )
        var format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var timestamp = ""

        today = if (today.length < 2) "0$today" else today

        try {
            val date = format.parse(getDate(dateStr))
            val todayFormat = SimpleDateFormat("dd")
            val dateToday = todayFormat.format(date!!)
            format = if (dateToday == today)
                SimpleDateFormat("hh:mm a")
            else
                SimpleDateFormat("dd LLL, hh:mm a")
            val date1 = format.format(date)
            timestamp = date1.toString()
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return timestamp
    }

    @Throws(ParseException::class)
    fun getMilliSecondFromString(Time: String): String {
        CommonUtil.PrintLogE(Time)
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        var date: Date
        date = formatter.parse(Time)
        val timestamp = date.time
        CommonUtil.PrintLogE("Time : " + timestamp / 1000)
        return (timestamp / 1000).toString() + ""
    }

}// private
