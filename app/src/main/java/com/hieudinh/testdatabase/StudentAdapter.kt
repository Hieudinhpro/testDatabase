package com.hieudinh.testdatabase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter :RecyclerView.Adapter<StudentAdapter.StudentViewHoder>(){

    private  var stdlist: ArrayList <StudentModel> = ArrayList()
    private var onclickItemt:((StudentModel )  -> Unit)  ? = null
    private var onclickDeleteItemt:((StudentModel )  -> Unit)  ? = null


    fun addItemt(items: ArrayList<StudentModel>){
        this.stdlist =items
        notifyDataSetChanged()
    }

fun setOnclickItemt(callback : (StudentModel) ->Unit){

    this.onclickItemt = callback
}
    fun  setOnclickDeleteItemt(callback : (StudentModel) ->Unit){
        this.onclickDeleteItemt = callback

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= StudentViewHoder (
        LayoutInflater.from(parent.context).inflate(R.layout.cart_item_std,parent,false)
            )

    override fun onBindViewHolder(holder: StudentViewHoder, position: Int) {
        val std = stdlist[position]
        holder.binView(std)
        holder.itemView.setOnClickListener{ onclickItemt?.invoke(std)}
        holder.btnDelete.setOnClickListener{onclickDeleteItemt?.invoke((std))}
    }

    override fun getItemCount(): Int {
       return stdlist.size
    }
    class  StudentViewHoder(var view : View) : RecyclerView.ViewHolder(view){
        private var  id = view.findViewById<TextView>(R.id.tvId)
        private var  name = view.findViewById<TextView>(R.id.tvName)
        private var  email = view.findViewById<TextView>(R.id.tvEmail)
        private var  classs = view.findViewById<TextView>(R.id.tvClasss)

        var  btnDelete = view.findViewById<Button>(R.id.btnDelete)

        fun binView(std:StudentModel){

            id.text=std.id.toString()
            name.text=std.name.toString()
            email.text=std.email.toString()
            classs.text=std.classs.toString()

        }

    }

}