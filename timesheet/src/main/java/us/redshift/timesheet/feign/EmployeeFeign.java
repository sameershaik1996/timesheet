package us.redshift.timesheet.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import us.redshift.timesheet.dto.BaseDto;

import java.util.Set;

@FeignClient(name = "Inventory-Service", fallback = EmployeeFeignFallback.class)
public interface EmployeeFeign {

    @GetMapping("/inventory/employee")
    ResponseEntity<Set<BaseDto>> getAllEmployee();

}

@Component
class EmployeeFeignFallback implements EmployeeFeign {


    @Override
    public ResponseEntity<Set<BaseDto>> getAllEmployee() {
        return null;
    }
}

