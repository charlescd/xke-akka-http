package fr.xebia.xke

import spray.json.DefaultJsonProtocol

case class User(id: Int, name: String, age: Int)

trait UserJson extends DefaultJsonProtocol {
  implicit val userFormat = jsonFormat3(User)
}
