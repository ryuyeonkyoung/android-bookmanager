package dduw.com.mobile.finalreport

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class BookDaoImpl(context: Context) : BookDao {
    private val dbHelper = BookDBHelper(context)

    override fun insert(book: BookDto): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("title", book.title)
            put("author", book.author)
            put("publisher", book.publisher)
            put("summary", book.summary)
            put("price", book.price)
        }
        val id = db.insert(BookDBHelper.TABLE_NAME, null, values)
        db.close()
        return id
    }

    override fun update(book: BookDto): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("title", book.title)
            put("author", book.author)
            put("publisher", book.publisher)
            put("summary", book.summary)
            put("price", book.price)
        }
        val result = db.update(
            BookDBHelper.TABLE_NAME,
            values,
            "_id = ?",
            arrayOf(book.id.toString())
        )
        db.close()
        return result
    }

    override fun delete(id: Long): Int {
        val db = dbHelper.writableDatabase
        val result = db.delete(
            BookDBHelper.TABLE_NAME,
            "_id = ?",
            arrayOf(id.toString())
        )
        db.close()
        return result
    }

    override fun getById(id: Long): BookDto? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            BookDBHelper.TABLE_NAME,
            null,
            "_id = ?",
            arrayOf(id.toString()),
            null, null, null
        )
        val book = if (cursor.moveToFirst()) cursorToBook(cursor) else null
        cursor.close()
        db.close()
        return book
    }

    override fun getAll(): List<BookDto> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            BookDBHelper.TABLE_NAME,
            null, null, null, null, null, null
        )
        val books = mutableListOf<BookDto>()
        if (cursor.moveToFirst()) {
            do {
                books.add(cursorToBook(cursor))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return books
    }

    private fun cursorToBook(cursor: Cursor): BookDto {
        return BookDto(
            id = cursor.getLong(cursor.getColumnIndexOrThrow("_id")),
            title = cursor.getString(cursor.getColumnIndexOrThrow("title")),
            author = cursor.getString(cursor.getColumnIndexOrThrow("author")),
            publisher = cursor.getString(cursor.getColumnIndexOrThrow("publisher")),
            summary = cursor.getString(cursor.getColumnIndexOrThrow("summary")),
            price = if (!cursor.isNull(cursor.getColumnIndexOrThrow("price"))) cursor.getInt(cursor.getColumnIndexOrThrow("price")) else null
        )
    }
}

