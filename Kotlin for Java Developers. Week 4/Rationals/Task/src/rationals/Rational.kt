package rationals

import java.lang.IllegalArgumentException
import java.math.BigInteger

private val zero: BigInteger = BigInteger.valueOf(0)
private val one: BigInteger = BigInteger.valueOf(1)

class Rational(val numerator: BigInteger, val denominator: BigInteger): Comparable<Rational> {

    init { if (denominator == zero) throw IllegalArgumentException() }

    operator fun plus(other: Rational): Rational = when {
        this.numerator == zero -> other
        other.numerator == zero -> this
        else -> this.numerator * other.denominator + other.numerator * this.denominator divBy this.denominator * other.denominator
    }
    operator fun minus(other: Rational): Rational = this + -other
    operator fun times(other: Rational): Rational = this.numerator * other.numerator divBy this.denominator * other.denominator
    operator fun div(other: Rational): Rational = when (other.numerator) {
        zero -> zero divBy this.denominator
        else -> this * (other.denominator divBy other.numerator)
    }
    operator fun rangeTo(other: Rational): ClosedRange<Rational> = RangeRational(this, other)
    override operator fun compareTo(other: Rational): Int {
        return (numerator * other.denominator).compareTo(other.numerator * denominator)
    }
    override fun toString(): String {
        val tmp = minusToNumerator()
        val gcd = tmp.numerator.gcd(tmp.denominator)
        var newNumerator = tmp.numerator / gcd
        var newDenominator = tmp.denominator / gcd
        if (newDenominator == one) return "$newNumerator"
        return "$newNumerator/$newDenominator"
    }
    operator fun unaryMinus(): Rational = -this.numerator divBy this.denominator
    override fun equals(other:Any?): Boolean = when (other) {
        is Rational -> this.numerator * other.denominator == this.denominator * other.numerator
        else -> false
    }

    private fun minusToNumerator(): Rational =
        if (this.denominator > zero) {
            this
        } else {
            - this.numerator divBy - this.denominator
        }

}

class RangeRational(override val start: Rational, override val endInclusive: Rational) : ClosedRange<Rational>


infix fun BigInteger.divBy(other: BigInteger): Rational = Rational(this, other)

infix fun Long.divBy(other: Long): Rational = BigInteger.valueOf(this) divBy BigInteger.valueOf(other)

infix fun Int.divBy(other: Int): Rational = this.toBigInteger() divBy other.toBigInteger()

fun String.toRational(): Rational {
    val splitList = this.split("/")
    return when (splitList.size) {
        1 -> toBigInteger() divBy one
        2 -> splitList[0].toBigInteger() divBy splitList[1].toBigInteger()
        else -> throw IllegalArgumentException()
    }
}

fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}