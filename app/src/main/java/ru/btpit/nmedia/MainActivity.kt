package ru.btpit.nmedia
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.core.view.get
import android.os.Bundle
import android.widget.Adapter
import androidx.appcompat.app.AppCompatActivity
import ru.btpit.nmedia.databinding.ActivityMainBinding
import androidx.activity.viewModels
import androidx.lifecycle.GeneratedAdapter
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import ru.btpit.nmedia.PostRepositoryInMemoryImpl.*
import ru.btpit.nmedia.databinding.PostCardBinding

class MainActivity : AppCompatActivity(), PostAdapter.Listener {
    private val viewModel: PostViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = PostAdapter(this)
        binding.recycler.adapter = adapter
        viewModel.data.observe(this){posts ->
            adapter.list = posts
        }
    }

    override fun onClickLike(post: Post) {
        viewModel.like(post.id)
    }

    override fun onClickShare(post: Post) {
        viewModel.share(post.id)
    }
    override fun cancelEditPost(post: Post,binding: PostCardBinding) {

        viewModel.removeId(post.id)
    }
}