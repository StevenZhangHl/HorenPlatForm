package com.cyy.company.bean;

/**
 * @author :ChenYangYi
 * @date :2018/10/23/10:29
 * @description :费用明细
 * @github :https://github.com/chenyy0708
 */
public class ChargeDetail {
    /**
     * 租箱费
     */
    private double rentalFee;
    /**
     * 耗材费
     */
    private double consumablesFee;
    /**
     * 运费
     */
    private double freight;
    /**
     * 清洗费
     */
    private double cleaningFee;
    /**
     * 优惠
     */
    private double Offer;
    /**
     * 实付金额
     */
    private double payAmount;
    /**
     * 总费用
     */
    private double totalCost;

    public ChargeDetail(double rentalFee, double consumablesFee, double freight, double cleaningFee, double offer, double payAmount, double totalCost) {
        this.rentalFee = rentalFee;
        this.consumablesFee = consumablesFee;
        this.freight = freight;
        this.cleaningFee = cleaningFee;
        Offer = offer;
        this.payAmount = payAmount;
        this.totalCost = totalCost;
    }

    public double getRentalFee() {
        return rentalFee;
    }

    public double getConsumablesFee() {
        return consumablesFee;
    }

    public double getFreight() {
        return freight;
    }

    public double getCleaningFee() {
        return cleaningFee;
    }

    public double getOffer() {
        return Offer;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public double getTotalCost() {
        return totalCost;
    }
}
