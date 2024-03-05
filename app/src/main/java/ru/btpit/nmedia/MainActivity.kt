package ru.btpit.nmedia
import android.view.View
import android.widget.PopupMenu
import androidx.activity.viewModels
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.btpit.nmedia.databinding.ActivityMainBinding
import ru.btpit.nmedia.PostRepositoryInMemoryImpl.*
import android.widget.Toast
import android.content.Context
import android.view.inputmethod.InputMethodManager
import ru.btpit.nmedia.databinding.PostAddBinding
import android.annotation.SuppressLint
import ru.btpit.nmedia.databinding.PostCardBinding
import android.content.Intent
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), PostAdapter.Listener {
    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)


        setContentView(binding.root)
        intent?.let {
            if (it.action != Intent.ACTION_SEND){
                return@let
            }

            val text = it.getStringExtra(Intent.EXTRA_TEXT)
            if(text.isNullOrBlank())
            {
                Snackbar.make(binding.root, "Пусто", BaseTransientBottomBar.LENGTH_INDEFINITE)
                    .setAction("Окей"){
                        finish()
                    }.show()
                return@let
            }
            viewModel.addPost(text)
            it.action = ""
        }
        val adapter = PostAdapter(this)
        binding.plusPost.setOnClickListener {
            viewModel.addPost("")
        }
        binding.recycler.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.list = posts
        }


    }


    override fun onClickLike(post: Post) {
        viewModel.like(post.id)
    }

    override fun onClickShare(post: Post) {
        viewModel.share(post.id)

        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT,post.author+"\n"+post.text+"\n"+post.url)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(intent, "Поделиться")
        startActivity(shareIntent)

    }

    override fun onClickRemove(post: Post) {
        viewModel.removeId(post.id)
    }

    override fun onClickMore(post: Post, view: View, binding: PostCardBinding) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.inflate(R.menu.menu_fynks)
        popupMenu.setOnMenuItemClickListener {
            if (it.itemId == R.id.menu_item_edit) editModeOn(binding, "")
            true
        }
        popupMenu.show()
    }

    override fun editModeOn(binding: PostCardBinding,content:String) {
        with(binding)
        {
            text.visibility = View.INVISIBLE
            editTextContent.visibility = View.VISIBLE

            textURL.visibility = View.INVISIBLE
            editTextURL.visibility = View.VISIBLE
            imageViewUrl.visibility = View.GONE

            if (content!="")
                editTextContent.setText(content)
            constraintEdit.visibility = View.VISIBLE

        }

    }

    override fun cancelEditPost(post: Post, binding: PostCardBinding) {
        with(binding)
        {
            text.visibility = View.VISIBLE
            editTextContent.visibility = View.GONE
            textURL.visibility = View.VISIBLE
            editTextURL.visibility = View.GONE

            constraintEdit.visibility = View.GONE

        }

    }

    override fun saveEditPost(post: Post, binding: PostCardBinding) {
        viewModel.editById(
            post.id,
            binding.editTextURL.text.toString(),
            binding.editTextContent.text.toString()
        )
        cancelEditPost(post, binding)
    }
}



