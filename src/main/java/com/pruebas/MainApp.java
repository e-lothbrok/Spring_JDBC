package com.pruebas;

import com.pruebas.dao.AdminDao;
import com.pruebas.pojo.Admin;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext contexto = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        AdminDao adminDao = contexto.getBean("adminDao", AdminDao.class);

        Timestamp ts = new Timestamp(new Date().getTime());

        try {
            /*Admin admin = new Admin();
            admin.setNombre("Martin");
            admin.setCargo("gerente");
            admin.setFechaCreacion(ts);
            adminDao.save(admin);

            List<Admin> admins = adminDao.findAll();
            for (Admin admins_ : admins) { System.out.println(admins_); }

            System.out.println(adminDao.findById(2));

            System.out.println(adminDao.findByName("J").toString());

            Admin admin1 = adminDao.findById(3);
            admin1.setNombre("martin");
            admin1.setCargo("subgerente");
            adminDao.update(admin1);

            adminDao.delete(4);*/

            List<Admin> admins = new ArrayList<Admin>();
            admins.add(new Admin("pedro","jefe de departamento",ts));
            admins.add(new Admin("luis","subjefe de departemento", ts));
            adminDao.saveAll(admins);

        }catch (CannotGetJdbcConnectionException e){
            e.printStackTrace();
        }catch (DataAccessException e){
            e.printStackTrace();
        }

        contexto.close();

    }
}