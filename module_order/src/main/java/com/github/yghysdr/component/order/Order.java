package com.github.yghysdr.component.order;

/**
 * Created by yghysdr on 2018/12/7.
 */
public class Order {

    public static void place() {
        System.out.println("下单了");
    }


    public static class Inner {
        public static void fun() {
            System.out.println("商品");
        }
    }
}
