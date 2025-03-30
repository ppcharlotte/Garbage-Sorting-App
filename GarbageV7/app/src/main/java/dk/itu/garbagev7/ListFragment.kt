package dk.itu.garbagev7

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_list, container, false)
        val itemList: RecyclerView = v.findViewById(R.id.listItems)
        val viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        itemList.layoutManager = LinearLayoutManager(requireContext())

        val mAdapter = ItemAdapter(viewModel)
        itemList.adapter = mAdapter
        mAdapter.updateItems(viewModel.uiState.value?.itemList ?: emptyList())

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            mAdapter.updateItems(state.itemList)
        }
        return v
    }

    private inner class ItemHolder(itemView: View, mainViewModel: MainViewModel):
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val itemWhat: TextView
        private val itemWhere: TextView
        private val itemNo: TextView
        private val viewModel = mainViewModel
        private lateinit var currentItem: Item

        init {
            itemNo = itemView.findViewById(R.id.item_no)
            itemWhat = itemView.findViewById(R.id.item_name)
            itemWhere = itemView.findViewById(R.id.item_where)
            itemView.setOnClickListener(this)
        }

        fun bind(item: Item, position: Int) {
            currentItem = item
            itemNo.text = " ${position + 1} "
            itemWhat.text = item.what
            itemWhere.text = item.where
        }

        override fun onClick(v: View?) {
            activity?.let { fragmentActivity ->
                viewModel.onDeleteItemClick(currentItem.what, fragmentActivity)
            }
        }
    }

    private inner class ItemAdapter(mainViewModel: MainViewModel) :
        RecyclerView.Adapter<ItemHolder>() {
        private val viewModel = mainViewModel
        private var items: List<Item> = emptyList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
            val layoutInflater = LayoutInflater.from(activity)
            val v = layoutInflater.inflate(R.layout.one_row, parent, false)
            return ItemHolder(v, viewModel)
        }

        override fun onBindViewHolder(holder: ItemHolder, position: Int) {
            holder.bind(items[position], position)
        }

        override fun getItemCount() = items.size

        fun updateItems(newItems: List<Item>) {
            items = newItems
            notifyDataSetChanged()
        }
    }
}