package chapt01

import java.util.Date

import org.scalatest.{FreeSpec, Matchers}
import cats.Eq
import cats.instances.all._
import cats.syntax.eq._
import cats.syntax.option._
import chapt01.CatInstance._

import scala.util.Try

/**
  * Created by feliperojas on 11/25/17.
  */
class CatsEqLearning extends FreeSpec with Matchers {

  "comparing instances with eq" in {
    val intEq: Eq[Int] = Eq.apply[Int]
    val stringIntEq = intEq.on[String](t => Try(t.toInt).getOrElse(0))

    intEq.eqv(10, 10) should be(true)
    stringIntEq.eqv("10", "test") should be(false)
    (10 =!= 20) should be(true)
    (1.some =!= None) should be(true)
  }

  "create Eq for custom instances" in {
    val longEq = Eq.apply[Long]
    implicit val dateEq = Eq.instance[Date]((d1, d2) => longEq.eq(d1.getTime, d2.getTime))

    (new Date() =!= new Date()) should be(true)
  }

  "create Eq for cat" in {
    val catEq = Eq.instance[Cat]((c1, c2) => c1.equals(c2))
    catEq.eqv(tobita, Cat("garfield", 20, "orange"))
  }

  "comparing more values with cats eq" in {
    case class Dog(name: String, age: Int)
    val dogEq = Eq.instance[Dog]((d1, d2) => d1.name == d2.name && d1.age == d2.age)

    dogEq.eqv(Dog("Apache", 10), Dog("Apache", 10)) should be(true)
    dogEq.eqv(Dog("Apache", 20), Dog("Apache", 10)) should be(false)
    dogEq.eqv(Dog("Apache", 10), Dog("Dakota", 10)) should be(false)

    val stringDogEq = dogEq.on[String] { s =>
      val values = s.split(" ")
      Dog(values(0), values(1).toInt)
    }

    stringDogEq.eqv("apache 10", "apache 10") should be(true)
    stringDogEq.eqv("apache 10", "apache 20") should be(false)
  }

}
