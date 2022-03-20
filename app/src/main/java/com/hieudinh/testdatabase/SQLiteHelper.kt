package com.hieudinh.testdatabase

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception

class SQLiteHelper  (context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    companion object{
        //thoi doi cot thi thay doi ten database
        private const val DATABASE_NAME ="student1.db"
        private const val DATABASE_VERSION=1
        private const val ID="id"
        private  const val NAME ="name"
        private const val EMAIL = "email"
        private const val CLASSS = "classs"
        private  const val TBL_STUDENT="tbl_student"
    }
    override fun onCreate(p0: SQLiteDatabase?) {
        val  createTbStudent=("CREATE TABLE "+ TBL_STUDENT+" ("+ ID+" INTEGER PRIMARY KEY ,"
                + NAME + " TEXT, " + EMAIL +" TEXT ," + CLASSS +" TEXT " +" )")
        p0?.execSQL(createTbStudent)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS $TBL_STUDENT")
        onCreate(p0)
    }
    fun insertStudent(std : StudentModel) : Long{
        val  db =this.writableDatabase
        val  contentValues= ContentValues()
        contentValues.put(ID,std.id)
        contentValues.put(NAME,std.name)
        contentValues.put(EMAIL,std.email)
        contentValues.put(CLASSS,std.classs)


        val success = db.insert(TBL_STUDENT,null,contentValues)
        db.close()
        return success

    }

    @SuppressLint("Range")
    fun getAllStudent() : ArrayList<StudentModel>{
        val stdlist : ArrayList<StudentModel> = ArrayList()
        val selectQuery="SELECT * FROM $TBL_STUDENT"
        val  db =this.readableDatabase
        val  cursor : Cursor?
        try {
            cursor= db.rawQuery(selectQuery,null)

        }catch (e:Exception){

            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id : Int
        var name : String

        var email : String
        var classs : String

        if (cursor.moveToFirst()){
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                name = cursor.getString(cursor.getColumnIndex("name"))
                email = cursor.getString(cursor.getColumnIndex("email"))
                classs = cursor.getString(cursor.getColumnIndex("classs"))


                val std =StudentModel(id=id,name=name,email= email ,classs=classs)
                stdlist.add(std)

            }while (cursor.moveToNext())
        }
        return  stdlist
    }
    fun  updateStudent(std : StudentModel) : Int{
        val db =this.writableDatabase
        val caontentValues = ContentValues()
        caontentValues.put(ID,std.id)
        caontentValues.put(NAME,std.name)
        caontentValues.put(EMAIL,std.email)
        caontentValues.put(CLASSS,std.classs)


        val success =db.update(TBL_STUDENT,caontentValues,"id = "+ std.id,null)
        db.close()
        return  success


    }
    fun deleteStudentByID(id :Int): Int {
        val db=this.writableDatabase
        val contentValues=ContentValues()
        contentValues.put(ID,id)
        val success = db.delete(TBL_STUDENT," id=$id", null)
            db.close()
        return success
    }

}