package com.example.midexam

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import java.io.File
import java.util.*

class TaskDetailsModel: ViewModel() {
    private val taskRepository = TaskRepository.get()
    private val taskIdLiveData = MutableLiveData<UUID>()
    var taskLiveData: LiveData<Task?> =
        switchMap(taskIdLiveData) { taskId ->
            taskRepository.getTask(taskId)
        }
    fun loadTask(taskId: UUID) {
        taskIdLiveData.value = taskId
    }
    fun saveTask(task: Task) {
        taskRepository.updateTask(task)
    }




}