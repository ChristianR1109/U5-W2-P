package christian_ragonese.U5_W2_P.services;

import christian_ragonese.U5_W2_P.entities.Employee;
import christian_ragonese.U5_W2_P.exceptions.BadRequestException;
import christian_ragonese.U5_W2_P.exceptions.NotFoundException;
import christian_ragonese.U5_W2_P.payloads.NewEmployeeDTO;
import christian_ragonese.U5_W2_P.repositories.EmployeeRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private Cloudinary imgUploader;


    //------------------------SAVE--------------------------------------

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

    //------------------------FIND ALL----------------------------------------
    public Page<Employee> findAll(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        return this.employeeRepository.findAll(pageable);
    }
    //------------------------FIND BY ID------------------------------------

    public Employee findById(UUID employeeId) {
        return this.employeeRepository.findById(employeeId).orElseThrow(() -> new NotFoundException(String.valueOf(employeeId)));
    }

    //--------------------FIND BY ID AND UPDATE------------------------------------------------

    public Employee findByIdAndUpdate(UUID employeeId, NewEmployeeDTO payload) {

        Employee found = this.findById(employeeId);

        if (!found.getEmail().equals(payload.email())) {
            this.employeeRepository.findByEmail(payload.email()).ifPresent(employee -> {
                throw new BadRequestException("L'email " + employee.getEmail() + " è già in uso! ");
            });
        }
        found.setUsername(payload.username());
        found.setName(payload.name());
        found.setSurname(payload.surname());
        found.setEmail(payload.email());

        Employee modifiedEmployee = this.employeeRepository.save(found);

        log.info("L'utente con id : " + found.getId() + " è stato modificato con successo!");

        return modifiedEmployee;
    }

    //--------------------FIND BY ID AND DELETE------------------------------------------------
    public void findByIdAndDelete(UUID employeeID) {
        Employee found = this.findById(employeeID);
        this.employeeRepository.delete(found);
    }

    //---------------------UPLOAD AVATAR----------------------------------------

    public Employee uploadAvatar(MultipartFile file, UUID employeeId) {
        try {
            Employee found = this.findById(employeeId);

            Map result = imgUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imgUrl = (String) result.get("url");

            found.setAvatar(imgUrl);
            return employeeRepository.save(found);
        } catch (Exception ex) {
            throw new BadRequestException("Problemi con il file");
        }
    }

}
