import kotlin.random.Random

 abstract class Player (
){
    open fun turn (): Int {
        return 0
    }
     open fun turn (x:Int): Int {
         return 0
     }
    open fun hit () : Int{
        val hit = Random.nextInt(2,11)
        return hit
    }
}