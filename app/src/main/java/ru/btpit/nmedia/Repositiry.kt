package ru.btpit.nmedia

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Calendar

interface PostRepository {
    fun get(): LiveData<List<Post>>
    fun like(id:Int)
    fun share(id:Int)
    fun removeId(id:Int)
    fun addPost(post: Post, string : String)
    fun editById(id: Int,url:String, content: String)
    interface OnInteractionListener{
        fun onLike(post: Post){}
        fun onEdit(post: Post){}
        fun onRemove(post: Post){}}
}

class PostRepositoryInMemoryImpl(context: Context) : PostRepository {
    private val gson = Gson()
    private val prefs =  context.getSharedPreferences("repoNastya", Context.MODE_PRIVATE)
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val key = "posts"
    private var posts = listOf<Post>()
    private val data = MutableLiveData(posts)

    init {
        prefs.getString(key, null)?.let {
            posts = gson.fromJson(it,type)
        }
        data.value = posts

    }
    private fun sync(){
        with(prefs.edit()){
            putString(key, gson.toJson(posts))
            apply()
        }
    }

//    private var posts = listOf(
//        Post(
//            id = 1,
//            author = "Образовательная среда для студентов",
//            text = "\t BARRIER \n \t Именно для этих целей ConstraintLayout предлагает специальный компонент — Barrier.",
//            url = "https://www.youtube.com/watch?v=ZZ91wIny3C4",
//            dataPublish = "7 сентября 2023 г",
//            isLike = false,
//            hertNumber = 999,
//            shareNumber = 999,
//            watchingNumber = 6000
//        ),
//        Post(
//            id = 2,
//            author = "Суши-даром",
//            text = "Покупайте нашщи суши\nСегодня скидка 30%",
//            url = "",
//            dataPublish = "15 сентября 2023г",
//            isLike = false,
//            hertNumber = 999,
//            shareNumber = 999,
//            watchingNumber = 78000
//        )
//
//    )
//    private val data = MutableLiveData(posts)

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
        sync()
    }

    override fun share(id: Int) {
        posts = posts.map {
            if (it.id != id)
                it
            else
                it.copy(shareNumber = it.shareNumber + 1)
        }
        data.value = posts
        sync()

    }

    override fun removeId(id: Int) {
        posts = posts.filter { it.id != id }
        data.value = posts
        sync()

    }

    override fun addPost(post: Post, string: String) {
        posts = listOf(
            post.copy(
                id = nextId(posts),
                dataPublish = Calendar.getInstance().time.toString(),
                hertNumber = 0,
                shareNumber = 0,
                isLike = false
            )
        ) + posts
        data.value = posts
        sync()
    }

    override fun editById(id: Int,url:String, content: String) {
        posts = posts.map {
            if(it.id != id)
                it
            else {
                if (it.id == 0 ) it.id = nextId(posts)
                it.copy(text = content, url = url)
            }
        }
        data.value = posts
        sync()
    }
}




    fun nextId(posts:List<Post>):Int
    {
        var id = 1
        posts.forEach{it1->
            posts.forEach{
                if (it.id==id) id=it.id+1
            }
        }

        return id
    }
    private val empty = Post(
        0,
        "Nastya",
        "",
        "",
        Calendar.getInstance().time.toString(),
        0,
        0,
        false,
        0
    )
class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryInMemoryImpl(application)
        val data = repository.get()
        private val edited = MutableLiveData(empty)
        fun addPost(string: String){
            edited.value?.let {
                repository.addPost(it, string)
            }
            edited.value = empty
        }
        fun editById(id: Int,url:String,content:String){
            repository.editById(id,url,content)
        }
        fun changeContent(content:String){
            edited.value?.let {
                if (it.text == content.trim()) return
                edited.value = it.copy(text = content.trim(),)
            }
        }
        fun like(id: Int) = repository.like(id)
        fun share(id: Int) = repository.share(id)
        fun removeId(id: Int) = repository.removeId(id)


    }
