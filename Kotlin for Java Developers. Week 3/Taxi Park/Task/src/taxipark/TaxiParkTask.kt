package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver>  {
    val nonFakeDrivers = this.trips.map {it.driver}
    return this.allDrivers.minus(nonFakeDrivers)
}


/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
    if (minTrips == 0)
        this.allPassengers
    else
        this.trips
            .flatMap { trip -> trip.passengers.map {it to trip} }
            .groupBy { it.first }
            .filter { (_, trips)  -> trips.size >= minTrips}
            .keys


/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
    this.trips
        .filter {it.driver == driver}
        .flatMap { trip -> trip.passengers.map {it to trip} }
        .groupBy { it.first }
        .filter { (_, trips)  -> trips.size > 1}
        .keys

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
    trips
        .flatMap { trip -> trip.passengers.map {it to trip} }
        .groupBy { it.first }
        .mapValues { entry -> entry.value.partition {it.second.discount != null} }
        .filterValues { it.first.size > it.second.size }
        .keys



/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    return trips
        .groupBy { trip ->
            val startOfRange = trip.duration / 10 * 10
            startOfRange..(startOfRange + 9)
         }
        .maxBy { it.value.size }
        ?.key
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if (trips.isEmpty()) return false

    fun find20PercentOfDrivers():Int? {
        val twentyPerCent = this.allDrivers.size.toDouble() * 20 / 100
        return if (twentyPerCent.toString().indexOf(".") != -1) twentyPerCent.toInt() else null
    }
    val twentyPercent = find20PercentOfDrivers() ?: return false

    val drivers = allDrivers.take(twentyPercent)

    val allIncome = trips.sumByDouble { it.cost }

    val incomeForDrivers = trips
        .filter { it.driver in drivers }
        .sumByDouble { it.cost }

    return incomeForDrivers >= allIncome * 80 / 100
}