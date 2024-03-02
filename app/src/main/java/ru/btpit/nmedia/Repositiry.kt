package ru.btpit.nmedia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface PostRepository {
    fun get(): LiveData<Post>
    fun like()
    fun share()
}

class PostRepositoryInMemoryImpl : PostRepository {
    private var post = Post(
        id = 1,
        author = "Образовательная среда для студентов",
        text = "\t BARRIER \n \t Именно для этих целей ConstraintLayout предлагает\n специальный компонент — Barrier.",
        dataPublish = "7 сентября 2023 г",
        isLike = false,
        hertNumber = 999,
        shareNumber = 999,
        watchingNumber = 6000
    )
    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data
    override fun like() {
        if (post.isLike)
            post.hertNumber--
        else
            post.hertNumber++
        post = post.copy(isLike = !post.isLike)
        data.value = post
    }
    override fun share() {
        post.shareNumber+=1
        data.value = post
    }
}
class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.get()
    fun like() = repository.like()
    fun share() = repository.share()
}