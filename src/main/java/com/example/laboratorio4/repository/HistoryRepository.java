package com.example.laboratorio4.repository;

import com.example.laboratorio4.dto.EmpleadoResumen;
import com.example.laboratorio4.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History,Integer> {

    @Query(value = "SELECT e.first_name AS \"nombre\",e.last_name AS \"apellido\",j.job_title AS \"cargo\", jh.start_date AS \"inicio\", jh.end_date AS \"fin\", \n" +
            "YEAR(jh.end_date)-YEAR(jh.start_date) AS \"anhos\", TIMESTAMPDIFF(MONTH,jh.start_date,jh.end_date) AS \"meses\"\n" +
            "FROM hr.employees e, hr.jobs j,hr.job_history jh \n" +
            "WHERE e.job_id=j.job_id AND jh.employee_id=e.employee_id",
            nativeQuery = true)
    List<EmpleadoResumen> obtenerHistoriaEmpleado();

}
