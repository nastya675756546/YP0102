package ru.btpit.nmedia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface PostRepository {
    fun get(): LiveData<List<Post>>
    fun like(id:Int)
    fun share(id:Int)
    fun removeId(id:Int)
}

class PostRepositoryInMemoryImpl : PostRepository {

    private var posts = listOf(
        Post(
            id = 1,
            author = "Образовательная среда для студентов",
            text = "\t BARRIER \n \t Именно для этих целей ConstraintLayout предлагает специальный компонент — Barrier.",
            dataPublish = "7 сентября 2023 г",
            isLike = false,
            hertNumber = 999,
            shareNumber = 999,
            watchingNumber = 6000
        ),
        Post(
            id = 2,
            author = "Образовательная среда для студентов",
            text = "\t ADAPTER\n \t Вызывает соответствующие методы (в наúем примере мы оповещаем адаптер об изменении данных, что приводит к перерисовке списка. ",
            dataPublish = "15 сентября 2023г",
            isLike = false,
            hertNumber = 999,
            shareNumber = 999,
            watchingNumber = 78000
        )

    )
    private val data = MutableLiveData(posts)

    override fun get(): LiveData<List<Post>> = data
    override fun like(id: Int) {

        posts = posts.map {
            if (it.id != id) it else {
                if (it.isLike)
                    it.hertNumber--
                else
                    it.hertNumber++
                it.copy(isLike = !it.isLike)
            }
        }
        data.value = posts
    }

    override fun share(id: Int) {
        posts = posts.map {
            if (it.id != id)
                it
            else
                it.copy(shareNumber = it.shareNumber + 1)
        }
        data.value = posts
    }

    override fun removeId(id: Int) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    class PostViewModel : ViewModel() {
        private val repository: PostRepository = PostRepositoryInMemoryImpl()
        val data = repository.get()
        fun like(id: Int) = repository.like(id)
        fun share(id: Int) = repository.share(id)
        fun removeId(id: Int) = repository.removeId(id)
    }
}