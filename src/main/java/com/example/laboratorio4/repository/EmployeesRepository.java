package com.example.laboratorio4.repository;

import com.example.laboratorio4.dto.EmpleadosConMayorSalarioDto;
import com.example.laboratorio4.dto.SalarioMaximoPorDepartamentoDto;
import com.example.laboratorio4.dto.EmpleadoResumen;
import com.example.laboratorio4.entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees,Integer> {


    @Query(value = "select e.first_name as nombre,e.last_name as apellido, jh.start_date as inicio, jh.end_date as fin, j.job_title as puesto" +
            "FROM employees e\n" +
            "inner join job_history jh on (jh.employee_id = e.employee_id)\n" +
            "inner join jobs j on (j.job_id = jh.job_id) where e.salary>=?1 \n", nativeQuery = true)
    List<EmpleadosConMayorSalarioDto> obtenerEmpleadosConMayorSalarioDto(int sueldo);

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



    @Query(value = "select d.department_id as id,d.department_name as departamento, avg(e.salary) as promedio" +
            "FROM employees e\n" +
            "inner join departments d on (d.department_id = e.department_id)\n" +
            "group by departamento\n", nativeQuery = true)
    List<SalarioMaximoPorDepartamentoDto> obtenerSalarioMaximoPorDepartamente();


}
