package christian_ragonese.U5_W2_P.controllers;


import christian_ragonese.U5_W2_P.entities.Employee;
import christian_ragonese.U5_W2_P.exceptions.ValidationException;
import christian_ragonese.U5_W2_P.payloads.NewEmployeeDTO;
import christian_ragonese.U5_W2_P.payloads.NewEmployeeRespDTO;
import christian_ragonese.U5_W2_P.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController

@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    //---------------------------POST---------------------------------
    @PostMapping("")  // POST http://localhost:1313/employees
    @ResponseStatus(HttpStatus.CREATED)
    public NewEmployeeRespDTO save(@RequestBody @Validated NewEmployeeDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage()).toList());
        } else {
            Employee newEmployee = this.employeeService.save(payload);
            return new NewEmployeeRespDTO(newEmployee.getId());
        }
    }
    //--------------------------------GET------------------------------------------

    @GetMapping("") // GET http://localhost:1313/employees
    public Page<Employee> findAll(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String sortBy
    ) {
        return this.employeeService.findAll(page, size, sortBy);
    }

    @GetMapping("/{employeeId}")
    public Employee getEmployeeById(@PathVariable UUID employeeId) {
        return this.employeeService.findById(employeeId);
    }

    //------------------------PUT ----------------------------------------
    @PutMapping("/{employeeId}")
    public Employee getByIdAndUpdate(@PathVariable UUID employeeId, @RequestBody NewEmployeeDTO payload) {
        return this.employeeService.findByIdAndUpdate(employeeId, payload);
    }

    //-------------------------DELETE --------------------------------------
    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getByIdAndDelete(@PathVariable UUID employeeId) {
        this.employeeService.findByIdAndDelete(employeeId);
    }
}
