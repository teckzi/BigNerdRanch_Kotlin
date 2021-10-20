package com.kkk.nyethack

open class Room(val name: String) {
    protected open val DangerLvl = 5

    //open fun description() = "Room: $name \nDanger level: $DangerLvl"
    var moster: Monster? = Goblin()
    fun description() = "Room: $name\n" +
            "Danger level: $DangerLvl\n" +
            "Creature: ${moster?.description ?: "none"}"

    open fun load() = "Nothing much to see here"

}

open class TownSquare : Room("Town Square") {
    override val DangerLvl: Int
        get() = super.DangerLvl - 3
    private val bellSound = "GWONG"
    override fun load() = "The villagers rally and cheer as you enter!\n${ringBell()}"
    fun ringBell() = "The bell tower announces your arrival. $bellSound"

}

val abandonedTownSquare = object : TownSquare() {
    override fun load() = "You anticipate applause, but no one is here..."
}