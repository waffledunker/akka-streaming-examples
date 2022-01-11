package com.example.waffledunker

import Examples.{BroadcastExample, QuickstartExample, TimeBasedExample, TweetExample}

import akka.actor.ActorSystem

object MainRunner extends App {

  //implicits
  implicit val system: ActorSystem = ActorSystem("Main")
  implicit val ec = system.dispatcher

  // Broadcast example
  val broadcast = new BroadcastExample
  val resGraph = broadcast.runGraph()

  // Quickstart example
  val quickstart = new QuickstartExample
  val result1 = quickstart.run()

  // Tweet example
  val tweetExample = new TweetExample
  val result2 = tweetExample.run

  // Time based example
  val timeBasedExample = new TimeBasedExample
  val result3 = timeBasedExample.run()

  result3.onComplete(_ => system.terminate())

}
