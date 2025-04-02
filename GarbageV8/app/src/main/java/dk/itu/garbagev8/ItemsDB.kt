package dk.itu.garbagev8

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import dk.itu.garbagev8.database.DBCreate
import dk.itu.garbagev8.database.ItemCursorWrapper
import dk.itu.garbagev8.database.ItemsDbSchema

class ItemsDB{
    companion object {
        var mDatabase: SQLiteDatabase? = null
    }

    fun initialize(context: Context) {
        if (mDatabase == null) {
            mDatabase = DBCreate(context.applicationContext).writableDatabase
        }
    }

    fun getValues(): ArrayList<Item>{
        val items = ArrayList<Item>()
        val cursor = queryItems(null, null)
        cursor.moveToFirst()
        while(!cursor.isAfterLast){
            items.add(cursor.item)
            cursor.moveToNext()
        }
        cursor.close()
        return items
    }

    fun findCategory(itemName: String): String {
        val whereClause = "${ItemsDbSchema.ItemTable.Cols.WHAT}=?"
        val whereArgs = arrayOf(itemName.lowercase())
        val cursor = queryItems(whereClause, whereArgs)
        cursor.moveToFirst()

        return if (!cursor.isAfterLast) {
            val item = cursor.item
            "$itemName should be placed in: ${item.where}"
        } else {
            "$itemName should be placed in: not found "
        }.also{
            cursor.close()
        }
    }

    fun addItem(what: String, where: String): Boolean {
        val whatS = what.lowercase()
        val whereS  = where.trim().lowercase().replaceFirstChar { it.titlecase()}
        if (getValues().any { it.what.lowercase() == whatS }){
            return false
        }

        val values = ContentValues()
        values.put(ItemsDbSchema.ItemTable.Cols.WHAT, whatS)
        values.put(ItemsDbSchema.ItemTable.Cols.WHERE, whereS)
        mDatabase!!.insert(ItemsDbSchema.ItemTable.NAME, null, values)
        return true
    }

    fun deleteItem(what: String) {
        val newItem = Item(what, "")
        val selection = ItemsDbSchema.ItemTable.Cols.WHAT + " LIKE ?"
        mDatabase!!.delete(ItemsDbSchema.ItemTable.NAME, selection, arrayOf(newItem.what))
    }

    fun size(): Int {
        return getValues().size
    }

    private fun queryItems(whereClause: String?, whereArgs: Array<String>?): ItemCursorWrapper {
        val cursor = mDatabase!!.query(
            ItemsDbSchema.ItemTable.NAME,
            null,  // Columns - null selects all columns
            whereClause, whereArgs,
            null,  // groupBy
            null,  // having
            null // orderBy
        )
        return ItemCursorWrapper(cursor)
    }
}