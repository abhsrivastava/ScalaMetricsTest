package com.abhi

import java.util.concurrent._
import java.util.concurrent.atomic.AtomicInteger

import com.twitter.concurrent.NamedPoolThreadFactory

import scala.concurrent.ExecutionContext

object MyExecutionContext {
   val ioThreadPool = Executors.newCachedThreadPool(
      new ThreadFactory {
         private val counter = new AtomicInteger(0)

         def newThread(r: Runnable) = {
            val thread = new Thread(r)
            thread.setName("abhishek-io-thread-" + counter.getAndIncrement.toString)
            thread.setDaemon(true)
            thread
         }
      })

   val pool = new ThreadPoolExecutor(20, 100, 100, TimeUnit.SECONDS, new LinkedBlockingQueue[Runnable](),
      new NamedPoolThreadFactory("endpoint-thread", makeDaemons = true))

   implicit val myec1 : ExecutionContext = ExecutionContext.fromExecutorService(pool)
   implicit val myec2 : ExecutionContext = ExecutionContext.fromExecutorService(ioThreadPool)
}
