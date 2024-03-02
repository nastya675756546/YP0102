package  ru.btpit.nmedia
data class Post(
    val id:Int,
    val author:String,
    val text:String,
    val dataPublish:String,
    var hertNumber:Int,
    var shareNumber:Int,
    val isLike:Boolean = false,
    var watchingNumber:Int
)