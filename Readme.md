# Spark MiniProject

In this mini project, you will use Apache Spark to process input files to generate an output file.

## Setup

Install any of the following you don't already have:

1. Download and unpack Spark 1.6.3: http://d3kbcqa49mib13.cloudfront.net/spark-1.6.3-bin-hadoop2.6.tgz
2. Install Java 8: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
3. Install SBT: http://www.scala-sbt.org/download.html

## Problem

### Inputs

There are three input files, all of which are comma-delimited:

#### activity.csv

* Contains three fields: `visitorId`, `email`, `timestamp`
* `visitorId` is a String representing a visitor to a website
* `email` is a String representing the email address of a user
* `timestamp` is a UTC DateTime (YYYY-MM-DD HH:MM:SS) representing the time the activity occurred
* Either one or both of `visitorId`/`email` must exist, and `timestamp` always exists

#### visitors.csv

* Contains two fields: `visitorId`, `userId`
* `visitorId` is a String representing a visitor to a website (same as in activity.csv)
* `userId` is an Integer representing a user in our database

#### users.csv

* Contains two fields: `userId`, `email`
* `userId` is an Integer representing a user in our database (same as in visitors.csv)
* `email` is a String representing the email address of a user (same as in activity.csv)

### Outputs

There are two parts to this problem. In each part, the goal is to generate an output directory with one or more `part-` files with the following requirements:

#### User Activity

* Tab-delimited, with the fields: `userId` and `timestamp`.
* Essentially, for each row in `activity.csv`, we want to use data from the other two files to get the `userId` from the `visitorId` or `email`.
* **If a row in activity.csv has BOTH `visitorId` and `email`, the lookup should be done using `email`.**
* Example row: `123<TAB>2017-01-02 03:04:05`

#### User's Best Hour(s)

* Tab-delimited, with the fields: `userId` and `bestHours`, where `bestHours` is a comma-delimited field containing the top three hours of the day (in UTC) where the user has the most activity.
* This should use the output from "User Activity" above.
* `bestHours` can contain less than three hours if a user is not active in three or more hours. Users with no activity should not show up in the file.
* The hours should be numbers between 0 and 23 inclusive.
* Example row: `123<TAB>3,4,23`

## Instructions

1. The entry points of the Spark applications are in ActivityRunner.scala (for "User Activity") and BestHoursRunner.scala (for "User's Best Hour(s)"). Start here, but feel free to create more files as necessary.
2. When done coding, run `sbt assembly` from the root of the project to package your project for testing.

### To Run "User Activity"

1. The application takes four command line arguments: `activityFile`, `visitorsFile`, `usersFile`, and `outputDir`. These correspond to the inputs/outputs described above.
2. Run your application on the command line like so (your jar path may be slightly different): `<path to unpacked spark>/bin/spark-submit --class miniproject.ActivityRunner target/scala-2.10/spark_miniproject-assembly-1.0.jar files/activity.csv files/visitors.csv files/users.csv user_activity`
3. The output files should be in the `user_activity/` directory.

### To Run "User's Best Hour(s)"

1. The application takes two command line arguments: `userActivityDirectory` and `outputDir`. `userActivityDirectory` refers to the output directory from "User Activity", and `outputDir` is the output directory for "User's Best Hour(s)".
2. Run your application on the command line like so (your jar path may be slightly different): `<path to unpacked spark>/bin/spark-submit --class miniproject.BestHoursRunner target/scala-2.10/spark_miniproject-assembly-1.0.jar user_activity best_hours`
3. The output files should be in the `best_hours/` directory.

## Notes

* All files are local, no HDFS is required
* Strive for production-quality code
* Code should be well-organized and reusable
* Although the sample files are small, your solution should be able to scale to much larger files
* Use either the RDD or DataFrame API
