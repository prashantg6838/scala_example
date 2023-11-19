package build.sbt

object scala_test {
  def main(args: Array[String]): Unit = {

    // 1. Data Types and Variables
    val myInteger: Int = 42
    val myDouble: Double = 3.14
    val myBoolean: Boolean = true
    val myString: String = "Hello, world!"

    // 2. String Interpolation
    val name = "Prashant"
    val age = 25
    val interpolatedString = s"My name is $name and I am $age years old."

    println(interpolatedString)

    // 3. Control Statements
    val x = 10
    if (x > 5) {
      println("x is greater than 5")
    } else {
      println("x is less than or equal to 5")
    }

    // 4. Match Expressions
    val day = "Monday"
    val typeOfDay = day match {
      case "Monday" | "Tuesday" | "Wednesday" | "Thursday" | "Friday" => "Weekday"
      case "Saturday" | "Sunday" => "Weekend"
      case _ => "Unknown day"
    }

    println(s"Today is $day, which is a $typeOfDay.")

    // 5. Scala Functions
    def add(a: Int, b: Int): Int = {
      a + b
    }

    val sum = add(3, 5)
    println(s"The sum of 3 and 5 is $sum")

    // 6. Higher order Function
    def applyOperation(x: Int, y: Int, operation: (Int, Int) => Int): Int = {
      operation(x, y)
    }

    val result = applyOperation(6, 3, (a, b) => a * b)
    println(s"The result of the operation is $result")

  }
}


