package com.takehome.rewarder.service;

import com.takehome.rewarder.models.Customer;
import com.takehome.rewarder.util.FileExtractionUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerExtractionService {

    @Value("${file.title.customerId: Customer ID}")
    private String customerIdTitleInFile;

    @Value("${file.title.firstName:Customer First Name}")
    private String customerFirstNameTitleInFile;

    @Value("${file.title.lastName:Customer LastName}")
    private String customerLastNameTitleInFile;

    @Value("${file.title.orderValue:Order Value}")
    private String customerOrderValueTitleInFile;

    @Value("${file.title.birthdayDate:Birthday date}")
    private String customerBirthDateTitleInFile;

    @Value("${file.data.format:d/MM/yyyy}")
    private String dateFormatInFile;

    public List<Customer> getCustomersFromFile(InputStream inputStream) throws Exception {
        List<String[]> fileAsToken = FileExtractionUtil.extractCsvAsListArray(inputStream);

        //get index of columns in file
        int customerIdIndex = -1;
        int customerFirstNameIndex = -1;
        int customerLastNameIndex = -1;
        int orderValueIndex = -1;
        int birthdayDateindex = -1;
        String[] headers = fileAsToken.get(0);
        for (int i = 0; i<headers.length; i++) {
            if(formatTitle(headers[i]).contains(formatTitle(customerIdTitleInFile))){
                customerIdIndex = i;
            }
            else if(formatTitle(headers[i]).contains(formatTitle(customerFirstNameTitleInFile))){
                customerFirstNameIndex = i;
            }
            else if(formatTitle(headers[i]).contains(formatTitle(customerLastNameTitleInFile))){
                customerLastNameIndex = i;
            }
            else if(formatTitle(headers[i]).contains(formatTitle(customerOrderValueTitleInFile))){
                orderValueIndex = i;
            }
            else if(formatTitle(headers[i]).contains(formatTitle(customerBirthDateTitleInFile))){
                birthdayDateindex = i;
            }
        }

        //check compulsory headers
        if(customerIdIndex == -1 || customerFirstNameIndex == -1 || orderValueIndex == -1 || birthdayDateindex == -1){
            throw new Exception("Invalid File headers supplied");
        }

        List<Customer> customerList = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormatInFile);
        for (int i = 1; i < fileAsToken.size(); i++){
            String[] tokens = fileAsToken.get(i);
            Customer customer = new Customer();
            customer.setBirthday(LocalDate.parse(tokens[birthdayDateindex], formatter));
            customer.setId(Long.valueOf(tokens[customerIdIndex]));
            customer.setFirstName(tokens[customerFirstNameIndex]);
            customer.setOrderValue(new BigDecimal(tokens[orderValueIndex]));
            if(customerLastNameIndex != -1){
                customer.setLastName(tokens[customerLastNameIndex]);
            }
            customerList.add(customer);
        }
        return customerList;
    }

    private String formatTitle(String title){
        return title.replaceAll(" ", "").toLowerCase();
    }
}
