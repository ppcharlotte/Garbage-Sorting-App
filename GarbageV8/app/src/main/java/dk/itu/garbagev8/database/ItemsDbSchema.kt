package dk.itu.garbagev8.database

class ItemsDbSchema {
    object ItemTable {
        const val NAME = "Items"

        object Cols {
            const val WHAT = "what"
            const val WHERE = "whereC"
        }
    }
}