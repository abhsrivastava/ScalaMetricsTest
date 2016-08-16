package com.abhi

import java.util.concurrent._
import akka.actor.Actor
import spray.http.MediaTypes._
import spray.json.DefaultJsonProtocol
import spray.routing.HttpService
import scala.concurrent.{Future}

case class Person (name: String, firstName: String, age: Long)

object MyJsonProtocol extends DefaultJsonProtocol {
   implicit val personFormat = jsonFormat3(Person)
}

class ServiceActor extends Actor with HttpService with Instrumented {
   private[this] val loading = metrics.timer("loading")
   import spray.httpx.SprayJsonSupport._
   import MyJsonProtocol._
   def actorRefFactory = context
   def receive = runRoute(noInstrumentation ~ instrumentation)
   val noInstrumentation = {
      path("path1") {
         get {
            respondWithMediaType(`application/json`) {
               complete {
                  Future({
                     Thread.sleep(300) // cause delay
                     Person("Bob", "Type A", System.currentTimeMillis())
                  })(MyExecutionContext.myec1)
               }
            }
         }
      }
   }

   val instrumentation = {
      path("path2") {
         get {
            respondWithMediaType(`application/json`) {
               complete {
                  loading.timeFuture ({
                     Future ({
                        Thread.sleep(300) // cause delay
                        Person("Bob", "Type A", System.currentTimeMillis())
                     })(MyExecutionContext.myec1)
                  })(MyExecutionContext.myec1)
               }
            }
         }
      }
   }

   val newPoolInstrumentataion = {
      path("path3") {
         get {
            respondWithMediaType(`application/json`) {
               complete {
                  loading.timeFuture ({
                     Future ({
                        Thread.sleep(300) // cause delay
                        Person("Bob", "Type A", System.currentTimeMillis())
                     })(MyExecutionContext.myec1)
                  })(MyExecutionContext.myec2)
               }
            }
         }
      }
   }
}