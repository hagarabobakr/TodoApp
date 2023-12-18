package com.example.todoappandroied.ui.home.edit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todoappandroied.Constants
import com.example.todoappandroied.R
import com.example.todoappandroied.database.MyDatabase
import com.example.todoappandroied.database.models.Task
import com.example.todoappandroied.databinding.ActivityEditTaskBinding
import com.example.todoappandroied.ui.home.MainActivity
import java.text.SimpleDateFormat
import java.util.Date

class EditTaskActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityEditTaskBinding
    lateinit var task :Task
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityEditTaskBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        task = ((intent.getSerializableExtra(Constants.TASK) as?Task)!!)

        showData(task)
        viewBinding.submit.setOnClickListener {
            updateToDo()
        }
    }

    private fun updateToDo() {
        if (isDataValid()){
            task.title = viewBinding.taskTitleTextFild.editText?.text.toString()
            task.description = viewBinding.taskDesc.editText?.text.toString()
            MyDatabase.getDatabase(this)
                .tasksDao()
                .updateTask(task)
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }

    private fun isDataValid(): Boolean {
            var valid = true
            val title = viewBinding.taskTitleTextFild.editText?.text.toString()
            val des = viewBinding.taskDesc.editText?.text.toString()
            if(title.isNullOrBlank()){
                viewBinding.taskTitleTextFild.error = "please enter title"
                valid = false
            }else{
                viewBinding.taskTitleTextFild.error = null
            }
            if(des.isNullOrBlank()){
                viewBinding.taskDesc.error = "please enter title"
                valid = false
            }else{
                viewBinding.taskDesc.error = null
            }
            return valid
    }

    private fun showData(task: Task) {
        viewBinding.taskTitleTextFild.editText?.setText(task.title)
        viewBinding.taskDesc.editText?.setText(task.description)
        var date = convertTimeToLong(task.date)
        viewBinding.taskDateText.text =date

    }

    private fun convertTimeToLong(date: Long?): String {
        val date = Date(date!!)
        val format = SimpleDateFormat("yyyy/MM/dd")
        return format.format(date)
    }


}