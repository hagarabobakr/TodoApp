package com.example.todoappandroied.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.todoappandroied.R
import com.example.todoappandroied.databinding.ActivityMainBinding
import com.example.todoappandroied.ui.home.addTask.AddTaskBottomSheet
import com.example.todoappandroied.ui.home.addTask.OnDismissListener
import com.example.todoappandroied.ui.home.list.ListFragment
import com.example.todoappandroied.ui.home.settings.SettingsFragment

class MainActivity : AppCompatActivity() {
    lateinit var viewBinding:ActivityMainBinding
    val tasksListFragment = ListFragment();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.bottomNav
            .setOnItemSelectedListener { item->
                when(item.itemId){
                    R.id.list->{
//                        viewBinding.screenTitle.text = "" ;R.string.title_settings
                        viewBinding.screenTitle.setText(R.string.ListScreenTitle)
                        showFragment(tasksListFragment)
                    }
                    R.id.settings->{
                        viewBinding.screenTitle.setText(R.string.SettingsScreenTitle)
                        showFragment(SettingsFragment())
                    }
                }
                return@setOnItemSelectedListener true;
            }
        viewBinding.bottomNav.selectedItemId = R.id.list
        viewBinding.addTask.setOnClickListener {
            showAddTaskBottomSheet();
        }
    }
    fun showAddTaskBottomSheet(){
        val addTaskBottomSheet = AddTaskBottomSheet();
        addTaskBottomSheet.onDismissListener = OnDismissListener {
            tasksListFragment.loadTasks()

        }
        addTaskBottomSheet.show(supportFragmentManager,null);
    }

    fun showFragment(fragment:Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container,fragment)
            .commit()
    }
}
