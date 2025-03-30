package dk.itu.garbagev7

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    private var itemsDB: ItemsDB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.garbage)

        itemsDB = ItemsDB.get()
        setUpFragments()

    }

    private fun setUpFragments() {
        val fm = supportFragmentManager
        var searchFragment:Fragment? = fm.findFragmentById(R.id.container_search)
        var listFragment:Fragment? = fm.findFragmentById(R.id.container_list)
        if ((searchFragment == null) && (listFragment == null)) {
            fm.beginTransaction()
                .add(R.id.container_search, Search())
                .add(R.id.container_list, ListFragment())
                .commit()
        }
    }


}