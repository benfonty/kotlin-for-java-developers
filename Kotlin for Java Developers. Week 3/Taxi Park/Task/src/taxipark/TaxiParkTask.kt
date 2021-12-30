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
                // TODO replace the two following methods by an alternative to groupby
            .flatMap { trip -> trip.passengers.map {trip to it } }
            .groupBy { it.second }
            .filter { (_, trips)  -> trips.size >= minTrips}
            .keys


/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
    this.trips
        .filter {it.driver == driver}
        .flatMap { trip -> trip.passengers.map {trip.driver to it } }
        .groupBy { it.second }
        .filter { (_, trips)  -> trips.size > 1}
        .keys

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
        TODO()

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
    val trips80PercentOfTheIncome: Set<Trip> = TODO()
    val drivers80PercentOfIncome = trips80PercentOfTheIncome
        .map { it.driver}
        .toSet()
    return drivers80PercentOfIncome.size / allDrivers.size * 100 == 20
}