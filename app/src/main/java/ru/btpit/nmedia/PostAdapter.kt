package ru.btpit.nmedia
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.btpit.nmedia.databinding.PostCardBinding
import android.view.View
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import com.bumptech.glide.Glide

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
                editTextContent.setText(post.text)

                textURL.text = post.url
                editTextURL.setText(post.url)
                if (post.url.startsWith("https://")) {
                    imageViewUrl.visibility = View.VISIBLE
                    imageViewUrl.setOnClickListener {
                        val intent = Intent()
                        intent.setAction(Intent.ACTION_VIEW)
                        intent.addCategory(Intent.CATEGORY_BROWSABLE)
                        intent.setData(Uri.parse(post.url))
                        binding.root.context.startActivity(intent)
                    }
                    Glide.with(binding.root.context)
                        .load(getURLImageVideo(post.url))
                        .into(imageViewUrl)
                }
                else
                    imageViewUrl.visibility = View.GONE

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
                close.setOnClickListener {
                    listener.onClickRemove(post)
                }

                this.menuClick.setOnClickListener {
                    listener.onClickMore(post,it, binding)
                }
                Cancel.setOnClickListener {
                    listener.cancelEditPost(post,binding)
                }
                Create.setOnClickListener {
                    listener.saveEditPost(post,binding)
                }
                if (post.id==0) listener.editModeOn(binding, "")
                }
            }
        }

    @SuppressLint("SuspiciousIndentation")
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
        fun onClickRemove(post: Post)
        fun onClickMore(post:Post, view: View, binding: PostCardBinding)
        fun cancelEditPost(post:Post,binding: PostCardBinding)
        fun saveEditPost(post:Post, binding: PostCardBinding)
        fun editModeOn(binding: PostCardBinding,content:String)
    }
}
@Suppress("SpellCheckingInspection")
fun getURLImageVideo(url:String):String{
    return  "https://img.youtube.com/vi/${if(url.split("v=").lastIndex == 1) url.split("v=")[1]
    else url.split("?si")[0].split("/").last()}/sddefault.jpg"

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
