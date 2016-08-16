package com.abhi

import akka.actor.{ActorSystem, Props}
import akka.util.Timeout
import akka.io.IO
import spray.can.Http
import akka.pattern.ask
import scala.concurrent.duration._
import com.codahale.metrics.MetricRegistry

object Boot extends App {
   val metricRegistry = new MetricRegistry()
   implicit val system = ActorSystem("spray-scala-metrics")
   val service = system.actorOf(Props[ServiceActor], "foobar")
   implicit val timeout = Timeout(.5 seconds)
   IO(Http) ? Http.Bind(service, interface="10.15.53.75", port=8999)
   //IO(Http) ? Http.Bind(service, interface="localhost", port=8999)
}