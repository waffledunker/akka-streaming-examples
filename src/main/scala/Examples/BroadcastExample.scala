package com.example.waffledunker
package Examples

import MainRunner.system
import Runners.RunWrapper

import akka.stream.ClosedShape
import akka.stream.scaladsl.{Broadcast, Flow, GraphDSL, RunnableGraph, Sink, Source}
import akka.{Done, NotUsed}

import scala.concurrent.Future

class BroadcastExample extends RunWrapper {

  val tweetObject = new TweetExample

  val tweets: Source[Tweet, NotUsed] = tweetObject.tweets

  override def run(): Future[Done] = ???

  def runGraph(): NotUsed = {
    graph.run()
  }

  val writeOutputSink: Sink[Any, Future[Done]] = Sink.foreach(println)

  val graph: RunnableGraph[NotUsed] = RunnableGraph.fromGraph(GraphDSL.create() { implicit b  =>
    import akka.stream.scaladsl.GraphDSL.Implicits._

    /**
     * Splitting Tweet flow into 2 different feeds and reusing same sink for both.
     */
    val bcast = b.add(Broadcast[Tweet](2))
    tweets ~> bcast.in
    bcast.out(0) ~> Flow[Tweet].map(_.author).grouped(2) ~> writeOutputSink
    bcast.out(1) ~> Flow[Tweet].mapConcat(_.hashtags.toList).grouped(2) ~> writeOutputSink
    ClosedShape
  })

}
