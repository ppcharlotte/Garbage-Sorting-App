package dk.itu.garbagev6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider


class Search : Fragment() {
    private lateinit var itemsDB: ItemsDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemsDB = ItemsDB.get()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val v = inflater.inflate(R.layout.fragment_search, container, false)
        val viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        //Text Fields
        val itemWhat = v.findViewById<TextView>(R.id.input_garbage)

       //buttons
        val where = v.findViewById<Button>(R.id.where_button)
        val delete = v.findViewById<Button>(R.id.delete_button)

        where.setOnClickListener{
            activity?.let {fragmentActivity ->
                viewModel.onFindCategoryClick(itemWhat,fragmentActivity)
            }
        }

        delete.setOnClickListener {
            activity?.let {fragmentActivity ->
                viewModel.onDeleteItemClick(itemWhat, fragmentActivity)
            }
        }

        return v
    }
}