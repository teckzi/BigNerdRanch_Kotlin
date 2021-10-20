import com.kkk.nyethack.*
import kotlin.system.exitProcess

fun main(args: Array<String>) {

    Game.play()
}

object Game {
    private val player = Player("zad")
    private var currentRoom: Room = TownSquare()

    init {
        println("Welcome, adventurer.")
    }

    private var worldMap = listOf(
        listOf(currentRoom, Room("Tavern"), Room("Back room")),
        listOf(Room("Long Corridor"), Room("Generic Room"))
    )

    private fun move(directionInput: String) =
        try {
            val direction = Direction.valueOf(directionInput.toUpperCase())
            val newPosition = direction.updateCoordinate(player.currentPosition)
            if (!newPosition.isInBounds) {
                throw IllegalStateException("$direction is out of bound.")
            }
            val newRoom = worldMap[newPosition.y][newPosition.x]
            player.currentPosition = newPosition
            currentRoom = newRoom
            "you move $direction to the ${newRoom.name}.\n${newRoom.load()}"
        } catch (e: Exception) {
            "Invalid direction $directionInput."
        }

    private fun Map(p: Coordinate): String {
        when (p) {
            Coordinate(0, 0) -> println("X O O\nO O")
            Coordinate(1, 0) -> println("O X O\nO O")
            Coordinate(2, 0) -> println("O O X\nO O")
            Coordinate(0, 1) -> println("O O O\nX O")
            Coordinate(1, 1) -> println("O O O\nO X")
            else -> println("You don't know where you are.")
        }
        return "Here is a map"
    }


    private fun ringMyBell(count: Int = 1): String {
        var printRing = ""
        printRing += if (player.currentPosition == Coordinate(0, 0)) {
            "Gwong. ".repeat(count)
        } else {
            "You can't do it here."
        }
        return printRing
    }

    private fun fight() = currentRoom.moster?.let {
        while (player.healthPoints > 0 && it.healthPoints > 0) {
            slay(it)
            Thread.sleep(1000)
        }
        "Combat complete."
    } ?: "There's nothing here to fight."

    private fun slay(monster: Monster) {
        println("${monster.name} did ${monster.attack(player)} damage!")
        println("${player.name} did ${player.attack(monster)} damage!")
        if (player.healthPoints <= 0) {
            println(">>>> You have been defeated! Thanks for playing. <<<<")
            exitProcess(0)
        }
        if (monster.healthPoints <= 0) {
            println(">>>> ${monster.name} has been defeated! <<<<")
            currentRoom.moster = null
        }
    }

    fun play() {
        while (true) {
            println(currentRoom.description())
            println(currentRoom.load())
            //состояние игрока
            printPlayerStatus(player)
            println(">Enter your command: ")
            val commandOne = readLine()
            if (commandOne?.toLowerCase() == "quit") {
                println("Goodbye.")
                break
            } else {
                println(GameInput(commandOne).processCommand())
            }
        }
    }

    private fun printPlayerStatus(player: Player) {
        println("(Aura: ${player.Aura()})" + "(Blessed: ${if (player.isBlessed) "Yes" else "No"})")
        println("${player.name} ${player.formatHealthStatus()}")
    }

    private class GameInput(arg: String?) {
        private val input = arg ?: ""
        val command = input.split(" ")[0]
        val argument = input.split(" ").getOrElse(1) { "" }

        fun processCommand() = when (command.toLowerCase()) {
            "move" -> move(argument)
            "map" -> Map(player.currentPosition)
            "fight" -> fight()
            "ring" -> ringMyBell(input.split(" ").getOrElse(1) { "1" }.toInt())
            else -> commandNotFound()
        }

        private fun commandNotFound() = "I'm not quite sure what you're trying to do!"
    }
}