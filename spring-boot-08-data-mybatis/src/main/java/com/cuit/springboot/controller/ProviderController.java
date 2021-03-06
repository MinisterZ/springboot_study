package com.cuit.springboot.controller;

import com.cuit.springboot.entities.Provider;
import com.cuit.springboot.mapper.ProviderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version JDK  1.8.151
 * @Author: Mirrors
 * @Description: Chengdu City
 *
 *      注解版mybatis
 */

@RestController
public class ProviderController {


    @Autowired
    ProviderMapper providerMapper;

    @GetMapping("/provider/{pid}")
    public Provider getProvider(@PathVariable("pid") Integer pid){

            Provider provider=providerMapper.getProviderByPid(pid);
            return provider;

    }
    @GetMapping("/provider")
    public Provider addProvider(Provider provider) {
            providerMapper.addProvider(provider);
            return provider;
    }

}
