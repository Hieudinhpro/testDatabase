package com.hieudinh.testdatabase

import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private  lateinit var edName : EditText
    private  lateinit var edEmail : EditText
    private  lateinit var edClass : EditText
    private  lateinit var btnAdd : Button
    private  lateinit var btnView : Button
    private  lateinit var btnUpdate : Button


    private  lateinit var sqliteHelper : SQLiteHelper
    private  lateinit var recyclerView: RecyclerView
    private  var adapter :StudentAdapter?=null
    private  var  std : StudentModel? = null






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initRecycleView()
        sqliteHelper= SQLiteHelper(this)
        btnAdd.setOnClickListener{addStudent()}
        btnView.setOnClickListener{getStudent()}
        btnUpdate.setOnClickListener{updateStuden()}


        adapter?.setOnclickItemt { Toast.makeText(this,it.name,Toast.LENGTH_LONG).show()
        edName.setText(it.name)
            edEmail.setText(it.email)
            edClass.setText(it.classs)

            std=it


        }
        adapter?.setOnclickDeleteItemt {
            deleteStudent(it.id)
        }
    }

    private fun updateStuden() {
        val name = edName.text.toString()
        val email = edEmail.text.toString()
        val classs = edClass.text.toString()
        if(name == std?.name && email==std?.email && classs==std?.classs){
            Toast.makeText(this,"don't have  changed ",Toast.LENGTH_SHORT).show()
            return
        }
        if(std == null)  return
          val     std = StudentModel(id =std!!.id,name= name ,email= email,classs=classs)
        val status = sqliteHelper.updateStudent(std)
        if(status > -1 ){
            clearEditText()
            getStudent()
        }else{
            Toast.makeText(this,"not update ",Toast.LENGTH_SHORT).show()
        }


    }

    private fun getStudent()  {
        val  stdlist=sqliteHelper.getAllStudent()
        Log.e("ppp","${stdlist}")

        adapter?.addItemt(stdlist)
    }

    private  fun addStudent(){
        val name =edName.text.toString()
        val  email =edEmail.text.toString()
        val  classs =edClass.text.toString()

        if(name.isEmpty() || email.isEmpty() || classs.isEmpty()){
            Toast.makeText(this,"enter your information",Toast.LENGTH_SHORT).show()
        }else{
            val     std=StudentModel(name=name,email = email,classs = classs)
            val  status =sqliteHelper.insertStudent(std)
            //check insert
            if (status>-1){
                Toast.makeText(this,"added",Toast.LENGTH_SHORT).show()
                clearEditText()
                getStudent()

            }else{
                Toast.makeText(this,"no",Toast.LENGTH_SHORT).show()

            }
        }
    }
    private fun  deleteStudent(id : Int){
        if(id == null ) return
        val builder  =AlertDialog.Builder(this)
        builder.setMessage("deleting ?  are you sure ?")
        builder.setCancelable(true)
        builder.setPositiveButton("yes",){ dialog,_ ->
            sqliteHelper.deleteStudentByID(id)
            getStudent()
            dialog.dismiss()


        }
        builder.setNegativeButton("no",){ dialog,_ ->
            dialog.dismiss()

        }
        val  aler = builder.create()
        aler.show()

    }
    private fun clearEditText() {
       edName.setText("")
        edEmail.setText("")
        edClass.setText("")
        edName.requestFocus()


    }

private  fun initRecycleView(){
    recyclerView.layoutManager= LinearLayoutManager(this)
    adapter = StudentAdapter()
    recyclerView.adapter=adapter

}

    private  fun  initView(){
        edName=findViewById(R.id.edName)
        edEmail=findViewById(R.id.edEmail)
        edClass=findViewById(R.id.edClasss)
        btnView=findViewById(R.id.btnView)
        btnAdd=findViewById(R.id.btnAdd)
        btnUpdate=findViewById(R.id.btnUpdate)
        recyclerView =findViewById(R.id.recycleView)
    }
}