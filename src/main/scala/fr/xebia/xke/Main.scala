package fr.xebia.xke

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

object Main extends App {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  val routes = Routes.routes

  // TODO
  // Démarrer le server en local sur le port 8080 avec l'objet routes ci-dessus
}