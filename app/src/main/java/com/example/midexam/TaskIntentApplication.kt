package com.example.midexam

import android.app.Application

class TaskIntentApplication :Application()
{

    override fun onCreate() {
        super.onCreate()
        TaskRepository.initialize(this)
    }



}