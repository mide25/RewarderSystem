package com.takehome.rewarder;

import com.takehome.rewarder.models.Customer;
import com.takehome.rewarder.service.CustomerExtractionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerExtractionServiceTest {

    @Autowired
    private CustomerExtractionService customerExtractionService;

    @Test
    public void testExtractCustomersFromFile() throws Exception {
        String filePath = "samples/test-rewarder-upload.csv";
        File file = new File(filePath);

        List<Customer> customerList = customerExtractionService.getCustomersFromFile(new FileInputStream(file));
        assertNotNull(customerList);
        assertNotNull(customerList.get(0));
        assertNotNull(customerList.get(0).getOrderValue());
        assertNotNull(customerList.get(0).getBirthday());
        assertNotNull(customerList.get(0).getFirstName());
        assertNotNull(customerList.get(0).getId());
    }

}
