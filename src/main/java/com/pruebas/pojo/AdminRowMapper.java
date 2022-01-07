package com.pruebas.pojo;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminRowMapper implements RowMapper<Admin> {

    @Override
    public Admin mapRow(ResultSet rs, int rowNum) throws SQLException {
        Admin admin = new Admin();

        admin.setId(rs.getInt("id"));
        admin.setNombre(rs.getString("nombre"));
        admin.setCargo(rs.getString("cargo"));
        admin.setFechaCreacion(rs.getTimestamp("fechaCreacion"));

        return admin;
    }
}
