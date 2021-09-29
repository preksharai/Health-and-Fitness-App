package com.example.healthandfitness

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//we'll pass context and factory also when data is read/write
class SqliteOpenHelper(context: Context, factory:SQLiteDatabase.CursorFactory?)
    : SQLiteOpenHelper(context, database_name,factory, database_version){

    //this is readymade object, with its help, we can give properties without creating object,just use class name
    companion object{
        private val database_version=1
        private val database_name="Exercise.db"
        private val TABLE_NAME="History"
        private val COLUMN_ID="_id"
        private val COLUMN_COMPLETED_DATE="Completed_date"

    }

    //called when db/table is created
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_HISTORY_TABLE="CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY,$COLUMN_COMPLETED_DATE TEXT)"
        db?.execSQL(CREATE_HISTORY_TABLE)
    }

    //called when version of database changes..when you add new table or add new column
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    //this function inserts the date into the table, whatever it is getting from final activity
    fun addDate(date:String)
    {
        //get date and time from final activity
        //we can save info in table without any query by creating object of content values class which will be saved in
        //table using insert method
        val values=ContentValues()
        //put method tales two params, colmn name and value to be inserted
        values.put(COLUMN_COMPLETED_DATE,date)

        //now tell table name and database name too
        //we've defined it already still it is needed to create object of database to write into database
        val db=this.writableDatabase  //if want to read.. then readableDatabase and this means current class
        //insert method is used, 2nd para is asking whether data has null value or not
        db.insert(TABLE_NAME,null,values)

        //closing is necessary
        db.close()
    }



    //to check data gaya h ki nhi database mai, read it..if able to read then yes
    //to insert data into RV later, fetch data into an arraylist
    //this func task is to go into db, get all rows into an array and return it to be displayed in RV of history
    fun getDates():ArrayList<String>
    {
        val list=ArrayList<String>()
        val db=this.readableDatabase
        //to run a query use rawQuery method, selectionArgs is asking for any filter
        val cursor=db.rawQuery("SELECT * FROM $TABLE_NAME",null) //whole data come in cursor object
        //cursor object bydefault points to first row
        while(cursor.moveToNext()) {
            val datevalue = cursor.getString(cursor.getColumnIndex(COLUMN_COMPLETED_DATE)) //getString bcz date is in string
            list.add(datevalue)
        }
        cursor.close()
        return list
    }

}