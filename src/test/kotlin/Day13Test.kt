import Packet.Companion.parsePacket
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.test.Test

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
            Pair(5,3) to false,
            Pair(4,4) to null,
        ).map { (values, expected) ->
            val (first, second) = values
            dynamicTest("$first compared to $second is $expected ") {
                SinglePacket(first).shouldBeBefore(SinglePacket(second)) shouldBe expected
            }
        }

    @Test
    fun `packetList comparisons`() {
        SinglePacket(1).shouldBeBefore(PacketList(mutableListOf(SinglePacket(2)))) shouldBe true
        PacketList(mutableListOf(SinglePacket(1))).shouldBeBefore(SinglePacket(2)) shouldBe true

        "[9]".parsePacket().shouldBeBefore("[[8,7,6]]".parsePacket()) shouldBe false

        testInput.chunked(3).map { (first, second) ->
            first.parsePacket().shouldBeBefore(second.parsePacket())
        } shouldBe (listOf(true, true, false, true, false, true, false, false))
    }

    @TestFactory
    fun `part 1`() = listOf(
        dynamicTest("example") { day13Part1(testInput) shouldBe 13 },
        dynamicTest("answer") { day13Part1(input) shouldBe 6428 },
    )

    @TestFactory
    fun `part 2`() = listOf(
        dynamicTest("example") { day13Part2(testInput) shouldBe 140 },
        dynamicTest("answer") { day13Part2(input) shouldBe 22464 },
    )
}
