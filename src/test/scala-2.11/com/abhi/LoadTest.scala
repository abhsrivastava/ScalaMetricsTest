package com.abhi

/**
  * Created by abhsrivastava on 8/15/16.
  */

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class LoadTest1 extends Simulation {
   val httpConf = http
      .baseURL("http://10.15.53.75:8999")
      .acceptHeader("application/json")
      .doNotTrackHeader("1")
      .acceptLanguageHeader("en-US,en;q=0.5")
      .acceptEncodingHeader("gzip, deflate")
      .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

   val scn1 = scenario("BasicSimulation1")
         .during(60 seconds) {
            exec(
               http("request_1")
                  .get("/path1")
                  .check(status.is(200)))
                  .pause(5)
         }

   setUp(
      scn1.inject(atOnceUsers(50))
   ).protocols(httpConf)
}

class LoadTest2 extends Simulation {
   val httpConf = http
      .baseURL("http://10.15.53.75:8999")
      .acceptHeader("application/json")
      .doNotTrackHeader("1")
      .acceptLanguageHeader("en-US,en;q=0.5")
      .acceptEncodingHeader("gzip, deflate")
      .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

   val scn2 = scenario("BasicSimulation2")
      .during(60 seconds) {
         exec(http("request_2")
            .get("/path2")
            .check(status.is(200)))
            .pause(5)
      }

   setUp(
      scn2.inject(atOnceUsers(50))
   ).protocols(httpConf)
}
