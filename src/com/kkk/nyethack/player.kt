package com.kkk.nyethack

import extensions.randomK
import java.io.File

class Player(
    _name: String, override var healthPoints: Int = 100,
    var isBlessed: Boolean,
    private var isImmortal: Boolean
) : Fightable {

    override val diceCount: Int = 3
    override val diceSides: Int = 6
    override fun attack(opponent: Fightable): Int {
        val damageDealt = if (isBlessed) {
            damageRoll * 2
        } else {
            damageRoll
        }
        opponent.healthPoints -= damageDealt
        return damageDealt
    }

    var name = _name
        get() = "${field.capitalize()} of $homeTown"
        private set(value) {
            field = value.trim()
        }
    private val homeTown by lazy { selectHomeTown() }
    var currentPosition = Coordinate(0, 0)

    init {
        require(healthPoints > 0, { "HealthPoints must be greater than zero." })
        require(_name.isNotBlank()) { "Player must have a name." }
    }

    constructor(name: String) : this(name, isBlessed = true, isImmortal = false) {
        if (name.toLowerCase() == "zad") healthPoints = 300
    }


    // НУЖНО ПРОВЕРИТЬ!!!!

    private fun selectHomeTown() = File("src\\com\\kkk\\nyethack\\data\\towns")
        .readText()
        .split(",")
        .randomK()


    private fun Karma(hhp: Int): String {
        val karma = (Math.pow(Math.random(), (110 - hhp) / 100.0) * 20).toInt()
        var auracolord = ""
        when (karma) {
            in 0..5 -> auracolord = "red"
            in 6..10 -> auracolord = "orange"
            in 11..15 -> auracolord = "purple"
            in 16..20 -> auracolord = "green"
        }
        return auracolord
    }

    fun castFireball(nunFireballs: Int = 2) {
        val debuff = nunFireballs
        println("A glass of fireball springs into existence.x$nunFireballs")
        var x = ""
        when (debuff) {
            in 1..10 -> x = "Tipsy"
            in 11..20 -> x = "Sloshed"
            in 21..30 -> x = "Soused"
            in 31..40 -> x = "Stewed"
            in 41..50 -> x = "t0aSt3d"
        }
        println("You are $x")
    }

    fun formatHealthStatus() = when (healthPoints) {
        100 -> "is in excellent condition!(HP:$healthPoints)"
        in 70..99 -> if (isBlessed) {
            "has a few scratches and blessed(HP:$healthPoints)"
        } else {
            "has a few scratches(HP:$healthPoints)"
        }
        in 50..69 -> "has some minor wounds(HP:$healthPoints)"
        in 1..49 -> "looks pretty hurt(HP:$healthPoints)"
        0 -> "is dead(HP:$healthPoints)"
        else -> "You're too FAAAAT!!! (HP:$healthPoints)"
    }


    fun Aura(): String {
        val auraVisible = isBlessed && healthPoints >= 50 || isImmortal
        return if (auraVisible) "aura is ${Karma(healthPoints)}" else "There is no aura"
    }

}


