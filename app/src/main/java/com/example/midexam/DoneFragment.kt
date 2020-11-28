package com.example.midexam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

class DoneFragment:Fragment()
{


    private lateinit var task: Task
    private lateinit var title: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        task = Task()


    }

    private val taskListViewModel: TaskListViewModel by
    lazy {
        ViewModelProviders.of(this).get(TaskListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.done_fragment,
            container, false)

        title = view.findViewById(R.id.done) as TextView





        return view
    }












}