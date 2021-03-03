package com.wesley.growth.implicity

/**
 * ImplicitClassApp
 *
 * @author WangPanYong
 * @since 2021/03/03 17:23
 */
object ImplicitClassApp extends App {

    /**
     * 定义 隐式类
     * @param x 为所有Int类型增加 add 方法
     */
    implicit class Calculator(x:Int) {
        def add(a:Int) = a + x
    }

    println(1.add(2))

}
