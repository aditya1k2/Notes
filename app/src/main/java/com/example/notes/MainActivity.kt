package com.example.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), INotesRVAdapter {

    lateinit var viewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = NotesRVAdapter(this,this)
        recyclerView.adapter = adapter


        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        viewModel.allNotes.observe(this, Observer {list->
                    list?.let{
                        adapter.updateList(it)
                    }
        })
    }

    fun submitData(view: View) {
        val input = findViewById<TextView>(R.id.input)
        val notetext = input.text.toString()
        if(notetext.isNotEmpty())
        {
            viewModel.insertNote(Note(notetext))
            Toast.makeText(this,"$notetext Inserted",Toast.LENGTH_SHORT).show()
        }
    }


    override fun onItemClicked(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this,"${note.text} Deleted",Toast.LENGTH_SHORT).show()
    }
}