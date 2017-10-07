package miniproject

import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._

object BestHoursRunner extends App with Helper {
  val userActivityDirectory = args(0)
  val outputDir = args(1)

  /**
    * Setup file headers.
    */
  final val userActivityHeaders = Seq("userId", "timestamp")

  /**
    * Create dataframe from ActivityRunner output.
    */
  val userActivityDf = createDf(s"$userActivityDirectory/", userActivityHeaders, "\t")

  /**
    * Counts the activity per hour of each userId.
    */
  val userTopActivityDf =
    userActivityDf
      .select(col("userId"), hour(col("timestamp")) as "hour")
      .groupBy("userId", "hour")
      .count

  /**
    * Set window to sort descending activity count per hour.
    */
  val window = Window.partitionBy("userId").orderBy(col("count").desc)

  /**
    * Take the top 3 hours by userId.
    */
  val bestHoursDf =
    userTopActivityDf
      .withColumn("r", row_number.over(window))
      .where(col("r") <= 3)
      .drop("r")
      .drop("count")

  /**
    * Aggregate the best hours.
    */
  val bestHoursAggDf =
    bestHoursDf
      .groupBy(col("userId"))
      .agg(concat_ws(",", collect_list(col("hour")
        .cast("string"))).alias("hours"))

  /**
    * Output the file.
    */
  writeDfToOutput(bestHoursAggDf, outputDir)
}
