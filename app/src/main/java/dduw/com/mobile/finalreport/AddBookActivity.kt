package dduw.com.mobile.finalreport

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dduw.com.mobile.finalreport.databinding.ActivityAddBookBinding

class AddBookActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBookBinding
    private lateinit var dao: BookDaoImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dao = BookDaoImpl(this)

        binding.btnAdd.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val author = binding.etAuthor.text.toString().trim()
            val publisher = binding.etPublisher.text.toString().trim().ifEmpty { null }
            val summary = binding.etSummary.text.toString().trim().ifEmpty { null }
            val priceText = binding.etPrice.text.toString().trim()
            val price = priceText.toIntOrNull()

            if (title.isEmpty() || author.isEmpty()) {
                Toast.makeText(this, "제목과 저자는 필수입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val book = BookDto(
                title = title,
                author = author,
                publisher = publisher,
                summary = summary,
                price = price
            )
            dao.insert(book)
            setResult(Activity.RESULT_OK)
            finish()
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
    }
}
