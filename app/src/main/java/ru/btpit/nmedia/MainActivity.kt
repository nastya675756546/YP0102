package ru.btpit.nmedia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.TextView
import ru.btpit.nmedia.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {

    private var amountLike = 999
    private var amountShare = 999
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val post = Post(
            0,
            "Образовательная среда для студентов",
            "\t BARRIER \n \t Именно для этих целей ConstraintLayout предлагает\n специальный компонент — Barrier.",
            "7 сентября 2023 г",
            999,
            500,
            false,
            6_500_000
        )
        with(binding) {
            author.text = post.author
            text.text = post.text
            dataPublish.text = post.dataPublish
            hertNumber.text = post.hertNumber.toString()
            shareNumber.text = post.shareNumber.toString()
            watchingNumber.text = convertToString(post.watchingNumber)
            if (post.isLike)
                hert.setImageResource(R.drawable.heart_red_24)

            hert.setOnClickListener {
                if(post.isLike) {
                    post.hertNumber--
                    hert.setImageResource(R.drawable.heart_24)
                } else {
                    post.hertNumber++
                    hert.setImageResource(R.drawable.heart_red_24)
                }
                hertNumber.text = convertToString(post.hertNumber)
                post.isLike = post.isLike.not()
            }
            share.setOnClickListener {
                post.shareNumber+=1
                shareNumber.text = convertToString(post.shareNumber)
            }
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