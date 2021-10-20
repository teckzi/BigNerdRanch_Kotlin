package com.kkk.nyethack

import extensions.randomK
import java.io.File


const val TAVERN_NAME = "Taernyl's Folly"
val patronList = mutableListOf("Eli", "Mordoc", "Frodo", "Bilbo")
val lastname = listOf("Ironfoot", "Fernsworth", "Baggins")
val menuList = File("src\\com\\kkk\\nyethack\\data\\menu")
    .readText()
    .split("\n")
val patronGold = mutableMapOf<String, Double>()
val kickList = mutableSetOf<String>()

fun main() {

    val uniquePatrons = mutableSetOf<String>()
    (0..4).forEach {
        val firstName = patronList.randomK()
        val secondName = lastname.randomK()
        val name = "$firstName $secondName"
        uniquePatrons += name
    }
    uniquePatrons.forEach {
        patronGold[it] = 6.0
    }
    println(uniquePatrons)
    var ordercount = 0
    var uniqInd = 0
    for (index in uniquePatrons) {
        while (ordercount <= 4) {
            placeOrder(uniquePatrons.randomK(), menuList.randomK())
            ordercount++
            uniqInd++
        }
    }
    for (i in kickList) {
        println("$i is kicked from tavern")
        patronGold.remove(i)
        uniquePatrons.remove(i)
    }
    displayPatronBalances()


}

fun placeOrder(patronName: String, menuData: String) {
    val indexofApost = TAVERN_NAME.indexOf('\'')
    val tavernmMaster = TAVERN_NAME.substring(0 until indexofApost)
    println("$patronName speaks with $tavernmMaster about their order.")

    val (type, name, price) = menuData.split(',')
    println("$patronName buys a $name ($type) for $price")
    performPurchase(price.toDouble(), patronName)
    if (name == "Dragon's Breath") {
        println("$patronName exclaims: " + "$name ZAEBIS".DragonSpeak())
    } else {
        println("$patronName says:Thanks for the $name")
    }

}

private fun String.DragonSpeak() =
    this.replace(Regex("[aeiouAEIOU]")) {
        when (it.value) {
            "a", "A" -> "4"
            "e", "E" -> "3"
            "i", "I" -> "1"
            "o", "O" -> "0"
            "u", "U" -> "|_|"
            else -> it.value
        }
    }

fun performPurchase(price: Double, patronName: String) {
    val totalPurse = patronGold.getValue(patronName)
    patronGold[patronName] = totalPurse - price
    if (patronGold.getValue(patronName) <= 0) kickList += patronName
}

private fun displayPatronBalances() {
    patronGold.forEach { patron, balance ->
        println("$patron, balance: ${"%.2f".format(balance)}")
    }
}





