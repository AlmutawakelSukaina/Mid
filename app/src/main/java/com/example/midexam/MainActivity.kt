package com.example.midexam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title=resources.getString(R.string.Add)
        loadFragment(TaskFragment())
        navigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_Add-> {
                    title=resources.getString(R.string.Add)
                    loadFragment(TaskFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_do-> {
                    title=resources.getString(R.string.To_Do)
                    loadFragment(ToDoFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_prog-> {
                title=resources.getString(R.string.Progress)
                loadFragment(ProgressFragment())
                return@setOnNavigationItemSelectedListener true
            }
                R.id.navigation_done-> {
                    title=resources.getString(R.string.Done)
                    loadFragment(DoneFragment())
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false

        }

    }

    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}