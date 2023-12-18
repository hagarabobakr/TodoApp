package com.example.todoappandroied.ui.home.list

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoappandroied.R
import com.example.todoappandroied.database.models.Task
import com.example.todoappandroied.databinding.ItemTaskBinding

class TasksRecyclerAdapter(var items : List<Task>?): RecyclerView.Adapter<TasksRecyclerAdapter.ViewHolder>() {
    class ViewHolder (val viewBinding : ItemTaskBinding) :RecyclerView.ViewHolder(viewBinding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context)
        ,parent,false)
        return ViewHolder(viewBinding)
    }
    var onCarditemEditClick : OnCardItemEditClick? = null
    var onCardItemDeletClick : OnCardItemDeletClick? = null
    var onCardItemDonetClick : OnCardItemDoneClick? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.viewBinding.title.text = items?.get(position)?.title
        holder.viewBinding.desc.text = items?.get(position)?.description
        holder.viewBinding.leftView.setOnClickListener {
            onCarditemEditClick?.onCardItemEditClicked(items!![position])
        }
        holder.viewBinding.rightView.setOnClickListener {
            onCardItemDeletClick?.onCardItemDeletClicked(items!![position])
        }
        holder.viewBinding.doneBtn.setOnClickListener {
            onCardItemDonetClick?.onCardItemDoneClicked(items!![position])
        }
        if(items!![position].isDone){
            holder.viewBinding.title.setTextColor(Color.GREEN)
            holder.viewBinding.desc.setTextColor(Color.GREEN)
            holder.viewBinding.startLine.setBackgroundColor(Color.GREEN)
            holder.viewBinding.doneBtn.setBackgroundResource(R.drawable.make_done)
        }
    }
    fun changeData(newListOfTasks:List<Task>?){
        items = newListOfTasks
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items?.size ?:0


}