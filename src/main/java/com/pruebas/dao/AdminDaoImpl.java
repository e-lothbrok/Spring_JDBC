package com.pruebas.dao;

import com.pruebas.pojo.Admin;
import com.pruebas.pojo.AdminRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public boolean update(Admin admin) {
        return jdbcTemplate.update("update admin set nombre=:nombre, cargo=:cargo, fechaCreacion=:fechaCreacion where id=:id",
                new BeanPropertySqlParameterSource(admin)) == 1;
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("delete from admin where id=:id",
                new MapSqlParameterSource("id",id)) == 1;
    }

    @Transactional
    @Override
    public int[] saveAll(List<Admin> adminList) {
        SqlParameterSource[] batchArgs = SqlParameterSourceUtils.createBatch(adminList.toArray());

        return jdbcTemplate.batchUpdate("insert into admin (nombre, cargo, fechaCreacion) values (:nombre, :cargo, :fechaCreacion)",
                batchArgs);
    }
}
