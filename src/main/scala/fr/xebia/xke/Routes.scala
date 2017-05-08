package fr.xebia.xke

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

import scala.collection.mutable

class Routes(users: mutable.ArrayBuffer[User]) extends SprayJsonSupport with UserJson {

  // TODO
  // Implémenter un mécanisme pour stocker les users
  // Utiliser la liste fournie en paramètre de la classe
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
