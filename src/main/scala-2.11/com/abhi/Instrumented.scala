package com.abhi

import nl.grons.metrics.scala.InstrumentedBuilder
trait Instrumented extends InstrumentedBuilder {
   val metricRegistry = Boot.metricRegistry
}
