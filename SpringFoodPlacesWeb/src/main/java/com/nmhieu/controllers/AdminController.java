/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author HP
 */

@Controller
@ControllerAdvice
public class AdminController {
    @ModelAttribute
    public void commonAttr(Model model) {
        
    }
    
    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }
}
