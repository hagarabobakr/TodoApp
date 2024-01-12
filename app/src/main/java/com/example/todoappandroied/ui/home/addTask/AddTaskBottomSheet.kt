package com.example.todoappandroied.ui.home.addTask

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.system.Os.accept
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todoappandroied.R
import com.example.todoappandroied.database.MyDatabase
import com.example.todoappandroied.database.models.Task
import com.example.todoappandroied.databinding.FragmentAddTaskBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.*

class AddTaskBottomSheet : BottomSheetDialogFragment() {
    lateinit var viewBinding: FragmentAddTaskBinding
    var currentDate = Calendar.getInstance()
    init {
        currentDate.set(Calendar.HOUR,0)
        currentDate.set(Calendar.MINUTE,0)
        currentDate.set(Calendar.SECOND,0)
        currentDate.set(Calendar.MILLISECOND,0)
    }
    var onDismissListener:OnDismissListener? = null
    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissListener?.onDismiss()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAddTaskBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDate()
        viewBinding.taskDate.setOnClickListener {
            showDatePicker()
        }
        viewBinding.submit.setOnClickListener {
            addTask()
        }
    }
    private fun validate() : Boolean{
        var valid = true
        val title = viewBinding.taskTitle.editText?.text.toString()
        val des = viewBinding.taskDesc.editText?.text.toString()
        if(title.isNullOrBlank()){
            viewBinding.taskTitle.error = getString(R.string.please_enter_title)
            valid = false
        }else{
            viewBinding.taskTitle.error = null
        }
        if(des.isNullOrBlank()){
            viewBinding.taskDesc.error = getString(R.string.please_enter_description)
            valid = false
        }else{
            viewBinding.taskDesc.error = null
        }
        return valid
    }
    private fun addTask() {
        if (!validate()){
            return
        }
        val title = viewBinding.taskTitle.editText?.text.toString()
        val des = viewBinding.taskDesc.editText?.text.toString()
        //add task in room database
        MyDatabase.getDatabase(requireActivity())
            .tasksDao().insertTask(Task(
                    title = title,
                    description = des,
                    date = currentDate.timeInMillis
            )
            )
        showTaskInsertDialog()
    }
    private fun showTaskInsertDialog() {
        val alirtDialogBuldir = AlertDialog.Builder(activity)
            .setMessage(resources.getString(R.string.successfully))
            .setPositiveButton(resources.getString(R.string.ok))
            { dialog, which ->
                dialog.dismiss()
                    dismiss()
            }.setCancelable(false)
            .show()
    }
    private fun showDatePicker() {
       val datePaicker =  DatePickerDialog(requireActivity(), DatePickerDialog.OnDateSetListener
        { view, year, month, dayOfMonth ->
            currentDate.set(Calendar.YEAR,year)
            currentDate.set(Calendar.MONTH,month)
            currentDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            setDate()

        },  currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH))
        datePaicker.show()
    }
    //function To type today's date or date specified for the user
    private fun setDate() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(currentDate.time)
        viewBinding.taskDateText.text = formattedDate
    }


}