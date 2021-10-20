package com.kkk.nyethack

fun main(args: Array<String>) {
    var swordsJuggling: Int? = null
    val isJugglingProf = (1..3).shuffled().last() == 3
    if (isJugglingProf) {
        swordsJuggling = 2
    }
    try {
        proficiencyCheck(swordsJuggling)
        swordsJuggling = swordsJuggling!!.plus(1)
    } catch (e: Exception) {
        println(e)
    }
    println("you juggle $swordsJuggling swords")
}

fun proficiencyCheck(swordsjuggling: Int?) {
    checkNotNull(swordsjuggling, { "com.kkk.nyethack.Player cannot juggle swords" })
}

class unskilledSwordJugglerException :
    IllegalStateException("com.kkk.nyethack.Player cannot juggle swords")

