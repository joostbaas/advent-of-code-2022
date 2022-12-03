package benchmark

import day01Part1
import day01Part2
import day02Part1
import day02Part2
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
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
open class TestBenchmark {

    private val input1 = readInput("Day01")
    private val input2 = readInput("Day02")

    @Benchmark
    fun day01_1(): Int {
        return day01Part1(input1)
    }

    @Benchmark
    fun day01_2(): Int {
        return day01Part2(input1)
    }

    @Benchmark
    fun day02_1(): Int {
        return day02Part1(input2)
    }

    @Benchmark
    fun day02_2(): Int {
        return day02Part2(input2)
    }
}
