package fr.xebia.xke

import java.util.UUID

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FunSpec, Matchers}

class RoutesTest extends FunSpec
  with Matchers
  with ScalatestRouteTest
  with MockFactory {

  describe("Routes") {

    describe("GET on any URI") {

      it("should return pong") {
        val uri = UUID.randomUUID().toString

        Get(uri) ~> Routes.routes ~> check {
          status should be(StatusCodes.OK)
          responseAs[String] should include("pong")
        }
      }
    }
  }
}
