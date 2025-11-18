package com.jeyo.pilotestarithmetic

enum class Signs(val str: String) {
    ADD("+"),
    SUB("-")
}

class Equation(private val length: Int) {
    private val min: Int = 1
    private val max: Int = 100
    var eq: MutableList<String> = mutableListOf()

    init {
        this.generate()
    }

    /*
        length: total of number in the equation
     */
    fun generate() {
        this.reset()
        for (i in 0 until length) {
            val newNumber = (min..max).random()
            eq.add(newNumber.toString())
            eq.add(Signs.entries.random().str)
        }
        // Remove last useless sign
        eq.removeAt(eq.lastIndex)
    }

    fun solve(): Int {
        if (eq.isEmpty()) {
            return 0
        }

        var result = eq[0].toInt()
        var i = 1
        while (i < eq.size) {
            val nextNumber = eq[i+1].toInt()
            when (eq[i]) {
                Signs.ADD.str -> {
                    result += nextNumber
                }
                Signs.SUB.str -> {
                    result -= nextNumber
                }
            }
            i += 2
        }

        return result
    }

    override fun toString(): String {
        return eq.joinToString(" ")
    }

    private fun reset() {
        eq.clear()
    }
}
