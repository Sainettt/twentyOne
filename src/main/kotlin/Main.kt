fun main(){
    val player:Player1 = Player1(100)
    val dealer:Bot = Bot()
val game:Game = Game()
    game.startGame(player = player, bot = dealer)

}