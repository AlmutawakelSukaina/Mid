package com.example.midexam

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.task_fragment.*
import java.io.File
import java.util.*

private const val ARG_TASK_ID = "task_id"
private const val REQUEST_DATE = 0
private const val DIALOG_DATE = "DialogDate"
class TaskFragment:Fragment(), DatePickerFragment.Callbacks
{
    private lateinit var task: Task
    private lateinit var titleField: EditText
    private lateinit var timeButton: Button
    private lateinit var add: Button
    private lateinit var  detailsField:EditText
    private lateinit var dt:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        task = Task()


    }

    private val taskListViewModel: TaskListViewModel by
    lazy {
        ViewModelProviders.of(this).get(TaskListViewModel::class.java)
    }

    private var callbacks: DatePickerFragment.Callbacks? = null


    companion object {
        fun newInstance(): TaskFragment {
            return TaskFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.task_fragment,
            container, false)

        titleField = view.findViewById(R.id.task_title) as EditText

        timeButton=view.findViewById(R.id.task_date) as Button
        timeButton.setText("Add Time")
         add = view.findViewById(R.id.addTask) as Button
        detailsField=view.findViewById(R.id.details) as EditText



        timeButton.setOnClickListener {
            DatePickerFragment.newInstance(task.date).apply {
                setTargetFragment(this@TaskFragment,
                    REQUEST_DATE)
                show(this@TaskFragment.requireFragmentManager(),
                    DIALOG_DATE)
            }
        }
     add.setOnClickListener{
       
     }


        return view
    }

    override fun onStart() {
        super.onStart()

        val titleWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }
            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                task.title = sequence.toString()
                task.details=sequence.toString()
            }
            override fun afterTextChanged(sequence: Editable?) {
// This one too
            }
        }
        titleField.addTextChangedListener(titleWatcher)
        detailsField.addTextChangedListener(titleWatcher)
    }


    private fun updateUI() {
        titleField.setText(task.title)
        detailsField.setText(task.details)
        dt= DateFormat.getDateFormat(context).format(task.date)
        timeButton.text= dt



    }

    override fun onDateSelected(date: Date) {
        task.date = date
        updateUI()

    }





}