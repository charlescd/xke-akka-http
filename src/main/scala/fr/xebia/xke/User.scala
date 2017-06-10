package fr.xebia.xke

//TODO
// Créer un user et les implicits nécessaires à la conversion en JSON
case class User()

object User {
  //TODO
  // Changer la méthode apply pour qu'elle renvoie un User généré aléatoirement
  def apply(): User = new User()
}
