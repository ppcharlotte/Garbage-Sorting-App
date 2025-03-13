package dk.itu.kotlingarbagev4

import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast



class MainActivity : AppCompatActivity(){
    private lateinit var itemsDB: ItemsDB

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputGarbage:TextView = findViewById(R.id.input_garbage)
        itemsDB = ItemsDB.get()
        itemsDB.fillItemsDB(this, "garbage.txt")

        val where: Button = findViewById(R.id.where_button)
        where.setOnClickListener{
            val itemName = inputGarbage.text.toString().trim()
            if(itemName.isEmpty()) {
                Toast.makeText(this, "Please enter an item!", Toast.LENGTH_SHORT).show()
            } else{
                val category:String = itemsDB.findCategory(itemName)
                inputGarbage.text = category
            }
        }

        val add: Button = findViewById(R.id.add_a_button)
        add.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }

    //empty the input area
    override fun onResume() {
        super.onResume()
        val inputGarbage:TextView = findViewById(R.id.input_garbage)
        inputGarbage.text=""
    }
}
