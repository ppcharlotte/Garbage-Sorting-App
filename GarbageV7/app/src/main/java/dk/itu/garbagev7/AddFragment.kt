package dk.itu.garbagev7

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

class AddFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_add, container, false)
        val itemName = v.findViewById<TextView>(R.id.what_text)
        val itemWhere = v.findViewById<TextView>(R.id.where_text)
        val add = v.findViewById<Button>(R.id.add_button)
        val viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        add.setOnClickListener{
            activity?.let {fragmentActivity ->
                val whatS = itemName.text.toString().trim()
                val whereS = itemWhere.text.toString().trim()
                viewModel.onAddItemClick(whatS, whereS, fragmentActivity)

                itemName.text = ""
                itemWhere.text = ""
            }
        }
        return v
    }
}