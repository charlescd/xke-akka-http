package fr.xebia.xke

import java.util.UUID

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.MethodRejection
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FunSpec, Inside, Matchers}

import scala.collection.mutable

class RoutesTest extends FunSpec
  with Matchers
  with ScalatestRouteTest
  with MockFactory
  with Inside
  with SprayJsonSupport
  with UserJson {

  describe("Routes") {

    describe("GET on /ping") {

      val users = mutable.ArrayBuffer[User]()
      val routes = new Routes(users).routes

      it("should return pong") {
        Get("/ping") ~> routes ~> check {
          status should be(StatusCodes.OK)
          responseAs[String] should be("pong")
        }
      }

      it("should fail on any other url") {
        Get(s"/${UUID.randomUUID().toString}") ~> routes ~> check {
          handled shouldBe false
        }
      }

      it("should fail on any other verb") {
        Post("/ping") ~> routes ~> check {
          inside(rejection) { case MethodRejection(_) => }
        }
      }
    }

    describe("POST on /users") {

      val users = mutable.ArrayBuffer[User]()
      val routes = new Routes(users).routes

      it("should return the user with the status 201") {
        val user = User(1, "toto", 29)
        Post("/users", user) ~> routes ~> check {
          status should be(StatusCodes.Created)
          responseAs[User] should be(user)
          users should have length 1
          users should contain(user)
        }
      }

    }
  }
}
