package com.abhi

import java.util.concurrent._

import akka.actor.Actor
import com.codahale.metrics.ScheduledReporter.NamedThreadFactory
import spray.http.MediaTypes._
import spray.json.DefaultJsonProtocol
import spray.routing.HttpService

import scala.concurrent.{ExecutionContext, Future}


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

   implicit val endpointExecutor = {
      val pool = new ThreadPoolExecutor(30, 100, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue[Runnable](), new NamedThreadFactory("endpoint-executor"))

      ExecutionContext.fromExecutorService(pool)
   }

   val noInstrumentation = {
      path("path1") {
         get {
            respondWithMediaType(`application/json`) {
               complete {
                  Future {
                     Thread.sleep(1000) // cause delay
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
                        Thread.sleep(1000) // cause delay
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
