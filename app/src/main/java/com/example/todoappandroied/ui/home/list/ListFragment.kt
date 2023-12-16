package com.example.todoappandroied.ui.home.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todoappandroied.database.MyDatabase
import com.example.todoappandroied.databinding.FragmentListBinding
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
        taskAdapter = TasksRecyclerAdapter(null)
        viewBinding.tasksRecycler.adapter = taskAdapter
        viewBinding.calendarView.setOnDateChangedListener { widget, date, selected ->
            if (selected){
                currentDate.set(Calendar.DAY_OF_MONTH,date.day)
                currentDate.set(Calendar.MONTH,date.month)
                currentDate.set(Calendar.YEAR,date.year)
                loadTasks()
            }
        }
        viewBinding.calendarView.selectedDate = CalendarDay.today()

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