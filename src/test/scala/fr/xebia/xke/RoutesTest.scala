package fr.xebia.xke

import java.util.UUID

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.MethodRejection
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FunSpec, Inside, Matchers}

class RoutesTest extends FunSpec
  with Matchers
  with ScalatestRouteTest
  with MockFactory
  with Inside {

  describe("Routes") {

    describe("GET on /ping") {

      it("should return pong") {
        Get("/ping") ~> Routes.routes ~> check {
          status should be(StatusCodes.OK)
          responseAs[String] should be("pong")
        }
      }

      it("should fail on any other url") {
        Get(s"/${UUID.randomUUID().toString}") ~> Routes.routes ~> check {
          handled shouldBe false
        }
      }

      it("should fail on any other verb") {
        Post("/ping") ~> Routes.routes ~> check {
          inside(rejection) { case MethodRejection(_) => }
        }
      }
    }

    describe("POST on /users") {

      it("should return the user with the status 201") {
        val user = User()
        Post("/users", user) ~> Routes.routes ~> check {
          status should be(StatusCodes.Created)
          responseAs[User] should be(user)
        }
      }

    }
  }
}
