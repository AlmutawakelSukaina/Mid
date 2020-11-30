package com.example.midexam

import androidx.lifecycle.ViewModel

class TaskListViewModel : ViewModel() {


    private val taskRepository = TaskRepository.get()

    val taskListLiveData = taskRepository.getTasks("1")
    val taskListProgress= taskRepository.getTasks("2")
    val taskListDone=taskRepository.getTasks("3")

    fun addTask(task: Task) {
        taskRepository.addTask(task)
    }
    fun  updateTask(task:Task)
    {
        taskRepository.updateTask(task)

    }
}