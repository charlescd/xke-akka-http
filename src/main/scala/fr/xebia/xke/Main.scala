package fr.xebia.xke

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

import scala.collection.mutable

object Main extends App {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  val routes = new Routes(mutable.ArrayBuffer[User]()).routes

  Http().bindAndHandle(routes, "localhost", 8080)
}