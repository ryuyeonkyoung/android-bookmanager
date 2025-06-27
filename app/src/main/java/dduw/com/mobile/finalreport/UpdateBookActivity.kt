package dduw.com.mobile.finalreport

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dduw.com.mobile.finalreport.databinding.ActivityAddBookBinding
import java.util.Calendar

class UpdateBookActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBookBinding
    private lateinit var dao: BookDaoImpl
    private var bookId: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dao = BookDaoImpl(this)

        val book = intent.getSerializableExtra("book") as? BookDto
        if (book == null) {
            Toast.makeText(this, "도서 정보가 없습니다.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        bookId = book.id
        binding.etTitle.setText(book.title)
        binding.etAuthor.setText(book.author)
        binding.etPublisher.setText(book.publisher ?: "")
        binding.etSummary.setText(book.summary ?: "")
        binding.etPrice.setText(book.price?.toString() ?: "")
        binding.etPublishedDate.setText(book.publishedDate ?: "")
        binding.btnAdd.text = "수정"

        val imageRes = when (book.title) {
            "어린왕자" -> R.mipmap.orinwangja
            "데미안" -> R.mipmap.demian
            "1984" -> R.mipmap.n1984
            "죄와 벌" -> R.mipmap.joewa_beol
            "삼국지" -> R.mipmap.samgukji
            else -> R.mipmap.ic_launcher
        }
        binding.imageBookCover.setImageResource(imageRes)

        binding.etPublishedDate.setOnClickListener {
            val cal = Calendar.getInstance()
            // 기존 값이 있으면 해당 날짜로 초기화
            if (!book.publishedDate.isNullOrEmpty()) {
                val parts = book.publishedDate.split("-")
                if (parts.size == 3) {
                    cal.set(Calendar.YEAR, parts[0].toInt())
                    cal.set(Calendar.MONTH, parts[1].toInt() - 1)
                    cal.set(Calendar.DAY_OF_MONTH, parts[2].toInt())
                }
            }
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val dialog = DatePickerDialog(this, { _, y, m, d ->
                val dateStr = String.format("%04d-%02d-%02d", y, m + 1, d)
                binding.etPublishedDate.setText(dateStr)
            }, year, month, day)
            dialog.show()
        }

        binding.btnAdd.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val author = binding.etAuthor.text.toString().trim()
            val publisher = binding.etPublisher.text.toString().trim().ifEmpty { null }
            val summary = binding.etSummary.text.toString().trim().ifEmpty { null }
            val priceText = binding.etPrice.text.toString().trim()
            val price = priceText.toIntOrNull()
            val publishedDate = binding.etPublishedDate.text.toString().trim().ifEmpty { null }

            if (title.isEmpty() || author.isEmpty()) {
                Toast.makeText(this, "제목과 저자는 필수입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updatedBook = BookDto(
                id = bookId,
                title = title,
                author = author,
                publisher = publisher,
                summary = summary,
                price = price,
                publishedDate = publishedDate
            )
            dao.update(updatedBook)
            setResult(Activity.RESULT_OK)
            finish()
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
    }
}
