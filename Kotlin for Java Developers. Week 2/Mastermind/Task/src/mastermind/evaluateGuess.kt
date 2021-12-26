package mastermind

import java.util.concurrent.atomic.AtomicReference

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun AtomicReference<String>.blancCharAt(index: Int) {
    this.run {
        set(get().substring(0, index) + "_" + get().substring(index + 1))
    }
}

fun AtomicReference<String>.removeAllBlancs() {
    this.run {
        set(get().replace("_", ""))
    }
}

fun calculateRights(guess: AtomicReference<String>, secret: AtomicReference<String>): Int {
    var right = 0
    for ((i, c) in guess.get().withIndex()) {
        if (c == secret.get()[i]) {
            right += 1
            guess.blancCharAt(i)
            secret.blancCharAt(i)
        }
        println("iteration $i rights $right guess ${guess.get()} working string ${secret.get()}")
    }
    return right
}

fun calculateWrongs(guess: AtomicReference<String>, secret: AtomicReference<String>): Int {
    var wrong = 0
    guess.removeAllBlancs()
    for ((i, c) in guess.get().withIndex()) {
        val index = secret.get().indexOfFirst { it == c }
        if (index != -1) {
            wrong += 1
            secret.blancCharAt(index)
        }
        println("iteration $i  wrongs $wrong guess ${guess.get()} working string ${secret.get()}")
    }
    return wrong
}

fun evaluateGuess(secret: String, guess: String): Evaluation {
    val guess = AtomicReference(guess)
    val secret = AtomicReference(secret)

    var right = calculateRights(guess, secret)
    var wrong = calculateWrongs(guess, secret)

    return Evaluation(right, wrong)
}
