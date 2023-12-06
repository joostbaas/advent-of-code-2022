import Packet.Companion.parsePacket
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

internal class Day13Test {

    private val testInput = readInput("Day13_test")
    private val input = readInput("Day13")

    @TestFactory
    fun parsing() =
        listOf(
            "5" to SinglePacket(5),
            "10" to SinglePacket(10),
            "[5]" to PacketList(mutableListOf(SinglePacket(5))),
            "[1,[1,2],3,1,1]" to PacketList(
                mutableListOf(
                    SinglePacket(1),
                    PacketList(
                        mutableListOf(SinglePacket(1), SinglePacket(2))
                    ),
                    SinglePacket(3),
                    SinglePacket(1),
                    SinglePacket(1),
                )
            ),
        ).map { (input, expected) ->
            dynamicTest(input) {
                input.parsePacket() shouldBe expected
            }
        }

    @TestFactory
    fun `SinglePacket comparisons`() =
        listOf(
            Pair(3, 5) to true,
            Pair(5, 3) to false,
            Pair(4, 4) to null,
        ).map { (packets, expected) ->
            val (first, second) = packets
            dynamicTest("$first compared to $second is $expected ") {
                SinglePacket(first).shouldBeBefore(SinglePacket(second)) shouldBe expected
            }
        }

    @TestFactory
    fun `packetList comparisons`() =
        listOf(
            Pair("1", "[2]") to true,
            Pair("[9]", "[[8,7,6]]") to false,
        ).map { (packets, expected) ->
            val (first, second) = packets
            dynamicTest("\"$first compared to $second is $expected \"") {
                first.parsePacket().shouldBeBefore(second.parsePacket()) shouldBe expected
            }

        }

    @TestFactory
    fun `part 1`() = listOf(
        dynamicTest("example") { day13Part1(testInput) shouldBe 13 },
        dynamicTest("answer") { day13Part1(input) shouldBe 6656 },
    )

    @TestFactory
    fun `part 2`() = listOf(
        dynamicTest("example") { day13Part2(testInput) shouldBe 140 },
        dynamicTest("answer") { day13Part2(input) shouldBe 19716 },
    )
}
