package com.example.midexam

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DoListFragment:Fragment()
{
    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var emptyText: TextView
    private var adapter: TaskAdapter= TaskAdapter()
    private var callbacks: DatePickerFragment.Callbacks? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    private val taskListViewModel: TaskListViewModel by lazy {
        ViewModelProviders.of(this).get(TaskListViewModel::class.java)
    }
    companion object {
        fun newInstance(): DoListFragment {
            return DoListFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.todo_list_fragment,
            container, false)
        taskRecyclerView =
            view.findViewById(R.id.crime_recycler_view) as
                    RecyclerView
        emptyText=view.findViewById(R.id.no_data) as TextView


        taskRecyclerView.layoutManager =
            LinearLayoutManager(context)

        taskRecyclerView.adapter = adapter


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState:
    Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskListViewModel.taskListLiveData.observe(
            viewLifecycleOwner,
            Observer { tasks ->
                tasks?.let {
                    Log.i("TAG", "Got ctaks ${tasks.size}")
                    updateUI(tasks)
                }
            })

    }
    private fun updateUI(tasks: List<Task>) {
        if (tasks.size==0)
        {
            emptyText.setVisibility(View.VISIBLE)

            taskRecyclerView.visibility=View.GONE


        }
        else
        {
            taskRecyclerView.visibility=View.VISIBLE

            emptyText.setVisibility(View.GONE)
            (taskRecyclerView.adapter as TaskAdapter).submitList(tasks)
        }



    }

    private inner class TaskHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener  {


        private lateinit var task: Task
        private  val titleTextView: TextView =
            itemView.findViewById(R.id.task_title)
        private   val detailsTextView: TextView =
                itemView.findViewById(R.id.details)
        private   val dateTextView: TextView =
            itemView.findViewById(R.id.task_date)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(task: Task) {
            this.task = task
            titleTextView.text = this.task.title
            detailsTextView.text = this.task.details
            dateTextView.text=this.task.date.toString()

        }

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }


    }
    private inner class TaskAdapter
        : androidx.recyclerview.widget.ListAdapter<Task, TaskHolder>(DiffCallback()){
        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int)
                : TaskHolder {
            val view =
                layoutInflater.inflate(R.layout.list_to_do_item, parent, false)
            return TaskHolder(view)
        }

        override fun onBindViewHolder(holder: TaskHolder,
                                      position: Int) {

            holder.bind(getItem(position))

        }
    }


    private inner class DiffCallback: DiffUtil.ItemCallback<Task>() {

        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }






}