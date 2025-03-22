package dk.itu.garbagev6

class ItemsDB private constructor() {
    private val itemsMap: HashMap<String, String> = HashMap()

    init{
        fillItemsDB()
    }

    companion object {
        private var sItemsDB: ItemsDB? = null

        fun get(): ItemsDB {
            return sItemsDB ?: ItemsDB().also { sItemsDB = it }
        }
    }

    fun findCategory(itemName: String): String {
        val category = itemsMap[itemName.lowercase()]
        return if (category != null) {
            "$itemName should be placed in: $category"
        } else {
            "$itemName should be placed in: not found "
        }
    }

    fun addItem(what: String, where: String) {
        itemsMap[what.lowercase()] = where.lowercase()
    }

    fun deleteItem(itemName: String){
        itemsMap.remove(itemName.lowercase())
    }

    fun listItems():String{
        return itemsMap.entries.joinToString("\n") { (key, value) -> "$key in $value" }
    }

    private fun fillItemsDB() {
        itemsMap["newspaper"] = "Paper"
        itemsMap["can"] = "Metal"
        itemsMap["magazine"] = "Paper"
        itemsMap["milk carton"] = "Food"
        itemsMap["stone box"] = "Cardboard"
    }


}