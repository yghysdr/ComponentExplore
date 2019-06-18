package com.github.yghysdr.component.order;

import com.github.yghysdr.component.order.api.IOrderApi;

/**
 * @date 2019/6/17
 * @email yghysdr@163.com
 */
public class OrderApi implements IOrderApi {

    @Override
    public String getOrderInfo(String id) {
        System.out.println("info=" + id);
        return "id=" + id;
    }

    @Override
    public void postOrder() {
        System.out.println("下订单");
    }
}
