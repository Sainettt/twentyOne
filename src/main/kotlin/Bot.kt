import kotlin.random.Random

class Bot : Player() {
    override fun turn(sum:Int): Int {
        val hit = hit()
        return if (sum < 14) {
            println("Dealer receives a card: $hit")
            hit
        } else if (sum in 14 .. 17){
            val rand = randomizer()
            if (rand) {
                println("Dealer receives a card: $hit")
                hit
            }
            else 0
        }
        else if (sum in 15..19){
            val chooseTurn = Random.nextInt(1,3)
            if (chooseTurn == 1) {
                println("Dealer receives a card: $hit")
                hit
            }
            else 0
        } else
            0
    }
    fun randomizer(): Boolean {
        val probability = 0.85
        return Math.random() < probability
    }
}