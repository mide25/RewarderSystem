package com.takehome.rewarder.service;

import com.takehome.rewarder.models.Customer;
import com.takehome.rewarder.models.Voucher;
import com.takehome.rewarder.models.VoucherConfig;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class VoucherService {

    public List<Voucher> generateVouchersForAllEligibleCustomersByValue(List<Customer> customers) throws Exception {
        List<Voucher> vouchers = new ArrayList<>();
        for (Customer customer : customers) {
            if(customer.getOrderValue().compareTo(VoucherValueConfigService.minimumSpendForEligibility) != -1){
                Voucher voucher = new Voucher();
                voucher.setCustomer(customer);
                setVoucherValidity(voucher);
                vouchers.add(voucher);
            }
        }
        return vouchers;
    }

    public List<Voucher> generateVouchersForCustomersEligibleToday(List<Customer> customers) throws Exception {
        List<Voucher> vouchers = new ArrayList<>();
        for (Customer customer : customers) {
            if((customer.getOrderValue().compareTo(VoucherValueConfigService.minimumSpendForEligibility) != -1 )&& (customer.getBirthday().equals(LocalDate.now()))){
                Voucher voucher = new Voucher();
                voucher.setCustomer(customer);
                setVoucherValidity(voucher);
                vouchers.add(voucher);
            }
        }
        return vouchers;
    }

    private void setVoucherValidity(Voucher voucher) throws Exception {
        Customer customer = voucher.getCustomer();
        if(customer == null || customer.getOrderValue() == null){
            throw new Exception("Cannot set validity for Voucher without a valid customer or customer with no valid order value");
        }
        List<VoucherConfig> voucherConfigList = VoucherValueConfigService.voucherConfigList;
        for (VoucherConfig vocherConfig : voucherConfigList) {
            BigDecimal customerOrderValue = customer.getOrderValue();
            if(customerOrderValue.compareTo(vocherConfig.getMinAmount()) == 0  || customerOrderValue.compareTo(vocherConfig.getMinAmount()) == 1) {
                //zero represents infinity here so if we see a max amount of zero that means the voucher will have the highest value possible
                if ((customerOrderValue.compareTo(vocherConfig.getMaxAmount()) == 0 || customerOrderValue.compareTo(vocherConfig.getMaxAmount()) == -1) || vocherConfig.getMaxAmount().compareTo(BigDecimal.ZERO) == 0) {
                    voucher.setAmount(vocherConfig.getVoucherValue());
                    voucher.setValidityInDays(vocherConfig.getValidityInDays());
                    voucher.setValidityStartDate(customer.getBirthday());
                    voucher.setValidityEndDate(customer.getBirthday().plusDays(vocherConfig.getValidityInDays() - 1));
                }
            }
        }
    }
}
