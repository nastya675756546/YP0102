package ru.btpit.nmedia
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.btpit.nmedia.databinding.PostCardBinding

class PostAdapter(private val listener: Listener):RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    var list = emptyList<Post>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    class PostViewHolder(
        private val binding: PostCardBinding
    ):RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post,listener: Listener) {
            binding.apply {
                author.text = post.author
                text.text = post.text
                dataPublish.text = post.dataPublish

                hertNumber.text = convertToString(post.hertNumber)
                shareNumber.text = convertToString(post.shareNumber)

                watchingNumber.text = convertToString(post.watchingNumber)

                hert.setImageResource(if (post.isLike) R.drawable.heart_red_24 else R.drawable.heart_24)
                hert.setOnClickListener {
                    listener.onClickLike(post)
                }
                share.setOnClickListener {
                    listener.onClickShare(post)
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }
    override fun onBindViewHolder(holder: PostViewHolder, position:Int){
        val post = list[position]
        holder.bind(post, listener)
    }
    override fun getItemCount() :Int = list. size

    interface Listener{
        fun onClickLike(post: Post)
        fun onClickShare(post: Post)
    }
}

@OptIn(ExperimentalStdlibApi::class)
private fun convertToString(count:Int):String{
    return when(count){
        in 0..<1_000 -> count.toString()
        in 1_000..<1_000_000 -> ((count/100).toFloat()/10).toString() + "K"
        in 1_000_000..<1_000_000_000 -> ((count/100_000).toFloat()/10).toString() + "M"
        else -> "B"
    }
}
