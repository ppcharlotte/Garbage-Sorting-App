package dk.itu.kotlingarbagev4

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

class AddActivity : AppCompatActivity() {
    private lateinit var itemsDB: ItemsDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_items)
        itemsDB = ItemsDB.get()

        val newWhat: TextView = findViewById(R.id.what_text)
        val newWhere: TextView = findViewById(R.id.where_text)

        val addItem: Button = findViewById(R.id.add_button)

        addItem.setOnClickListener {
            val whatS = newWhat.text.toString().trim()
            val whereS = newWhere.text.toString().trim()
            if ((whatS.isNotEmpty()) && (whereS.isNotEmpty())) {
                itemsDB.addItem(whatS, whereS)
                newWhat.text = ""
                newWhere.text = ""
            } else {
                Toast.makeText(this, R.string.add_toast, Toast.LENGTH_LONG).show()
            }
        }
    }
}