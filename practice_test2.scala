package shikshalokam

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._

object practice_test1 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master(master = "local[1]")
      .appName(name = "practice_test1")
      .getOrCreate()

    // Task 1: Read a JSON file from local
    val jsonPath = "/home/user1/scala learn/vivek_scala_test/vivek_scala_test/raw_data/simple.json"

    // Task 2: JSON Schema
    val jsonSchema = StructType(Seq(
      StructField("person", StructType(Seq(
        StructField("name", StringType, nullable = false),
        StructField("age", IntegerType, nullable = false),
        StructField("address", StructType(Seq(
          StructField("city", StringType, nullable = false),
          StructField("zipcode", StringType, nullable = false)
        )), nullable = false),
        StructField("languages", ArrayType(StringType), nullable = false)
      )), nullable = false),
      StructField("items", ArrayType(StructType(Seq(
        StructField("id", IntegerType, nullable = false),
        StructField("name", StringType, nullable = false),
        StructField("price", DoubleType, nullable = false)
      ))), nullable = false)
    ))

    val df = spark.read.schema(jsonSchema).json(jsonPath)
    df.show()

    // Task 3: Extract the few keys in object, array
    val selectedColumns = Seq(
      "person.name",
      "person.age",
      "person.address.city",
      "person.languages",
      "items.id",
      "items.name",
      "items.price"
    )
    val extractedData = df.selectExpr(selectedColumns: _*)

    // Task 4: Save it into a CSV file
    val csvOutputPath = "/home/user1/scala learn/vivek_scala_test/vivek_scala_test/raw_data/practice_test.csv"
    extractedData.write.mode(SaveMode.Overwrite).csv(csvOutputPath)

    // Task 5: Push the data into local PostgreSQL

    // Apply the schema to the DataFrame
    val dfWithSchema = extractedData
      .withColumnRenamed("person.languages", "person_languages_temp")
      .selectExpr(
        (0 until 2).map(i => s"person_languages_temp[$i] as person_languages_${i + 1}"): _*,
        "items.price"
      )

    // Write the DataFrame to PostgreSQL
    val dbProperties = Map(
      "user" -> "postgres",
      "password" -> "root",
      "driver" -> "org.postgresql.Driver",
      "url" -> "jdbc:postgresql://localhost:5432/pgdatabase"
    )

    dfWithSchema.write
      .mode(SaveMode.Overwrite)
      .jdbc(dbProperties("url"), "extractedData", dbProperties)

    spark.stop()

  }
}