package com.example.todoappandroied.ui.home.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todoappandroied.R
import com.example.todoappandroied.databinding.FragmentListBinding
import com.example.todoappandroied.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {
    lateinit var viewBinding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentSettingsBinding.inflate(inflater,container,false)
        return viewBinding.root
    }


}