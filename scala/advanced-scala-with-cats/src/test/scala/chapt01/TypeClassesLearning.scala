package chapt01

import org.scalatest.{FreeSpec, Matchers}

/**
  * Created by feliperojas on 11/25/17.
  */
class TypeClassesLearning extends FreeSpec with Matchers {

  trait Json

  final case class JsObject(get: Map[String, Json]) extends Json

  final case class JsString(get: String) extends Json

  final case class JsNumber(get: Double) extends Json

  trait JsonWriter[A] {
    def write(a: A): Json
  }

  final case class Person(name: String, email: String)

  object JsonWriteInstances {
    implicit val stringJsonWriter = new JsonWriter[String] {
      def write(a: String): Json = JsString(a)
    }

    implicit val personJsonWriter = new JsonWriter[Person] {
      def write(a: Person): Json =
        JsObject(Map(
          "name" -> JsString(a.name),
          "email" -> JsString(a.email)
        ))
    }
  }

  object Json {
    def toJson[A](a: A)(implicit jsonWriter: JsonWriter[A]): Json =
      jsonWriter.write(a)
  }

  "user json case class" in {
    import JsonWriteInstances._

    val json = Json.toJson(Person("felipe", "mail@mail.com"))

    json should be(JsObject(Map(
      "name" -> JsString("felipe"),
      "email" -> JsString("mail@mail.com")
    )))
  }
}
