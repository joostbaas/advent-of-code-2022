import Packet.Companion.parsePacket
import kotlin.math.max

sealed interface Packet : Comparable<Packet> {
    companion object {
        fun String.parsePacket(): Packet {
            val firstPacketListContent = mutableListOf<Packet>()
            val stack: MutableList<PacketList> = mutableListOf(PacketList(firstPacketListContent))
            var remainder = this
            while (remainder.isNotEmpty()) {
                val numberConsumed: Int = when (remainder[0]) {
                    in ('0'..'9') -> {
                        val number = remainder.takeWhile { it.isDigit() }
                        stack.last().add(SinglePacket(number.toInt()))
                        number.length
                    }
                    '[' -> {
                        stack.add(PacketList(mutableListOf()))
                        1
                    }
                    ']' -> {
                        val finishedPacketList = stack.removeLast()
                        stack.last().add(finishedPacketList)
                        1
                    }
                    ',' -> 1
                    else -> throw IllegalArgumentException("should only be numbers, commas and brackets, but found \"${remainder[0]}\"")
                }
                remainder = remainder.drop(numberConsumed)
            }
            return stack.first().content.first()
        }
    }

    override fun compareTo(other: Packet): Int =
        when (this.shouldBeBefore(other)) {
            true -> -1
            null -> 0
            false -> 1
        }


    fun shouldBeBefore(other: Packet): Boolean? {
        return when {
            this is SinglePacket && other is SinglePacket ->
                when {
                    this.content < other.content -> true
                    this.content == other.content -> null
                    else -> false
                }
            this is PacketList && other is PacketList -> {
                for (i in (0..max(this.content.size, other.content.size))) {
                    val thisChild = content.getOrNull(i)
                    val otherChild = other.content.getOrNull(i)
                    when {
                        thisChild == null && otherChild != null -> return true
                        thisChild == null && otherChild == null -> return null
                        otherChild == null -> return false
                        else -> {
                            if (thisChild!!.shouldBeBefore(otherChild) == true) return true
                            if (thisChild.shouldBeBefore(otherChild) == false) return false
                        }
                    }
                }
                null
            }
            this is SinglePacket && other is PacketList -> return PacketList(mutableListOf(this)).shouldBeBefore(other)
            this is PacketList && other is SinglePacket -> return this.shouldBeBefore(PacketList(mutableListOf(other)))
            else -> throw IllegalArgumentException()
        }
    }
}

data class SinglePacket(val content: Int) : Packet {
    override fun toString(): String {
        return content.toString()
    }
}

data class PacketList(val content: MutableList<Packet>) : Packet {
    fun add(packet: Packet) {
        this.content.add(packet)
    }

    override fun toString(): String {
        return content.joinToString(separator = ",", prefix = "[", postfix = "]")
    }
}

fun day13Part1(input: List<String>): Int =
    input.asSequence().chunked(3).mapIndexed { i, lines ->
        val (first, second) = lines
        i to first.parsePacket().shouldBeBefore(second.parsePacket())
    }.filter { it.second == true }
        .map { it.first + 1 }
        .sum()

fun day13Part2(input: List<String>): Int {
    val decoderPacket1 = "[[2]]".parsePacket()
    val decoderPacket2 = "[[6]]".parsePacket()
    val allPacketsSorted = input.filterNot { it.isEmpty() }
        .map { it.parsePacket() }
        .plus(listOf(decoderPacket1, decoderPacket2))
        .sorted()
    val indexOfdecoderPacket1 = allPacketsSorted.indexOf("[[2]]".parsePacket()) + 1
    val indexOfdecoderPacket2 = allPacketsSorted.indexOf("[[6]]".parsePacket()) + 1
    return indexOfdecoderPacket1 * indexOfdecoderPacket2
}

