package dk.itu.garbagev6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_list, container, false)
        val listThings:TextView = v.findViewById(R.id.listItems)

        val viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            listThings.text = "Sorting List\n" + uiState.listItems
        }

        return v
    }
}