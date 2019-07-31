package com.takehome.rewarder.models;

import java.math.BigDecimal;

public class VoucherConfig {

    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private BigDecimal voucherValue;
    private int validityInDays;

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public BigDecimal getVoucherValue() {
        return voucherValue;
    }

    public int getValidityInDays() {
        return validityInDays;
    }
}
