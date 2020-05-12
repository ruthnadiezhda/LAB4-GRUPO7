package com.example.laboratorio4.repository;

import com.example.laboratorio4.dto.EmpleadoResumen;
import com.example.laboratorio4.entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees,Integer> {

    @Query(value = "select e.* \n" +
            "from employees e\n" +
            "where e.first_name = ?1 or e.last_name = ?1 \n" +
            "or e.job_id = (select j.job_id\n" +
            "from jobs j \n" +
            "where j.job_title = ?1)\n" +
            "or e.department_id = (select d.department_id\n" +
            "from departments d\n" +
            "where d.department_name = ?1 or d.location_id = (select lo.location_id\n" +
            "from locations lo\n" +
            "where lo.city = ?1));" , nativeQuery = true)
    List<Employees> obtenerBusquedaEmpleado(String busqueda);




}
