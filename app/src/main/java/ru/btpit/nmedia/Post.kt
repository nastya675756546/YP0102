package  ru.btpit.nmedia
data class Post(
    var id:Int,
    val author:String,
    var text:String,
    val dataPublish:String,
    var hertNumber:Int,
    var shareNumber:Int,
    val isLike:Boolean,
    var watchingNumber:Int
)