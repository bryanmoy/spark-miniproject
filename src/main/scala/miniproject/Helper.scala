package miniproject

import org.apache.spark.SparkContext
import org.apache.spark.sql.hive._
import org.apache.spark.sql.DataFrame

trait Helper {
  val sc = new SparkContext()
  val sqlContext = new HiveContext(sc)
  val BaseDir = new java.io.File(".").getCanonicalPath

  protected def createDf(file: String, headers: Seq[String], delimiter: String = ","): DataFrame = {
    sqlContext.read.format("com.databricks.spark.csv")
      .option("inferSchema", "true")
      .option("delimiter", delimiter)
      .load(s"$BaseDir/$file")
      .toDF(headers: _*)
  }

  protected def writeDfToOutput(dataframe: DataFrame, outputDir: String): Unit = {
    var output = s"$BaseDir/$outputDir"

    dataframe.write.format("com.databricks.spark.csv")
      .option("delimiter", "\t")
      .save(output)
  }
}
