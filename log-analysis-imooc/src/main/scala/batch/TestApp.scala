package batch

import org.apache.spark.sql.SparkSession
;

/**
 * <p>
 *
 * </p>
 * Email yani@uoko.com
 *
 * @author Created by Yani on 2019/09/03
 */
object TestApp {

    def main(args: Array[String]): Unit = {
        val spark = SparkSession.builder().appName("TestApp")
                    .master("local[2]")
                    .getOrCreate()

        val rdd = spark.sparkContext.parallelize(List(1,2,3,4))
        rdd.collect().foreach(println)

        spark.stop()
    }
}
