package com.wesley.growth.implicity

import com.wesley.growth.implicity.ImplicitAspect._
/**
  * <p>
  *
  * </p>
  * Email yani@uoko.com
  *
  * @author Created by Yani on 2018/10/16
  */
object ImplicitApp {

    // 定义隐式转换函数 将 Man 转换为 Superman
    implicit def man2superman(p:Person): Superman = new Superman("clark")

    def main(args: Array[String]): Unit = {
        val p = new Person()
        p.destoryWall()

        // 不传参数, 会使用隐式参数
        perform()

        // 通过隐式转换 调用 Superman的方法
        p.fly()
    }

    // 隐式参数
    implicit val act: String = "laughing"

    // 通过柯里化函数定义, 配合implicit修饰变量, 在调用函数时可以省略对应参数
    def perform()(implicit act:String): Unit = println(s"perform $act")

}

class Person(){
    def fearWall(): Unit = println("敬畏城墙")
}

class Giant(){
    def destoryWall(): Unit = println("摧毁城墙")
}

class Superman(val name:String){
    def fly(): Unit = {
        println(s"superman [ $name ] fly ...")
    }
}