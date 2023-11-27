class Player1(var deposit: Int) : Player() {

    override fun turn() : Int {
        var bool = true
        val hit = hit()
        println()
        print("Make your turn Hit or Stand or Double: ")
        while (bool) {

            when (readln()) {
                "Hit" -> {
                    println()
                    println("Player receives a card: $hit")
                    bool = false
                    return hit
                }

                "Stand" -> {
                    bool = false
                    return 0
                }
                "Double" -> {
                    println("Player receives a card: $hit")
                    bool = false
                    return hit
                }
                else -> {
                    print("Chose: [Hit] [Stand] [Double] : ")
                }
            }
        }
        return hit
    }
}