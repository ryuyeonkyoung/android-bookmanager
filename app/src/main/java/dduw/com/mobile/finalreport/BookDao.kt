package dduw.com.mobile.finalreport

interface BookDao {
    fun insert(book: BookDto): Long
    fun update(book: BookDto): Int
    fun delete(id: Long): Int
    fun getById(id: Long): BookDto?
    fun getAll(): List<BookDto>
}

