package dduw.com.mobile.finalreport

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dduw.com.mobile.finalreport.databinding.ItemBookBinding

class BookAdapter(
    private val data: MutableList<BookDto>
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    private var itemClickListener: ((BookDto) -> Unit)? = null
    private var itemLongClickListener: ((BookDto) -> Boolean)? = null

    fun setOnItemClickListener(listener: (BookDto) -> Unit) {
        itemClickListener = listener
    }

    fun setOnItemLongClickListener(listener: (BookDto) -> Boolean) {
        itemLongClickListener = listener
    }

    inner class BookViewHolder(val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: BookDto) {
            binding.textTitle.text = book.title
            binding.textAuthor.text = book.author
            val imageRes = when (book.title) {
                "어린왕자" -> R.mipmap.orinwangja
                "데미안" -> R.mipmap.demian
                "1984" -> R.mipmap.n1984
                "죄와 벌" -> R.mipmap.joewa_beol
                "삼국지" -> R.mipmap.samgukji
                else -> R.mipmap.ic_launcher
            }
            binding.imageView.setImageResource(imageRes)
            binding.root.setOnClickListener {
                itemClickListener?.invoke(book)
            }
            binding.root.setOnLongClickListener {
                itemLongClickListener?.invoke(book) ?: false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun setItems(newItems: List<BookDto>) {
        data.clear()
        data.addAll(newItems)
        notifyDataSetChanged()
    }

    fun addItem(book: BookDto) {
        data.add(book)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        data.removeAt(position)
        notifyDataSetChanged()
    }

    fun filterList(newList: List<BookDto>) {
        data.clear()
        data.addAll(newList)
        notifyDataSetChanged()
    }
}
