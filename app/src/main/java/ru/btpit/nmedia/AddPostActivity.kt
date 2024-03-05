package ru.btpit.nmedia

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import ru.btpit.nmedia.databinding.PostAddBinding


class AddPostActivityViewModel : ViewModel() {
    private val repository = AddPostRepositoryInMemoryImpl()
    var isFirst = repository.isFirs
    var contentText = repository.contentText
    /* fun setLayout() = repository.setLayout()
     fun setData(appCompatActivity:AppCompatActivity,mainBinding:ViewBinding,addPostBinding:ViewBinding) = repository.setData(appCompatActivity,mainBinding,addPostBinding)
     fun checkoutLayout() = repository.checkoutLayout()*/
}

class AddPostRepositoryInMemoryImpl()  {
    val isFirs = true
    val contentText = ""
}
