package com.example.laboratorio4.controller;


import com.example.laboratorio4.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Search")
public class SearchController {

    @Autowired
    EmployeesRepository employeeRepository;

    @GetMapping(value = {"","/"})
    public String indice(){
        return "Search/indice";
    }

    @GetMapping(value = {"/listar"})
    public String listaEmpleadosMayorSalrio (Model model){
        model.addAttribute("lista2",employeeRepository.obtenerEmpleadosConMayorSalarioDto(8000));
        return "Search/lista2";
    }

    @PostMapping("/busqueda")
    public String buscar (Model model, @RequestParam("sueldo") Integer sueldo){
        model.addAttribute("lista2",employeeRepository.obtenerEmpleadosConMayorSalarioDto(sueldo));
        return "Search/lista2";
    }

    @GetMapping(value = "/vistaDoble")
    public String cantidadEmpleadosPorPais (){

        //COMPLETAR
        return "/Search/salario";
    }

    @GetMapping("/listar")
    public String listarEmpleadoDep() {
        //COMPLETAR
        return "/Search/lista3";

    }


}
