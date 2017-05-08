package fr.xebia.xke

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route


object Routes {

  // TODO
  // Implémenter une route qui :
  // 1. Sur le endpoint /users avec le verbe POST
  // 2. Récupère le body de la requête (une simple string)
  // 4. La renvoie telle quelle avec le status 201
  // La combiner avec la route existante
  val routes: Route =
  path("ping") {
    get {
      complete("pong")
    }
  }

}
