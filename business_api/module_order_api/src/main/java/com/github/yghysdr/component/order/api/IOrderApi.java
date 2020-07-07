package com.github.yghysdr.component.order.api;

/**
 * @date 2019/6/17
 * @email yghysdr@163.com
 */
public interface IOrderApi {
    /**
     * 获取订单
     *
     * @param id
     * @return
     */
    String getOrderInfo(String id);

    /**
     * 生成订单
     */
    void postOrder();
}
