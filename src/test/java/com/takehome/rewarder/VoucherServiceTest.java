package com.takehome.rewarder;

import com.takehome.rewarder.models.Customer;
import com.takehome.rewarder.models.Voucher;
import com.takehome.rewarder.service.CustomerExtractionService;
import com.takehome.rewarder.service.VoucherService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VoucherServiceTest {

    @Autowired
    private CustomerExtractionService customerExtractionService;

    @Autowired
    private VoucherService voucherService;

    @Test
    public void testGenerateVouchers() throws Exception {
        String filePath = "samples/test-rewarder-upload.csv";
        File file = new File(filePath);

        List<Customer> customerList = customerExtractionService.getCustomersFromFile(new FileInputStream(file));
        List<Voucher> vouchers = voucherService.generateVouchersForAllEligibleCustomersByValue(customerList);
        assertNotNull(vouchers);
        assertNotEquals(0, vouchers.size());
        if(vouchers != null) {
            Voucher voucher = vouchers.get(0);
            assertNotNull(voucher);
            assertNotNull(voucher.getVoucherCode());
            assertNotNull(voucher.getCustomer());
            assertNotNull(voucher.getAmount());
            assertNotNull(voucher.getValidityEndDate());
            assertNotNull(voucher.getValidityStartDate());
            assertNotEquals(0, voucher.getValidityInDays());
        }
    }

    @Test
    public void testGenerateVouchersForCustomersEligibleToday() throws Exception {
        Customer customer = new Customer();
        customer.setLastName("Doe");
        customer.setFirstName("John");
        customer.setOrderValue(new BigDecimal(100000));
        customer.setId(100L);
        customer.setBirthday(LocalDate.now());

        assertNotNull(customer.getLastName());

        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        List<Voucher> vouchers = voucherService.generateVouchersForCustomersEligibleToday(customerList);

        assertNotNull(vouchers);
        assertEquals(1, vouchers.size());

        Voucher voucher = vouchers.get(0);

        assertNotNull(voucher);
        assertNotNull(voucher.getAmount());
        assertEquals(LocalDate.now(), voucher.getValidityStartDate());
        assertNotNull(voucher.getVoucherCode());
        assertNotNull(voucher.getCustomer());
    }

}
