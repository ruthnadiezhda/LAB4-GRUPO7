package com.example.laboratorio4.controller;
import com.example.laboratorio4.entity.Employees;
import com.example.laboratorio4.repository.DepartmentsRepository;
import com.example.laboratorio4.repository.EmployeesRepository;
import com.example.laboratorio4.repository.JobsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.jws.WebParam;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeesRepository employeesRepository;

    @Autowired
    JobsRepository jobsRepository;

    @Autowired
    DepartmentsRepository departmentsRepository;

    @GetMapping(value = {"","/"})
    public String listaEmployee(Model model){
        model.addAttribute("listaEmployeea", employeesRepository.findAll());
        model.addAttribute("listaJobs", jobsRepository.findAll());
        model.addAttribute("listaDepartments", departmentsRepository.findAll());
        return "employee/lista";
    }

    @GetMapping("/new")
    public String nuevoEmployeeForm(@ModelAttribute("employees") Employees employees, Model model) {
            model.addAttribute("listaJobs", jobsRepository.findAll());
            model.addAttribute("listaManagers", employeesRepository.findAll());
            model.addAttribute("listaDepartment", departmentsRepository.findAll());
        return "employee/Frm";
    }

    @PostMapping("/save")
    public String guardarEmployee(@ModelAttribute("employees") @Valid Employees employees, BindingResult bindingResult,
                                  RedirectAttributes attr,
                                  @RequestParam(name="hire_date", required=false) String hire_date, Model model) {

        if(bindingResult.hasErrors()){
            model.addAttribute("listaJobs", jobsRepository.findAll());
            model.addAttribute("listaManagers", employeesRepository.findAll());
            model.addAttribute("listaDepartments", departmentsRepository.findAll());
            return "employee/Frm";
        }else {

            if (employees.getEmployee_id() == 0) {
                attr.addFlashAttribute("msg", "Empleado creado exitosamente");
                employees.setHire_date(new Date());
                employeesRepository.save(employees);
                return "redirect:/employee";
            } else {

                try {
                    employees.setHire_date(new SimpleDateFormat("yyyy-MM-dd").parse(hire_date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                employeesRepository.save(employees);
                attr.addFlashAttribute("msg", "Empleado actualizado exitosamente");
                return "redirect:/employee";
            }
        }
    }

    @GetMapping("/edit")
    public String editarEmployee(@RequestParam("employee_id") int employee_id, Model model, @ModelAttribute("employees") Employees employees, RedirectAttributes attr) {

        Optional<Employees> opt = employeesRepository.findById(employee_id);
        if (opt.isPresent()) {
            Employees e =opt.get();

            model.addAttribute("employee",e);
                model.addAttribute("listaJobs", jobsRepository.findAll());
                model.addAttribute("listaManagers", employeesRepository.findAll());
                model.addAttribute("listaDepartments", departmentsRepository.findAll());
                return "employee/Frm";

        } else {
            return "redirect:/employee";
        }



    }

    @GetMapping("/delete")
    public String borrarEmpleado(Model model,
                                      @RequestParam("id") int id,
                                      RedirectAttributes attr) {

        Optional<Employees> optEmployees = employeesRepository.findById(id);

        if (optEmployees.isPresent()) {
            employeesRepository.deleteById(id);
            attr.addFlashAttribute("msg","Empleado borrado exitosamente");
        }
        return "redirect:/employee";

    }

    @PostMapping("/search")
    public String buscar (@RequestParam("search") String busqueda, Model model){

        model.addAttribute("listaEmployee", employeesRepository.obtenerBusquedaEmpleado(busqueda));
        model.addAttribute("listaManagers", employeesRepository.obtenerBusquedaEmpleado(busqueda));
        model.addAttribute("listaJobs", jobsRepository.findAll());
        model.addAttribute("listaDepartments", departmentsRepository.findAll());
        return "employee/lista";
    }

}
