package com.takehome.rewarder.models;

import org.apache.tomcat.util.codec.binary.Base64;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
        LocalDateTime startTime = LocalDateTime.now();
        String timeJumbled = startTime.toString().replace("-", "").replace(":", "").replace(".", "").replace("T", "");
        this.voucherCode = new String(Base64.encodeBase64String(timeJumbled.getBytes()));
    }

    public String getVoucherCode() {
        return voucherCode;
    }
}
