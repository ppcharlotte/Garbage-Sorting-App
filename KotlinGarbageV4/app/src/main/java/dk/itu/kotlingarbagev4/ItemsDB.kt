package dk.itu.kotlingarbagev4

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class ItemsDB private constructor() {
    private val itemsMap: HashMap<String, String> = HashMap()

    companion object {
        private var sItemsDB: ItemsDB? = null

        fun get(): ItemsDB {
            return sItemsDB ?: ItemsDB().also { sItemsDB = it }
        }
    }

    fun fillItemsDB(context: Context, filename: String) {
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(
                InputStreamReader(context.assets.open(filename))
            )
            var line = reader.readLine()
            while (line != null) {
                val parts = line.split(",")
                if (parts.size == 2) {
                    val name = parts[0].trim()
                    val category = parts[1].trim()
                    itemsMap[name] = category
                }
                line = reader.readLine()
            }
        } catch (e: IOException) {
            Log.e("ItemsDB", "An error occurred: ${e.message}")
        } finally {
            reader?.close()
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
}


