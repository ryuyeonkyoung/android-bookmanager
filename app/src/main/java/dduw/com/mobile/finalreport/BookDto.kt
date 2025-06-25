package dduw.com.mobile.finalreport

data class BookDto(
    val id: Long = 0,
    val title: String,
    val author: String,
    val publisher: String?,
    val summary: String?,
    val price: Int?,
    val imageUri: String? = null,
    val publishedDate: String? = null
) : java.io.Serializable
