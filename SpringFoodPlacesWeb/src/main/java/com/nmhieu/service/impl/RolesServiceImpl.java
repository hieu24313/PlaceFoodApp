/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.service.impl;

import com.nmhieu.repository.RolesRepository;
import com.nmhieu.service.RolesService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author HP
 */
@Service
public class RolesServiceImpl implements RolesService {

    @Autowired
    private RolesRepository rolesRepo;

    @Override
    public List<Object[]> getRoles() {
        return this.rolesRepo.getRoles();
    }

}
