package com.example.mediapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertMed(user: MedicineModel): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBSchema.UserEntity.COLUMN_USER_ID, user.userid)
        values.put(DBSchema.UserEntity.COLUMN_NAME, user.name)

        // Insert the new row, returning the primary key value of the new row
        val success = db.insert(DBSchema.UserEntity.TABLE_NAME, null, values)

        db.close()
        return (Integer.parseInt("$success")!=-1)
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteMed(userid: String): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBSchema.UserEntity.COLUMN_USER_ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(userid)
        // Issue SQL statement.
        val success = db.delete(DBSchema.UserEntity.TABLE_NAME, selection, selectionArgs)

        db.close()
        return (Integer.parseInt("$success")!= 0)
    }

    fun readMed(userid: String): ArrayList<MedicineModel> {
        val users = ArrayList<MedicineModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBSchema.UserEntity.TABLE_NAME + " WHERE " + DBSchema.UserEntity.COLUMN_USER_ID + "='" + userid + "'", null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            db.close()
            return ArrayList()
        }

        var name: String
        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                name = cursor.getString(cursor.getColumnIndex(DBSchema.UserEntity.COLUMN_NAME))

                users.add(MedicineModel(userid, name))
                cursor.moveToNext()
            }
        }
        db.close()
        return users
    }

    fun readAllMeds(): ArrayList<MedicineModel> {
        val users = ArrayList<MedicineModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBSchema.UserEntity.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var userid: String
        var name: String
        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                userid = cursor.getString(cursor.getColumnIndex(DBSchema.UserEntity.COLUMN_USER_ID))
                name = cursor.getString(cursor.getColumnIndex(DBSchema.UserEntity.COLUMN_NAME))

                users.add(MedicineModel(userid, name))
                cursor.moveToNext()
            }
        }
        db.close()
        return users
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "FeedReader.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBSchema.UserEntity.TABLE_NAME + " (" +
                    DBSchema.UserEntity.COLUMN_USER_ID + " TEXT PRIMARY KEY," +
                    DBSchema.UserEntity.COLUMN_NAME + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBSchema.UserEntity.TABLE_NAME
    }

}