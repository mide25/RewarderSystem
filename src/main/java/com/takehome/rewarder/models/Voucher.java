package com.takehome.rewarder.models;

import com.takehome.rewarder.util.VoucherCodeGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Voucher {

    private BigDecimal amount;

    private int validityInDays;

    private LocalDate validityStartDate;

    private LocalDate validityEndDate;

    private Customer customer;

    private String voucherCode;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getValidityInDays() {
        return validityInDays;
    }

    public void setValidityInDays(int validityInDays) {
        this.validityInDays = validityInDays;
    }

    public LocalDate getValidityStartDate() {
        return validityStartDate;
    }

    public void setValidityStartDate(LocalDate validityStartDate) {
        this.validityStartDate = validityStartDate;
    }

    public LocalDate getValidityEndDate() {
        return validityEndDate;
    }

    public void setValidityEndDate(LocalDate validityEndDate) {
        this.validityEndDate = validityEndDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        this.voucherCode = VoucherCodeGenerator.randomString(7);
    }

    public String getVoucherCode() {
        return voucherCode;
    }
}
