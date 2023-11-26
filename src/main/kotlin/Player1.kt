class Player1(var deposit: Int) : Player() {

    override fun turn() : Int {
        var bool = true
        val hit = hit()
        while (bool) {
            print("Make your turn: ")
            when (readln()) {
                "Hit" -> {
                    println("Player receives a card: $hit")
                    bool = false
                    return hit
                }

                "Stand" -> {
                    bool = false
                    return 0
                }
                else -> {
                    println("Chose: Hit or Stand")
                    println()
                }
            }
        }
        return hit
    }
}