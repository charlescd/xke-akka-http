package fr.xebia.xke

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

import scala.collection.mutable

class Routes(users: mutable.ArrayBuffer[User]) extends SprayJsonSupport with UserJson {

  // TODO
  // Implémenter une route pour lister tous les user (GET sur la route /users).
  // Ajouter un mécanisme pour filtrer les users par nom et par age dans les query params.
  val routes: Route =
  path("ping") {
    get {
      complete("pong")
    }
  } ~
    path("users") {
      post {
        entity(as[User]) { user =>
          if (users.exists(_.id == user.id)) complete(StatusCodes.Conflict)
          else {
            users.append(user)
            complete(StatusCodes.Created, user)
          }
        }
      }
    } ~
    path("users" / IntNumber) { userId =>
      get {
        val user = users.find(_.id == userId)
        user match {
          case Some(u) => complete(u)
          case None => complete(StatusCodes.NotFound)
        }
      }
    }

}
