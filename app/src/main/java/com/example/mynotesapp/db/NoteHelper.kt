package com.example.mynotesapp.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.mynotesapp.db.DatabaseContract.NoteColumns.Companion.TABLE_NAME
import com.example.mynotesapp.db.DatabaseContract.NoteColumns.Companion._ID
import java.sql.SQLException

class NoteHelper(context: Context) {

    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private val INSTANCE: NoteHelper? = null

        // metode menginisiasi database
        fun getInstance(context: Context): NoteHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: NoteHelper(context)
            }

    }

    // constructor
    init {
        dataBaseHelper = DatabaseHelper(context)
    }

    @Throws(SQLException::class)
    // metode membuka koneksi
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    // metode menutup koneksi
    fun close() {
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()

    }

    // mengambil data
    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC"
        )
    }

    // mengambil data dengan id tertentu
    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }

    //metode menyimpan data
    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    //metode memperbaharu data
    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    //metode untuk menghapus data
    fun deleteById(id: String): Int{
        return database.delete(DATABASE_TABLE, "$_ID = '$id", null)
    }

}