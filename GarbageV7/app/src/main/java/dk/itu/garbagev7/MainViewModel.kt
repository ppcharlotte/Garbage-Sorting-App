package dk.itu.garbagev7

import android.graphics.Color
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    private val itemsDB = ItemsDB.get()

    val uiState: MutableLiveData<SortingUiState> =
        MutableLiveData<SortingUiState>(SortingUiState(itemsDB.getItemList()))

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
            if (itemsDB.listItems().contains(itemName.lowercase())) {
                itemsDB.deleteItem(itemName)
                uiState.value = SortingUiState(itemsDB.getItemList())
                itemWhat.text = "" // 清空输入框
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
        }
        itemsDB.addItem(whatS, whereS)
        uiState.value = SortingUiState(itemsDB.getItemList())
        Toast.makeText(activity, "$whatS added to list!", Toast.LENGTH_SHORT).show()

    }

    fun onDeleteItemClick(itemName: String, activity: FragmentActivity) {
        itemsDB.deleteItem(itemName)
        uiState.value = SortingUiState(itemsDB.getItemList())
        Toast.makeText(activity, "$itemName removed!", Toast.LENGTH_SHORT).show()
    }


    data class SortingUiState(val itemList: List<Item>)

}