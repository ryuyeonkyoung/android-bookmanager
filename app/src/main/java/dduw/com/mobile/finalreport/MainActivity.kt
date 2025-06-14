package dduw.com.mobile.finalreport

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import dduw.com.mobile.finalreport.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dao: BookDaoImpl
    private lateinit var adapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        dao = BookDaoImpl(this)
        adapter = BookAdapter(dao.getAll().toMutableList())
        binding.rvBooks.layoutManager = LinearLayoutManager(this)
        binding.rvBooks.adapter = adapter

        // 아이템 클릭 시 UpdateBookActivity로 BookDto 전달
        adapter.setOnItemClickListener { book ->
            val intent = Intent(this, UpdateBookActivity::class.java)
            intent.putExtra("book", book)
            startActivityForResult(intent, 101)
        }

        // 롱클릭 시 삭제 다이얼로그
        adapter.setOnItemLongClickListener { book ->
            AlertDialog.Builder(this)
                .setTitle("도서 삭제")
                .setMessage("\"${book.title}\" 도서를 삭제하시겠습니까?")
                .setPositiveButton("삭제") { dialog, _ ->
                    dao.delete(book.id)
                    adapter.setItems(dao.getAll())
                    Toast.makeText(this, "삭제 완료", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("취소", null)
                .show()
            true
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.setItems(dao.getAll())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add -> {
                val intent = Intent(this, AddBookActivity::class.java)
                startActivityForResult(intent, 100)
                true
            }
            R.id.menu_about -> {
                val intent = Intent(this, DeveloperInfoActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_exit -> {
                AlertDialog.Builder(this)
                    .setTitle("앱 종료")
                    .setMessage("앱을 종료하시겠습니까?")
                    .setPositiveButton("확인") { _, _ -> finish() }
                    .setNegativeButton("취소", null)
                    .show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((requestCode == 100 || requestCode == 101) && resultCode == RESULT_OK) {
            adapter.setItems(dao.getAll())
        }
    }
}