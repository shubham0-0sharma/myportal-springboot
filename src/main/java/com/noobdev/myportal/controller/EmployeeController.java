package com.noobdev.myportal.controller;

import com.noobdev.myportal.exception.ResourceNotFoundException;
import com.noobdev.myportal.model.Employee;
import com.noobdev.myportal.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = {"http://192.168.137.1:3000","http://localhost:3000/"})
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    public List<Employee> getAllEmployee(){
        return employeeRepository.findAll();
    }
    @GetMapping("/employees/{empId}")
    public ResponseEntity<Employee>  getEmployee(@PathVariable Long empId){
       Employee emp = employeeRepository.findById(empId).orElseThrow(()-> new ResourceNotFoundException("Employee does not exist with id: "+ empId));
       return new ResponseEntity<>(emp, HttpStatus.OK);

    }
    @PostMapping("/employees")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee emp){
       Employee  empCreated = employeeRepository.save(emp);
        return new ResponseEntity<>(empCreated, HttpStatus.CREATED);

    }
    @PutMapping("/employees/{empId}")
    public ResponseEntity<HttpStatus> addEmployee( @PathVariable Long empId ,@RequestBody Employee emp){
       Employee empFound =employeeRepository.findById(empId).orElseThrow(()-> new ResourceNotFoundException("Employee does not exist with id: "+ empId));
        empFound.setFirstName(emp.getFirstName());
        empFound.setLastName(emp.getLastName());
        empFound.setEmailId(emp.getEmailId());
        employeeRepository.save(empFound);

        return new ResponseEntity<>(HttpStatus.OK);

    }
    @DeleteMapping("/employees/{empId}")
        public ResponseEntity<Map<String,Boolean>>   deleteEmployee(@PathVariable Long empId ){
        Employee empFound =employeeRepository.findById(empId).orElseThrow(()-> new ResourceNotFoundException("Employee does not exist with id: "+ empId));
        employeeRepository.delete(empFound);
        Map<String, Boolean> response  = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);

    }
}
