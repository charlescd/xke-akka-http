package fr.xebia.xke

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

object Main extends App {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  val routes = Routes.routes

  Http().bindAndHandle(routes, "localhost", 8080)
}