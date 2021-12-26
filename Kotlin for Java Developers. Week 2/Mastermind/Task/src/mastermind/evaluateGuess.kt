package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun String.removeAt(index: Int): String {
    return this.substring(0, index) + "_" + this.substring(index + 1)
}

fun evaluateGuess(secret: String, guess: String): Evaluation {
    var _secret = secret
    var _guess = guess
    var right = 0
    var wrong = 0

    for ((i, c) in guess.withIndex()) {
        if (c == _secret[i]) {
            right += 1
            _secret = _secret.removeAt(i)
            _guess = _guess.removeAt(i)
        }
        println("iteration $i rights $right guess $guess working string $_secret")
    }
    _guess = _guess.replace("_", "")
    for ((i, c) in _guess.withIndex()) {
        val index = _secret.indexOfFirst { it == c }
        if (index != -1) {
            wrong += 1
            _secret = _secret.removeAt(index)
        }
        println("iteration $i  wrongs $wrong guess $_guess working string $_secret")
    }
    return Evaluation(right, wrong)
}
