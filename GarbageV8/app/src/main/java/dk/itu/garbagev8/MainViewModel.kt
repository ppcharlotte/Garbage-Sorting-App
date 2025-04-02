package dk.itu.garbagev8

import android.content.Context
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    private val itemsDB: ItemsDB = ItemsDB()

    val uiState: MutableLiveData<SortingUiState> =
        MutableLiveData<SortingUiState>(SortingUiState.Empty)

    fun initializeDB(context: Context) {
        itemsDB.initialize(context)
        updateUiState()
    }

    fun onFindCategoryClick(itemWhat: TextView, activity: FragmentActivity){
        val itemName = itemWhat.text.toString().trim { it <= ' ' }
        if (itemName.isEmpty()) {
            Toast.makeText(activity, "Please enter an item!", Toast.LENGTH_SHORT).show()
        } else {
            val category = itemsDB.findCategory(itemName)
            itemWhat.text = category
        }
        itemWhat.onEditorAction(EditorInfo.IME_ACTION_DONE)
    }

    fun onDeleteItemClick(itemWhat: TextView, activity: FragmentActivity){
        val itemName = itemWhat.text.toString().trim()
        if (itemName.isEmpty()) {
            Toast.makeText(activity, "Please enter an item to delete!", Toast.LENGTH_SHORT).show()
        } else {
            if (itemsDB.getValues().any { it.what.lowercase() == itemName.lowercase() }) {
                itemsDB.deleteItem(itemName)
                updateUiState()
                itemWhat.text = ""
                Toast.makeText(activity, "$itemName removed!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "$itemName not found!", Toast.LENGTH_SHORT).show()
            }
        }
        itemWhat.onEditorAction(EditorInfo.IME_ACTION_DONE)
    }

    fun onAddItemClick(whatS : String, whereS:String, activity: FragmentActivity){
        if (whatS.isEmpty() || whereS.isEmpty()){
            Toast.makeText(activity, "Please enter item and where to put it", Toast.LENGTH_SHORT).show()
            return
        }
        val add = itemsDB.addItem(whatS, whereS)
        if (add) {
            updateUiState()
            Toast.makeText(activity, "$whatS added to list!", Toast.LENGTH_SHORT).show()
        }else {
            Toast.makeText(activity, "$whatS already exist!", Toast.LENGTH_SHORT).show()
        }
    }

    fun onDeleteItemClick(itemName: String, activity: FragmentActivity) {
        itemsDB.deleteItem(itemName)
        updateUiState()
        Toast.makeText(activity, "$itemName removed!", Toast.LENGTH_SHORT).show()
    }

    private fun updateUiState() {
        uiState.value = SortingUiState(itemsDB.getValues(), itemsDB.size())
    }


    data class SortingUiState(
        val itemList: List<Item>,
        val itemListSize: Int
    ){
        companion object{
            val Empty = SortingUiState(arrayListOf(), 0)
        }
    }

}