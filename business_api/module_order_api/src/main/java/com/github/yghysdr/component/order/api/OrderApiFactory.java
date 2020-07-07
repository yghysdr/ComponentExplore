package com.github.yghysdr.component.order.api;

import java.lang.reflect.InvocationTargetException;

/**
 * @date 2019/6/17
 * @email yghysdr@163.com
 */
public class OrderApiFactory {

    public static IOrderApi getIOrderApi(){
        try {
            return (IOrderApi) Class.forName("com.github.yghysdr.component.order.OrderApi").getConstructor().newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("");
    }

}
