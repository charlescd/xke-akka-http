package fr.xebia.xke

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route


object Routes {

  // TODO
  // Améliorer cette route pour qu'elle ne réponde pong que lors d'un GET sur la route /ping
  val routes: Route = complete("pong")

}
