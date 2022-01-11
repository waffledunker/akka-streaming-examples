package com.example.waffledunker
package Examples

import Runners.RunWrapper

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Sink, Source}
import akka.{Done, NotUsed}

import scala.concurrent.Future

class QuickstartExample extends RunWrapper {
  implicit val system: ActorSystem = ActorSystem("QuickStart")

  val source: Source[Int, NotUsed] = Source(1 to 10)
  val factorials = source.scan(BigInt(1))((acc, next) => acc * next)


  override def run(): Future[Done] = {

    val result: Future[Done] =
      factorials
        .map(num => s"\t$num\t")
        .grouped(2)
        .runWith(Sink.foreach(println(_)))
    result
  }
}
