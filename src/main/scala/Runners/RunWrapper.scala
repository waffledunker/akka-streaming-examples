package com.example.waffledunker
package Runners

import akka.Done

import scala.concurrent.Future

trait RunWrapper {

  def run(): Future[Done]

}
