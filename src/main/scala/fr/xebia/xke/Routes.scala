package fr.xebia.xke

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

//TODO
// Penser à étendre ou importer la classe apportant le support JSON
object Routes {

  // TODO
  // Remplacer la string par une case class User avec : un id (int), un nom (string), un age (int)
  // Utiliser le fichier User présent dans le package fr.xebia.xke
  val routes: Route =
  path("ping") {
    get {
      complete("pong")
    }
  } ~
    path("users") {
      post {
        entity(as[String]) { user =>
          complete(StatusCodes.Created, user)
        }
      }
    }

}
