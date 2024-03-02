package  ru.btpit.nmedia
data class Post(
    val id:Long,
    val author:String,
    val text:String,
    val dataPublish:String,
    var hertNumber:Int,
    var shareNumber:Int,
    val isLike:Boolean,
    var watchingNumber:Int
)