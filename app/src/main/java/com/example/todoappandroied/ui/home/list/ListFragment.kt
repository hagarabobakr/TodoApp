package com.example.todoappandroied.ui.home.list

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todoappandroied.Constants
import com.example.todoappandroied.R
import com.example.todoappandroied.database.MyDatabase
import com.example.todoappandroied.database.models.Task
import com.example.todoappandroied.databinding.FragmentListBinding
import com.example.todoappandroied.ui.home.edit.EditTaskActivity
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.util.Calendar


class ListFragment : Fragment() {
    lateinit var viewBinding: FragmentListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentListBinding.inflate(inflater,container,false)
        return viewBinding.root
    }
    lateinit var taskAdapter: TasksRecyclerAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {

        taskAdapter = TasksRecyclerAdapter(null)
        viewBinding.tasksRecycler.adapter = taskAdapter
        viewBinding.calendarView.setOnDateChangedListener { widget, date, selected ->
            if (selected){
                currentDate.set(Calendar.DAY_OF_MONTH,date.day)
                currentDate.set(Calendar.MONTH,date.month-1)
                currentDate.set(Calendar.YEAR,date.year)
                loadTasks()
            }
        }
        viewBinding.calendarView.selectedDate = CalendarDay.today()
        taskAdapter.onCarditemEditClick =
            OnCardItemClick { task ->
                val alirtDialogBuldir = AlertDialog.Builder(activity)
                    .setMessage(resources.getString(R.string.edit))
                    .setPositiveButton(getString(R.string.update))
                    { _, which ->
                        updateTodo(task)
                    }.setNegativeButton(getString(R.string.no),
                        { dialog, which ->
                            dialog.dismiss()
                        }).show()
            }
        taskAdapter.onCardItemDeletClick = OnCardItemClick { task ->
            val alirtDialogBuldir = AlertDialog.Builder(activity)
                .setMessage(resources.getString(R.string.delet))
                .setPositiveButton(getString(R.string.delete))
                { _, which ->
                    deletTodo(task)
                }.setNegativeButton(getString(R.string.no),
                    { dialog, which ->
                        dialog.dismiss()
                    }).show()
        }
        taskAdapter.onCardItemDonetClick = OnCardItemClick { task ->
        task.isDone = true
            MyDatabase
                .getDatabase(requireContext())
                .tasksDao()
                .updateTask(task)
            refreshRecyclerView()
        }
    }

    private fun deletTodo(task: Task?) {
        if (task != null) {
            MyDatabase.getDatabase(requireContext())
                .tasksDao()
                .deleteTask(task)
            refreshRecyclerView()
        }
    }

    private fun refreshRecyclerView() {
        taskAdapter.changeData(MyDatabase.getDatabase(requireContext()).
        tasksDao().getTasksByDate(currentDate.timeInMillis))
        taskAdapter.notifyDataSetChanged()
    }

    private fun updateTodo(task: Task) {
        var intent = Intent(requireContext(),EditTaskActivity()::class.java)
        intent.putExtra(Constants.TASK,task)
        startActivity(intent)

    }


    var currentDate = Calendar.getInstance()
    init {
        currentDate.set(Calendar.HOUR,0)
        currentDate.set(Calendar.MINUTE,0)
        currentDate.set(Calendar.SECOND,0)
        currentDate.set(Calendar.MILLISECOND,0)
    }
    override fun onResume() {
        super.onResume()
        loadTasks()
    }
    fun loadTasks(){
        if(!isResumed){
            return
        }
        val tasks =  MyDatabase.getDatabase(requireActivity())
            .tasksDao()
            .getTasksByDate(currentDate.timeInMillis);
        taskAdapter.changeData(tasks)

    }


}