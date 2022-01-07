package rationals

import java.lang.IllegalArgumentException
import java.math.BigInteger
import java.math.BigInteger.ZERO
import java.math.BigInteger.ONE

@Suppress("DataClassPrivateConstructor")
data class Rational
    private constructor(val numerator: BigInteger, val denominator: BigInteger):Comparable<Rational> {
        companion object {
            fun from(numerator: BigInteger, denominator: BigInteger): Rational {
                if (denominator == ZERO) throw IllegalArgumentException()
                val sign = denominator.signum().toBigInteger()
                val gcd = numerator.gcd(denominator)
                return Rational(numerator * sign / gcd, denominator * sign / gcd)
            }
        }

    operator fun plus(other: Rational): Rational = when {
        this.numerator == ZERO -> other
        other.numerator == ZERO -> this
        else -> Rational.from(
            this.numerator * other.denominator + other.numerator * this.denominator,
            this.denominator * other.denominator
        )
    }
    operator fun minus(other: Rational): Rational = this + -other
    operator fun times(other: Rational): Rational = Rational.from(
        this.numerator * other.numerator,
        this.denominator * other.denominator
    )
    operator fun div(other: Rational): Rational = when (other.numerator) {
        ZERO -> throw IllegalArgumentException()
        else -> this * Rational.from(other.denominator, other.numerator)
    }
    operator fun rangeTo(other: Rational): ClosedRange<Rational> = RangeRational(this, other)
    override operator fun compareTo(other: Rational): Int {
        return (numerator * other.denominator).compareTo(other.numerator * denominator)
    }
    override fun toString(): String {
        if (denominator == ONE) return "$numerator"
        return "$numerator/$denominator"
    }
    operator fun unaryMinus(): Rational = Rational.from(-this.numerator, this.denominator)
}

class RangeRational(override val start: Rational, override val endInclusive: Rational) : ClosedRange<Rational>


infix fun BigInteger.divBy(other: BigInteger): Rational = Rational.from(this, other)

infix fun Long.divBy(other: Long): Rational = BigInteger.valueOf(this) divBy BigInteger.valueOf(other)

infix fun Int.divBy(other: Int): Rational = this.toBigInteger() divBy other.toBigInteger()

fun String.toRational(): Rational {
    val splitList = this.split("/")
    return when (splitList.size) {
        1 -> Rational.from(toBigInteger(), ONE)
        2 -> Rational.from(splitList[0].toBigInteger(), splitList[1].toBigInteger())
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