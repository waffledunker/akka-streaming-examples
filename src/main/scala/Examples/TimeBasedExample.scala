package com.example.waffledunker
package Examples

import MainRunner.system
import Runners.RunWrapper

import akka.Done
import akka.stream.scaladsl.Source

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt

class TimeBasedExample extends RunWrapper {

  val quickstartExample = new QuickstartExample

  override def run(): Future[Done] = {
    val testSource = Source(1 to 10)
    testSource
      .scan(BigInt(1))((acc, next) => acc * next)
      .zipWith(Source(0 to 10))((num, idx) => s"$idx! = $num")
      .grouped(2)
      .throttle(2, 500.millisecond)
      .runForeach(println)
  }

}
