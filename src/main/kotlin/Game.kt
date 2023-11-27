class Game {
    var sumPlayer = 0
    var sumBot = 0
    var winner = 0
    val cardsPlayer = mutableListOf<Int>()
    val cardsDealer = mutableListOf<Int>()

    fun startGame(player: Player1, bot: Bot) {

        var bet = 0

        while (player.deposit >= 0) {

            if (player.deposit <= 0){
                println("You LOH proebal vse dengi )))")
                return
            }
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
                            notEnoughMoney(player)
                            incorrectValue = true
                        }
                    }

                    "Stop" -> return
                }
            }




            println("_______________")
            println("START GAME")
            println("_______________")


            println("Dealer card - ?")
            var dealerCard = bot.hit()
            cardsDealer.add(dealerCard)
            sumBot += dealerCard

            dealerCard = bot.hit()
            println("Dealer card - $dealerCard")
            cardsDealer.add(dealerCard)
            sumBot += dealerCard
            println()



            var playerCard = player.hit()
            cardsPlayer.add(playerCard)
            sumPlayer += playerCard
            println("Player card - $playerCard")

            playerCard = player.hit()
            cardsPlayer.add(playerCard)
            sumPlayer += playerCard
            println("Player card - $playerCard")

            gameState(player, bot)

            if (winner == 1) {
                player.deposit += bet
            } else if (winner == 0){
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

       while (bool){

           val playerCard = player.turn()
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


           val dealerCard = bot.turn(sumBot)
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

        winner = if (sumBot>sumPlayer) {
            additionalInformationOfGame("Dealer")
            setWhoWin(0)
        } else if (sumPlayer>sumBot) {
            additionalInformationOfGame("Player")
            setWhoWin(1)
        } else {
            additionalInformationOfGameForDraw()
            setWhoWin(2)
        }
    }
    fun helperGameStateForPlayer (sumPlayer: Int) : Boolean{
        if (sumPlayer > 21) {
            additionalInformationOfGame("Dealer")
            winner = setWhoWin(0)
            return false
        }
        if (sumPlayer == 21) {
            additionalInformationOfGame("Player")
            winner = setWhoWin(1)
            return false
        }
        return true
    }
    fun helperGameStateForBot (sumBot: Int) : Boolean{
        if (sumBot > 21) {
            additionalInformationOfGame("Player")
            winner = setWhoWin(1)
            return false
        }
        if (sumBot == 21) {
            additionalInformationOfGame("Dealer")
            winner = setWhoWin(0)
            return false
        }
        return true
    }
    fun setWhoWin(winner:Int):Int{
        return winner
    }

    fun additionalInformationOfGame(player: String){
        println()
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
        println()
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
    fun notEnoughMoney(player: Player1){
        val info = """
             You don't have enough money!
             Make your bet smaller
             Deposit is ${player.deposit}
        """.trimIndent()
        println(info)
        println()
    }
    fun questionOfContinue(){
        println("Do you want to continue?")
        print("Input [Y] - yes | [N] - no : ")
    }
}