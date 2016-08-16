package com.abhi

/**
  * Created by abhsrivastava on 8/15/16.
  */

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class LoadTest extends Simulation {
   val httpConf = http
      .baseURL("http://localhost:8999")
      .acceptHeader("application/json")
      .doNotTrackHeader("1")
      .acceptLanguageHeader("en-US,en;q=0.5")
      .acceptEncodingHeader("gzip, deflate")
      .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

   val scn = scenario("BasicSimulation")
      .exec(http("request_1")
      .get("/path1"))
      .pause(5)

   setUp(
      scn.inject(atOnceUsers(10))
   ).protocols(httpConf)
}
