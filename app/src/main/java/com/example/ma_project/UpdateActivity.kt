package com.example.ma_project
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.ma_project.R

class UpdateActivity : AppCompatActivity() {

    private lateinit var titleInput: EditText
    private lateinit var authorInput: EditText
    private lateinit var pagesInput: EditText
    private lateinit var updateButton: Button
    private lateinit var deleteButton: Button

    private lateinit var id: String
    private lateinit var title: String
    private lateinit var author: String
    private lateinit var pages: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        titleInput = findViewById(R.id.title_input2)
        authorInput = findViewById(R.id.author_input2)
        pagesInput = findViewById(R.id.pages_input2)
        updateButton = findViewById(R.id.update_button)
        deleteButton = findViewById(R.id.delete_button)


        getAndSetIntentData()

        supportActionBar?.title = title

        updateButton.setOnClickListener {

            val myDB = MyDatabaseHelper(this)
            title = titleInput.text.toString().trim()
            author = authorInput.text.toString().trim()
            pages = pagesInput.text.toString().trim()
            myDB.updateData(id, title, author, pages)
        }

        deleteButton.setOnClickListener {
            confirmDialog()
        }
    }

    private fun getAndSetIntentData() {
        if (intent.hasExtra("id") && intent.hasExtra("title") &&
            intent.hasExtra("author") && intent.hasExtra("pages")
        ) {
            // Getting Data from Intent
            id = intent.getStringExtra("id")!!
            title = intent.getStringExtra("title")!!
            author = intent.getStringExtra("author")!!
            pages = intent.getStringExtra("pages")!!

            // Setting Intent Data
            titleInput.setText(title)
            authorInput.setText(author)
            pagesInput.setText(pages)
            Log.d("stev", "$title $author $pages")
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun confirmDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete $title ?")
        builder.setMessage("Are you sure you want to delete $title ?")
        builder.setPositiveButton("Yes") { _, _ ->
            val myDB = MyDatabaseHelper(this)
            myDB.deleteOneRow(id)
            finish()
        }
        builder.setNegativeButton("No") { _, _ -> }

        builder.create().show()
    }
}
