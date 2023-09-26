/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.twillio;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 *
 * @author HP
 */
@Configuration
@PropertySource("classpath:configs.properties")
public class TwilioConfig {
    
    @Autowired
    private Environment env;
    
    public final String ACCOUNT_SID = env.getProperty("twillio.ACCOUNT_SID");
    public final String AUTH_TOKEN = env.getProperty("twillio.AUTH_TOKEN");

    @Bean
    public TwilioManager twilioManager() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        return new TwilioManager();
    }
}
