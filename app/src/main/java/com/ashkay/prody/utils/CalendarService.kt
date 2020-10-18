package com.ashkay.prody.utils

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.CalendarContract
import java.util.*

class CalendarService {

    companion object {

        fun readCalendar(context: Context) {
            val contentResolver: ContentResolver = context.contentResolver

            val projection: Array<String> = arrayOf(
                CalendarContract.Calendars._ID,
                CalendarContract.Calendars.ACCOUNT_NAME,
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                CalendarContract.Calendars.OWNER_ACCOUNT
            )
            val uri: Uri = CalendarContract.Calendars.CONTENT_URI

            val cur: Cursor? =
                contentResolver.query(uri, projection, null, null, null)

            cur?.moveToFirst()
            while (cur != null && cur.moveToNext()) {
                val calID: Long = cur.getLong(0)
                val displayName: String = cur.getString(1)
                val accountName: String = cur.getString(2)
                val ownerName: String = cur.getString(3)

                println("# $calID - $displayName - $accountName - $ownerName")
                readCalendarEvents(context, calID)
            }
            cur?.close()
        }

        fun readCalendarEvents(context: Context, calId: Long) {
            val contentResolver: ContentResolver = context.contentResolver

            val projection: Array<String> = arrayOf(
                CalendarContract.Events.CALENDAR_ID,
                CalendarContract.Events.TITLE,
                CalendarContract.Events.DESCRIPTION,
                CalendarContract.Events.DTSTART,
                CalendarContract.Events.DTEND
            )
            val uri: Uri = CalendarContract.Events.CONTENT_URI

            val calStart: Calendar = Calendar.getInstance()
            calStart.time = Date()
            calStart[Calendar.HOUR_OF_DAY] = 0
            calStart[Calendar.MINUTE] = 0
            calStart[Calendar.SECOND] = 0
            calStart[Calendar.MILLISECOND] = 0
            val midnightYesterday = calStart.timeInMillis

            val calEnd: Calendar = Calendar.getInstance()
            calEnd.time = Date()
            calEnd[Calendar.DAY_OF_YEAR] = calEnd[Calendar.DAY_OF_YEAR] + 1
            calEnd[Calendar.HOUR_OF_DAY] = 0
            calEnd[Calendar.MINUTE] = 0
            calEnd[Calendar.SECOND] = 0
            calEnd[Calendar.MILLISECOND] = 0
            val midnightTonight = calEnd.timeInMillis

            val selection =
                "${CalendarContract.Events.DTSTART} >= ? AND ${CalendarContract.Events.DTSTART} <= ?"

            val selectionArgs =
                arrayOf(midnightYesterday.toString(), midnightTonight.toString())

            val sortOrder = "${CalendarContract.Events.DTSTART} ASC"

            val cur: Cursor? =
                contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)

            cur?.moveToFirst()
            while (cur != null && cur.moveToNext()) {
                val calID: Long = cur.getLong(0)
                val title: String? = cur.getString(1)
                val description: String? = cur.getString(2)
                val dtStart: String = cur.getString(3)
                val dtEnd: String? = cur.getString(4)

                println("> $calID - $title - $description - $dtStart - $dtEnd")
            }

            println("$midnightYesterday, $midnightTonight, ${cur?.count}")
            cur?.close()
        }
    }
}