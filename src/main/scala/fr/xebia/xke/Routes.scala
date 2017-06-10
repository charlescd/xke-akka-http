package fr.xebia.xke

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

import scala.collection.mutable

class Routes(users: mutable.ArrayBuffer[User]) extends SprayJsonSupport with UserJson {

  // TODO
  // Implémenter une route pour récupérer un user par id (l'id sera dans le path).
  // La route doit renvoyer 404 si le user n'existe pas, et 200 s'il existe.
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
    }

}
