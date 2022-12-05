package benchmark

import day01Part1
import day01Part2
import day02Part1
import day02Part2
import day03Part1
import day03Part2
import day04Part1
import day04Part2
import kotlinx.benchmark.Scope
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Measurement
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.Warmup
import readInput
import java.util.concurrent.TimeUnit


@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 0)
@Measurement(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
open class TestBenchmark {

    private val input1 = readInput("Day01")
    private val input2 = readInput("Day02")
    private val input3 = readInput("Day03")
    private val input4 = readInput("Day04")

    @Benchmark fun day01_1(): Int = day01Part1(input1)
    @Benchmark fun day01_2(): Int = day01Part2(input1)
    @Benchmark fun day02_1(): Int = day02Part1(input2)
    @Benchmark fun day02_2(): Int = day02Part2(input2)
    @Benchmark fun day03_1(): Int = day03Part1(input3)
    @Benchmark fun day03_2(): Int = day03Part2(input3)
    @Benchmark fun day04_1(): Int = day04Part1(input4)
    @Benchmark fun day04_2(): Int = day04Part2(input4)
}
