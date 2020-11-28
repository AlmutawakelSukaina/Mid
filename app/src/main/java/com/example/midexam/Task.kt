package com.example.midexam

import java.util.*
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(@PrimaryKey val id: UUID = UUID.randomUUID(),
                var title: String = "",
                var date: Date = Date(),
                var details: String = "" )

{

}
