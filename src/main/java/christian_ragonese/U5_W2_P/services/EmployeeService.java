package christian_ragonese.U5_W2_P.services;

import christian_ragonese.U5_W2_P.entities.Employee;
import christian_ragonese.U5_W2_P.exceptions.BadRequestException;
import christian_ragonese.U5_W2_P.payloads.NewEmployeeDTO;
import christian_ragonese.U5_W2_P.repositories.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee save(NewEmployeeDTO payload) {
        this.employeeRepository.findByEmail(payload.email()).ifPresent(employee -> {
            throw new BadRequestException("L'email " + employee.getEmail() + " è già in uso, dipendente già registrato");
        });
        Employee newEmployee = new Employee(payload.username(), payload.name(), payload.surname(), payload.email(), payload.avatar());
        newEmployee.setAvatar(" " + payload.name() + "_" + payload.surname());

        Employee savedEmployee = this.employeeRepository.save(newEmployee);

        log.info("Il dipendente con id: " + savedEmployee.getId() + " è stato salvato correttamente!");
        return savedEmployee;

    }
}
