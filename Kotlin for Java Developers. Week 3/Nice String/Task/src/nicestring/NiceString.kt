package nicestring

fun String.isNice(): Boolean {
    val doesNotContainsEvilSubstrings = if (this.contains(Regex("bu|ba|be"))) 0 else 1

    val containsThreeVowels = if (this.filter { it in "aeiou" }.length >= 3)  1 else 0

    val containsDoubleLetters = if (this.windowed(2).any {it[0] == it[1]}) 1 else 0

    return containsDoubleLetters + doesNotContainsEvilSubstrings + containsThreeVowels >= 2


}