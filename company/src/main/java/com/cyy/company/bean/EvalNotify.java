package com.cyy.company.bean;

/**
 * 评价成功
 */
public class EvalNotify {
    private int position;
    private String order_id;

    public EvalNotify(int position, String order_id) {
        this.position = position;
        this.order_id = order_id;
    }

    public int getPosition() {
        return position;
    }

    public String getOrder_id() {
        return order_id;
    }
}
