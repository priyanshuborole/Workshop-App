package com.application.workshopapp.data.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.database.getStringOrNull
import com.application.workshopapp.data.model.Workshop

class WorkshopSqliteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "workshops.db"
        private const val DATABASE_VERSION = 3
        private const val TABLE_NAME = "workshops"
        private const val STUDENT_WORKSHOPS_TABLE_NAME = "student_workshops"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_IMAGE = "image"
        private const val COLUMN_DESC = "description"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_STUDENT_ID = "student_id"
        private const val COLUMN_WORKSHOP_ID = "workshop_id"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NAME TEXT, $COLUMN_IMAGE TEXT, $COLUMN_DESC TEXT, $COLUMN_DATE TEXT)"
        db?.execSQL(createTable)
        val createStudentWorkshopsTable =
            "CREATE TABLE $STUDENT_WORKSHOPS_TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_STUDENT_ID INTEGER, $COLUMN_WORKSHOP_ID INTEGER)"
        db?.execSQL(createStudentWorkshopsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db?.execSQL("DROP TABLE IF EXISTS $STUDENT_WORKSHOPS_TABLE_NAME")
        onCreate(db)
    }

     fun insertWorkshops(workshops: List<Workshop>) {
        val db = this.writableDatabase
        for (workshop in workshops) {
            val values = ContentValues()
            values.put(COLUMN_ID, workshop.workshop_id)
            values.put(COLUMN_NAME, workshop.workshop_name)
            values.put(COLUMN_IMAGE, workshop.workshop_image)
            values.put(COLUMN_DESC, workshop.workshop_desc)
            values.put(COLUMN_DATE, workshop.workshop_date)
            db.insert(TABLE_NAME, null, values)
        }
        db.close()
    }

    @SuppressLint("Range")
     fun getWorkshops(): ArrayList<Workshop> {
        val workshops = ArrayList<Workshop>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                val image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE))
                val desc = cursor.getString(cursor.getColumnIndex(COLUMN_DESC))
                val date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE))

                if(image != null && name != null && desc != null && date != null)
                    workshops.add(Workshop(id, name, image, desc, date))

            } while (cursor.moveToNext())
        }
//        if (cursor.moveToFirst()) {
//            do {
//                workshops.add(
//                    Workshop(
//                        cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
//                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
//                        cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE)),
//                        cursor.getString(cursor.getColumnIndex(COLUMN_DESC)),
//                        cursor.getString(cursor.getColumnIndex(COLUMN_DATE))
//                    )
//                )
//            } while (cursor.moveToNext())
//        }
        cursor.close()
        return workshops
    }

    fun applyToWorkshop(studentId: Int, workshopId: Int): Boolean {
        val db = this.writableDatabase
        val query =
            "SELECT * FROM $STUDENT_WORKSHOPS_TABLE_NAME WHERE $COLUMN_STUDENT_ID = $studentId AND $COLUMN_WORKSHOP_ID = $workshopId"
        val cursor = db.rawQuery(query, null)
        if (cursor.count > 0) {
            cursor.close()
            return false
        }
        val values = ContentValues()
        values.put(COLUMN_STUDENT_ID, studentId)
        values.put(COLUMN_WORKSHOP_ID, workshopId)
        db.insert(STUDENT_WORKSHOPS_TABLE_NAME, null, values)
        cursor.close()
        return true
    }

    @SuppressLint("Range")
    fun getAppliedWorkshops(studentId: Int): ArrayList<Workshop> {
        val workshops = ArrayList<Workshop>()
        val selectQuery = "SELECT w.* FROM $STUDENT_WORKSHOPS_TABLE_NAME sw JOIN $TABLE_NAME w ON sw.$COLUMN_WORKSHOP_ID = w.$COLUMN_ID WHERE sw.$COLUMN_STUDENT_ID = $studentId"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                workshops.add(
                    Workshop(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DESC)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DATE))
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return workshops
    }

    fun isWorkshopApplied(studentId: Int, workshopId: Int): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM student_workshops WHERE student_id = $studentId AND workshop_id = $workshopId"
        val cursor = db.rawQuery(query, null)
        val count = cursor.count
        cursor.close()
        return count > 0
    }
}