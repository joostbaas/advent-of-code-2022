package day15

import kotlin.math.abs

fun String.parse(): SensorReading {
    val regex = """Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)"""
    val (x1, y1, x2, y2) = regex.toRegex().matchEntire(this)!!.destructured
    return SensorReading(Point(x1.toInt(), y1.toInt()), Point(x2.toInt(), y2.toInt()))
}

data class Point(
    val x: Int,
    val y: Int,
) {
    fun distanceTo(other: Point) = abs(x - other.x) + abs(y - other.y)
}

data class SensorReading(val sensor: Point, val beacon: Point) {
    val distanceToBeacon = sensor.distanceTo(beacon)
    fun rulesOutPossibilityOfHereBeingABeacon(point: Point): Boolean {
        return sensor.distanceTo(point) <= distanceToBeacon
    }
}


fun day15Part1(input: List<String>, y: Int): Int =
    input.map {
        it.parse()
    }.let { sensorReadings ->
        val allPoints = sensorReadings.flatMap { listOf(it.sensor, it.beacon) }
        val minY = allPoints.minOf { it.y }
        val minX = allPoints.minOf { it.x }
        val maxY = allPoints.maxOf { it.y }
        val maxX = allPoints.maxOf { it.x }
        // this could be simpler/more efficient: just take the max distance between a sensor and it's beacon for instance.
        val maximumDistanceToLookFurther = Point(minX, minY).distanceTo(Point(maxX, maxY))
        val start = minX - maximumDistanceToLookFurther
        val end = maxX + maximumDistanceToLookFurther

        val whichPointCannotContainABeacon = (start..end).map { Point(it, y) }
            .count { point ->
                sensorReadings.any { reading -> reading.rulesOutPossibilityOfHereBeingABeacon(point) }
            }
        val beaconsOnThatLine = sensorReadings.map { it.beacon }.toSet().count { it.y == y }
        whichPointCannotContainABeacon - beaconsOnThatLine
    }

fun day15Part2(input: List<String>, end: Int): Long = input.map {
    it.parse()
}.let { sensorReadings ->
    val allSensorsAndBeacons = sensorReadings.flatMap { listOf(it.sensor, it.beacon) }

    // check all the points distance+1 from a sensor, it must be there somewhere
    val locationsWhereThereCouldBeABeacon = (0..end).flatMap { y ->
        val relevantSensors = sensorReadings.filter {
            val distanceToThisLine = abs(y - it.sensor.y)
            distanceToThisLine < it.distanceToBeacon
        }
        relevantSensors.flatMap { sensorReading ->
            val distanceToThisLine = abs(y - sensorReading.sensor.y)
            val leftX = sensorReading.sensor.x - (sensorReading.distanceToBeacon - distanceToThisLine) - 1
            val rightX = sensorReading.sensor.x + (sensorReading.distanceToBeacon - distanceToThisLine) + 1
            listOf(Point(leftX, y), Point(rightX, y))
                .filter { candidate -> candidate.x in (0..end) && candidate.y in (0..end) }
                .filter { candidate -> candidate !in allSensorsAndBeacons }
                .filter { candidate ->
                    sensorReadings.none { reading -> reading.rulesOutPossibilityOfHereBeingABeacon(candidate) }
                }
        }
    }

    return locationsWhereThereCouldBeABeacon[0].let { it.x.toLong() * 4_000_000L + it.y }
}
