package com.example.opsc7213_goalignite.utilis

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.opsc7213_goalignite.model.ToDoModel


class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "toDoListDatabase"
        private const val TABLE_TODO = "todo"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TASK = "task"
        private const val COLUMN_STATUS = "status"
        private const val CREATE_TABLE_TODO = "CREATE TABLE $TABLE_TODO ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_TASK TEXT, $COLUMN_STATUS INTEGER)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_TODO)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TODO")
        onCreate(db)
    }

    fun insertTask(task: ToDoModel) {
        val cv = ContentValues().apply {
            put(COLUMN_TASK, task.task)
            put(COLUMN_STATUS, task.status)
        }
        val db = this.writableDatabase
        db.insert(TABLE_TODO, null, cv)
    }

    fun getAllTasks(): List<ToDoModel> {
        val taskList = mutableListOf<ToDoModel>()
        val db = this.readableDatabase
        val cursor: Cursor? = db.query(
            TABLE_TODO,
            arrayOf(COLUMN_ID, COLUMN_TASK, COLUMN_STATUS),
            null,
            null,
            null,
            null,
            null
        )
        cursor?.use { cur ->
            if (cur.moveToFirst()) {
                do {
                    val idIndex = cur.getColumnIndex(COLUMN_ID)
                    val taskIndex = cur.getColumnIndex(COLUMN_TASK)
                    val statusIndex = cur.getColumnIndex(COLUMN_STATUS)

                    if (idIndex != -1 && taskIndex != -1 && statusIndex != -1) {
                        val task = ToDoModel(
                            id = cur.getInt(idIndex),
                            task = cur.getString(taskIndex),
                            status = cur.getInt(statusIndex)
                        )
                        taskList.add(task)
                    }
                } while (cur.moveToNext())
            }
        }
        return taskList
    }

    fun updateStatus(id: Int, status: Int) {
        val cv = ContentValues().apply {
            put(COLUMN_STATUS, status)
        }
        val db = this.writableDatabase
        db.update(TABLE_TODO, cv, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    fun updateTask(id: Int, task: String) {
        val cv = ContentValues().apply {
            put(COLUMN_TASK, task)
        }
        val db = this.writableDatabase
        db.update(TABLE_TODO, cv, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    fun deleteTask(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_TODO, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }
}