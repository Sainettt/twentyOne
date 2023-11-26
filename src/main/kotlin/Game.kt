class Game {
    var sumPlayer = 0
    var sumBot = 0
    var winner = false
    val cardsPlayer = mutableListOf<Int>()
    val cardsDealer = mutableListOf<Int>()

    fun startGame(player: Player1, bot: Bot) {

        var bet: Int = 0

        while (player.deposit > 0) {
            println("Your deposit: ${player.deposit}")
            println()

            var incorrectValue = true
            while (incorrectValue) {
                print("Make your choice, Bet or Stop: ")
                when (readln()) {

                    "Bet" -> {
                        print("Place your bet: ")
                        bet = readln().toIntOrNull() ?: 0
                        incorrectValue = false
                        println()
                        if (bet <= 0) {
                            println("Error try again")
                            println()
                            incorrectValue = true
                        }
                        if (bet > player.deposit) {
                            println(notEnoughMoney(player))
                            println()
                            incorrectValue = true
                        }
                    }

                    "Stop" -> return
                }
            }




            println("_______________")
            println("START GAME")
            println("_______________")
            var dealerCard = 0
            var playerCard = 0

            println("Dealer card - ?")
            dealerCard = bot.hit()
            cardsDealer.add(dealerCard)
            sumBot += dealerCard

            dealerCard = bot.hit()
            println("Dealer card - $dealerCard")
            cardsDealer.add(dealerCard)
            sumBot += dealerCard
            println()



            playerCard = player.hit()
            cardsPlayer.add(playerCard)
            sumPlayer += playerCard
            println("Player card - $playerCard")

            playerCard = player.hit()
            cardsPlayer.add(playerCard)
            sumPlayer += playerCard
            println("Player card - $playerCard")
            println()

            gameState(player, bot)
            if (winner) {
                player.deposit += bet
            } else {
                player.deposit -= bet
            }
            sumPlayer = 0
            sumBot = 0
            cardsPlayer.clear()
            cardsDealer.clear()

            questionOfContinue()
            var incorrectAnswer = true
            while (incorrectAnswer) {
                when (readln()) {
                    "Y" -> {
                        println()
                        incorrectAnswer = false
                    }
                    "N" -> return
                    else -> {
                        print("Input [Y] or [N] : ")
                    }
                }
            }
        }
    }

   fun gameState(player: Player1, bot: Bot){
       var bool = true
       var playerCard = 0
       var dealerCard = 0

       while (bool){
           playerCard = player.turn()
           if (playerCard == 0){
               calculateResult(sumBot,sumPlayer)
               bool = false
           }
           else {
               cardsPlayer.add(playerCard)
               this.sumPlayer += playerCard
           }
           if (!bool) return
           if (!helperGameStateForPlayer(this.sumPlayer)) return


           dealerCard = bot.turn(sumBot)
           if (dealerCard == 0) {
              calculateResult(sumBot,sumPlayer)
               bool = false
           }
           else {
               cardsDealer.add(dealerCard)
               this.sumBot += dealerCard
           }
           if (!bool) return
           if (!helperGameStateForBot(this.sumBot)) return
       }
   }
    fun calculateResult(sumBot: Int, sumPlayer:Int){
        if (sumBot>sumPlayer) {
            additionalInformationOfGame("Dealer")
            setWhoWin(false)
        }
        else if (sumPlayer>sumBot) {
            additionalInformationOfGame("Player")
            setWhoWin(true)
        }
        else additionalInformationOfGameForDraw()
    }
    fun helperGameStateForPlayer (sumPlayer: Int) : Boolean{
        if (sumPlayer > 21) {
            additionalInformationOfGame("Dealer")
            setWhoWin(false)
            return false
        }
        if (sumPlayer == 21) {
            additionalInformationOfGame("Player")
            setWhoWin(true)
            return false
        }
        return true
    }
    fun helperGameStateForBot (sumBot: Int) : Boolean{
        if (sumBot > 21) {
            additionalInformationOfGame("Player")
            setWhoWin(true)
            return false
        }
        if (sumBot == 21) {
            additionalInformationOfGame("Dealer")
            setWhoWin(false)
            return false
        }
        return true
    }
    fun setWhoWin(winner:Boolean){
        this.winner = winner
    }

    fun additionalInformationOfGame(player: String){
        val res = """
            ___________________________________
             Dealer cards: ${cardsDealer} hand: $sumBot
             Player cards: ${cardsPlayer} hand: $sumPlayer
             $player WIN
            ___________________________________
        """.trimIndent()
        println(res)
        println()
    }

    fun additionalInformationOfGameForDraw(){
        val res = """
            ___________________________________
             Dealer cards: ${cardsDealer} hand: $sumBot
             Player cards: ${cardsPlayer} hand: $sumPlayer
             Draw
            ___________________________________
        """.trimIndent()
        println(res)
        println()
    }
    fun notEnoughMoney(player: Player1):String{
        val info = """
             You don't have enough money!
             Make your bet smaller
             Deposit is ${player.deposit}
        """.trimIndent()
        return info
    }
    fun questionOfContinue(){
        println("Do you wanna continue?")
        print("Input [Y] - yes | [N] - no : ")
    }
}