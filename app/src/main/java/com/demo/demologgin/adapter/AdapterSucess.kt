package com.demo.demologgin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.demo.demologgin.R
import com.demo.demologgin.model.User

class AdapterSucess : RecyclerView.Adapter<AdapterSucess.SucessHolder>() {

    var list:List<User> = listOf()

    fun setListUser(list : List<User>)
    {
        this.list = list
        notifyDataSetChanged()

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SucessHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.customecyc,parent,false)
        return SucessHolder(view)
    }

    override fun onBindViewHolder(holder: SucessHolder, position: Int) {

        var user = list.get(position)
        if (user == null)
        {
            return
        }

        holder.id.text = user.id
        holder.age.text = user.age.toString()
        holder.email.text = user.email
        holder.name.text = user.name
        holder.phone.text = user.phone

    }

    override fun getItemCount(): Int {
        return list.size
    }


    class SucessHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var id :TextView
        var name :TextView
        var age :TextView
        var email :TextView
        var phone :TextView

        init {
            id = itemView.findViewById(R.id.id)
            name = itemView.findViewById(R.id.name)
            age = itemView.findViewById(R.id.age)
            email = itemView.findViewById(R.id.email)
            phone = itemView.findViewById(R.id.phone)

        }

    }
}