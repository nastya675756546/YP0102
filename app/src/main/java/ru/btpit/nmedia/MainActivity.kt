package ru.btpit.nmedia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.btpit.nmedia.databinding.ActivityMainBinding
import androidx.activity.viewModels

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val postViewModel: PostViewModel by viewModels()
        postViewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                text.text = post.text
                dataPublish.text = post.dataPublish
                hertNumber.text = convertToString(post.hertNumber)
                shareNumber.text = convertToString(post.shareNumber)
                if (post.isLike)
                    hert.setImageResource(R.drawable.heart_red_24)
                else
                    hert.setImageResource(R.drawable.heart_24)

            }
        }

        binding.hert.setOnClickListener{
            postViewModel.like()
        }
        binding.share.setOnClickListener{
            postViewModel.share()
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
    }