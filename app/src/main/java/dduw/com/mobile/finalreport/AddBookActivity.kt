package dduw.com.mobile.finalreport

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dduw.com.mobile.finalreport.databinding.ActivityAddBookBinding
import java.util.Calendar

class AddBookActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBookBinding
    private lateinit var dao: BookDaoImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dao = BookDaoImpl(this)

        var selectedDate: String? = null
        binding.etPublishedDate.setOnClickListener {
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val dialog = DatePickerDialog(this, { _, y, m, d ->
                val dateStr = String.format("%04d-%02d-%02d", y, m + 1, d)
                binding.etPublishedDate.setText(dateStr)
                selectedDate = dateStr
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

            val book = BookDto(
                title = title,
                author = author,
                publisher = publisher,
                summary = summary,
                price = price,
                publishedDate = publishedDate
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
