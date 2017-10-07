name := "spark_miniproject"

version := "1.0"

scalaVersion := "2.10.6"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.6.3" % "provided",
  "org.apache.spark" %% "spark-sql" % "1.6.3" % "provided",
  "org.apache.spark" %% "spark-hive" % "1.6.3" % "provided",
  "com.databricks" % "spark-csv_2.10" % "1.5.0",
  "joda-time" % "joda-time" % "2.9.9"
)
