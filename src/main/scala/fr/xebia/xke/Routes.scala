package fr.xebia.xke

import akka.actor.ActorSystem
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import akka.util.ByteString

import scala.collection.mutable

class Routes(users: mutable.ArrayBuffer[User])(implicit system: ActorSystem, mat: ActorMaterializer) extends SprayJsonSupport with UserJson {

  implicit val ec = system.dispatcher

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
        } ~ get {
          parameters('name.?, 'age.as[Int].?) {
            case (Some(name), Some(age)) => complete(users.filter(user => user.name == name && user.age == age).toSeq)
            case (None, Some(age)) => complete(users.filter(_.age == age).toSeq)
            case (Some(name), None) => complete(users.filter(_.name == name).toSeq)
            case (None, None) => complete(users.toSeq)
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
      } ~
      path("encode-response") {
        encodeResponse {
          complete("Encoded !")
        }
      } ~
      path("orders") {
        put {
          withSizeLimit(10000L) {
            fileUpload("step-7") {
              case (fileInfo, fileStream) =>
                onSuccess(fileStream.runFold(ByteString.empty)(_ ++ _).map(_.utf8String)) { file =>
                  complete(s"${file.length}")
                }
            }
          }
        }
      }

}
