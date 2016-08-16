package com.abhi

import akka.actor.Actor
import spray.http.MediaTypes._
import spray.json.DefaultJsonProtocol
import spray.routing.HttpService

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


case class Person (name: String, firstName: String, age: Long)

object MyJsonProtocol extends DefaultJsonProtocol {
   implicit val personFormat = jsonFormat3(Person)
}

class ServiceActor extends Actor with HttpService with Instrumented {
   import spray.httpx.SprayJsonSupport._
   private[this] val loading = metrics.timer("loading")
   import MyJsonProtocol._
   def actorRefFactory = context
   def receive = runRoute(noInstrumentation ~ instrumentation)
   val noInstrumentation = {
      path("path1") {
         get {
            respondWithMediaType(`application/json`) {
               complete {
                  Future {
                     Thread.sleep(1000) // fuck the server
                     println("going to return object")
                     Person("Bob", "Type A", System.currentTimeMillis())
                  }
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
                  loading.timeFuture {
                     Future {
                        Thread.sleep(1000) // fuck the server
                        println("going to return instrumented object")
                        Person("Bob", "Type A", System.currentTimeMillis())
                     }
                  }
               }
            }
         }
      }
   }
}