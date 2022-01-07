package com.pruebas.dao;

import com.pruebas.pojo.Admin;
import com.pruebas.pojo.AdminRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component("adminDao")
public class AdminDaoImpl implements AdminDao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public boolean save(Admin admin) {

        /*MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue("nombre", admin.getNombre());
        paramMap.addValue("cargo", admin.getCargo());
        paramMap.addValue("fechaCreacion", admin.getFechaCreacion());*/

        BeanPropertySqlParameterSource paramMap = new BeanPropertySqlParameterSource(admin);

        return jdbcTemplate.update("insert into admin (nombre, cargo, fechaCreacion) " +
                "values (:nombre, :cargo, :fechaCreacion)", paramMap) == 1;
    }

    @Override
    public List<Admin> findAll() {

        return jdbcTemplate.query("select * from admin", new RowMapper<Admin>() {
            @Override
            public Admin mapRow(ResultSet rs, int rowNum) throws SQLException {
                Admin admin = new Admin();

                admin.setId(rs.getInt("id"));
                admin.setNombre(rs.getString("nombre"));
                admin.setCargo(rs.getString("cargo"));
                admin.setFechaCreacion(rs.getTimestamp("fechaCreacion"));

                return admin;
            }
        });
    }

    @Override
    public Admin findById(int id) {
        return jdbcTemplate.queryForObject("select * from admin where id = :id",
                new MapSqlParameterSource("id", id), new AdminRowMapper());
    }

    @Override
    public List<Admin> findByName(String nombre) {
        return jdbcTemplate.query("select * from admin where nombre like :nombre",
                new MapSqlParameterSource("nombre", "%" + nombre + "%"), new AdminRowMapper());
    }
}
