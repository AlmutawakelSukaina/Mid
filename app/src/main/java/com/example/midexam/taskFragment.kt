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
import android.view.*
import android.widget.*
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.task_fragment.*
import java.io.File
import java.util.*
import androidx.lifecycle.Observer

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
    private lateinit var title:String
    private lateinit var details:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        task = Task()



    }

    interface Callbacks {
        fun onTaskSelect(taskId: UUID)
    }
    private var callbacks: Callbacks? = null

    private val taskDetailViewModel: TaskDetailsModel by
    lazy {
        ViewModelProviders.of(this).get(TaskDetailsModel::class.java)
    }

    private val taskListViewModel: TaskListViewModel by
    lazy {
        ViewModelProviders.of(this).get(TaskListViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskDetailViewModel.taskLiveData.observe(
            viewLifecycleOwner,Observer {   task ->
                task?.let {
                    this.task = task


                    updateUI()
                }
            } )

    }

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
        detailsField=view.findViewById(R.id.details) as EditText
        timeButton=view.findViewById(R.id.task_date) as Button
        timeButton.setText("Add Time")
         add = view.findViewById(R.id.addTask) as Button


       title=titleField.text.toString()
        details=detailsField.text.toString()

        timeButton.setOnClickListener {
            DatePickerFragment.newInstance(task.date).apply {
                setTargetFragment(this@TaskFragment,
                    REQUEST_DATE)
                show(this@TaskFragment.requireFragmentManager(),
                    DIALOG_DATE)
            }
        }
     add.setOnClickListener{


         if(titleField.text.isNullOrEmpty())
             titleField?.error="You have to fill the field"
         else
             if(detailsField.text.isNullOrEmpty())
             detailsField?.error="You have to fill the field"


         else
             {
                 taskListViewModel.addTask(task)
                 callbacks?.onTaskSelect(task.id)
                 val toast= Toast.makeText(context,"The Task has been added to TO DO list",Toast.LENGTH_LONG)
                 toast.setGravity(Gravity.CENTER,0,0)
                 toast.show()

             }


     }

        return view
    }

    override fun onStart() {
        super.onStart()
        val titleWatcher = object : TextWatcher {
            override fun beforeTextChanged(sequence: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                task.title = sequence.toString()
                //task.details=sequence.toString()
            }
            override fun afterTextChanged(sequence: Editable?) {


            }
        }
        titleField.addTextChangedListener(titleWatcher)


    }


    private fun updateUI() {
        titleField.setText(task.title)
        detailsField.setText(task.details)
        dt= DateFormat.getDateFormat(context).format(task.date)
        timeButton.text= dt



    }


    override fun onDateSelected(date: Date) {
        task.date = date
        task.title=titleField.text.toString()
       task.details=detailsField.text.toString()
        task.type="1"
        updateUI()

    }
    override fun onStop() {
        super.onStop()
        taskDetailViewModel.saveTask(task)
    }




}