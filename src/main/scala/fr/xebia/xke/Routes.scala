package fr.xebia.xke

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

import scala.collection.mutable

class Routes(users: mutable.ArrayBuffer[User]) extends SprayJsonSupport with UserJson {

  // TODO
  // Implémenter un mécanisme pour stocker les users créés
  // Utiliser la liste fournie en paramètre de la classe
  // Attention : la liste ne doit contenir que des id uniques
  //
  // Lorsque l'on tente de créer un user déjà existant, la route doit retourner 409 (CONFLICT)
  val routes: Route =
  path("ping") {
    get {
      complete("pong")
    }
  } ~
    path("users") {
      post {
        entity(as[User]) { user =>
          complete(StatusCodes.Created, user)
        }
      }
    }

}
