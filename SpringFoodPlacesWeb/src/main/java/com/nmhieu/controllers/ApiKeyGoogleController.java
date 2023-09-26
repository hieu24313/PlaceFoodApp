/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.controllers;

import java.net.HttpURLConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author HP
 */
@RestController
@PropertySource("classpath:configs.properties")
@RequestMapping("/api")
public class ApiKeyGoogleController {
    @Autowired
    private Environment env;
    
    @GetMapping("/loginGoogleKey/")
    @CrossOrigin
    public ResponseEntity<String> getApiKeyLoginGoogle(){
        return new ResponseEntity<>(env.getProperty("google.LOGIN_KEY"), HttpStatus.OK);
    }
    
    @GetMapping("/recaptchaKey/")
    @CrossOrigin
    public ResponseEntity<String> getApiKeyRecaptcha(){
        return new ResponseEntity<>(env.getProperty("google.RECAPTCHA_KEY"), HttpStatus.OK);
    }
}
