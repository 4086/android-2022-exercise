package com.example.repositoryaddex

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

class MyAdapter(private val context: Context, private val repos: List<RepoD>) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val repo = repos[position]
        holder.itemView.findViewById<TextView>(R.id.textView1).text = repo.name
        holder.itemView.findViewById<TextView>(R.id.textView2).text = repo.owner
        holder.itemView.setOnClickListener {
            AlertDialog.Builder(context).setMessage("You clicked ${repo.name}.").show()
        }
    }

    override fun getItemCount(): Int {
        return repos.size
    }
}