package com.pruebas.dao;

import com.pruebas.pojo.Admin;

import java.util.List;

public interface AdminDao {
    public boolean save(Admin admin);
    public List<Admin> findAll();
    public Admin findById(int id);
    public List<Admin> findByName(String nombre);
}
