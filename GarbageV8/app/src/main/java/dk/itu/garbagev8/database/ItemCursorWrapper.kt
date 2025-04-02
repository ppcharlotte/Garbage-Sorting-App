package dk.itu.garbagev8.database

import android.database.Cursor
import android.database.CursorWrapper
import dk.itu.garbagev8.Item
import dk.itu.garbagev8.database.ItemsDbSchema.ItemTable

class ItemCursorWrapper(cursor: Cursor?) : CursorWrapper(cursor) {
    val item: Item
        get() {
            val what = getString(getColumnIndex(ItemTable.Cols.WHAT))
            val where = getString(getColumnIndex(ItemTable.Cols.WHERE))
            return Item(what, where)
        }
}