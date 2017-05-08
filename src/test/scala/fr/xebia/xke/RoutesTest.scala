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

      it("should return 409 when the id already exists") {
        val user = User(1, "titi", 30)
        Post("/users", user) ~> routes ~> check {
          status should be(StatusCodes.Conflict)
        }
      }

    }

    describe("GET on /users/:id") {

      val users = mutable.ArrayBuffer[User](User(1, "toto", 29), User(2, "titi", 30))
      val routes = new Routes(users).routes

      it("should return the user if it exists") {
        Get("/users/1") ~> routes ~> check {
          status should be(StatusCodes.OK)
          responseAs[User] should be(User(1, "toto", 29))
        }
      }

      it("should return 404 if it does not exists") {
        Get("/users/18") ~> routes ~> check {
          status should be(StatusCodes.NotFound)
        }
      }
    }

    describe("GET on /users") {

      val users = mutable.ArrayBuffer[User](User(1, "toto", 29), User(2, "titi", 30), User(3, "toto", 30))
      val routes = new Routes(users).routes

      it("should return all the users") {
        Get("/users") ~> routes ~> check {
          status should be(StatusCodes.OK)
          responseAs[List[User]] should contain theSameElementsAs List(
            User(1, "toto", 29),
            User(2, "titi", 30),
            User(3, "toto", 30)
          )
        }
      }

      it("should return only some user when the request contains a filter") {
        Get("/users?nom=toto") ~> routes ~> check {
          status should be(StatusCodes.OK)
          responseAs[List[User]] should contain theSameElementsAs List(
            User(1, "toto", 29),
            User(3, "toto", 30)
          )
        }
      }
    }
  }

}
