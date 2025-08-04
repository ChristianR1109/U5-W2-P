package christian_ragonese.U5_W2_P.controllers;


import christian_ragonese.U5_W2_P.entities.Employee;
import christian_ragonese.U5_W2_P.exceptions.ValidationException;
import christian_ragonese.U5_W2_P.payloads.NewEmployeeDTO;
import christian_ragonese.U5_W2_P.payloads.NewEmployeeRespDTO;
import christian_ragonese.U5_W2_P.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController

@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("")
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

}
