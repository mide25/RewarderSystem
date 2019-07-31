package com.takehome.rewarder.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.takehome.rewarder.models.VoucherConfig;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class VoucherValueConfigService {

    @Value("${voucher.config.path}")
    private String configPath;

    public static List<VoucherConfig> voucherConfigList;

    public static BigDecimal minimumSpendForEligibility;

    /***
     * Load Voucher config at application startup
     */
    @PostConstruct
    public void bootstrapConfig() throws Exception {
        if(Strings.isBlank(configPath)){
            throw new Exception("Cannot load config for voucher");
        }

        String contents = new String(Files.readAllBytes(Paths.get(configPath)));
        Type listType = new TypeToken<ArrayList<VoucherConfig>>(){}.getType();
        Gson gson = new Gson();
        voucherConfigList = gson.fromJson(contents, listType);

        //get the minimum amount customer must spend to be eligible for a voucher from config
        for (VoucherConfig voucherConfig : voucherConfigList) {
            if(minimumSpendForEligibility == null){
                minimumSpendForEligibility = voucherConfig.getMinAmount();
                continue;
            }
            if(minimumSpendForEligibility.compareTo(voucherConfig.getMinAmount()) == 1){
                minimumSpendForEligibility = voucherConfig.getMinAmount();
            }
        }
    }

}
