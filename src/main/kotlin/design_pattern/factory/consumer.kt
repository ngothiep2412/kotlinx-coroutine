package design_pattern.factory

import kotlinx.coroutines.channels.Channel

fun main() {
    Shape("CIRCLE").draw()
    Shape("SQUARE").draw()
    Shape("RECTANGLE").draw()

}