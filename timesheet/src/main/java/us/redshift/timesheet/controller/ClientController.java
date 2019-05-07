package us.redshift.timesheet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.timesheet.assembler.ClientAssembler;
import us.redshift.timesheet.domain.Client;
import us.redshift.timesheet.dto.ClientDto;
import us.redshift.timesheet.dto.ClientListDto;
import us.redshift.timesheet.feign.EmployeeFeign;
import us.redshift.timesheet.service.IClientService;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("timesheet/v1/api/client")
public class ClientController {

    private final IClientService clientService;
    private final ClientAssembler clientAssembler;

    private final EmployeeFeign employeeFeign;


    public ClientController(IClientService clientService, ClientAssembler clientAssembler, EmployeeFeign employeeFeign) {
        this.clientService = clientService;
        this.clientAssembler = clientAssembler;
        this.employeeFeign = employeeFeign;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveClient(@Valid @RequestBody ClientDto clientDto) throws ParseException {
        Client client = clientAssembler.convertToEntity(clientDto);
        Client clientSaved = clientService.saveClient(client);
        return new ResponseEntity<>(clientAssembler.convertToDto(clientSaved), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateClient(@Valid @RequestBody ClientDto clientDto) throws ParseException {
        Client client = clientAssembler.convertToEntity(clientDto);
        Client clientSaved = clientService.updateClient(client);
        return new ResponseEntity<>(clientAssembler.convertToDto(clientSaved), HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<?> getAllClientByPagination(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limits", defaultValue = "1") int limits, @RequestParam(value = "orderBy", required = false) String orderBy, @RequestParam(value = "fields", defaultValue = "id", required = false) String... fields) throws ParseException {
        List<Client> clients = clientService.getAllClientByPagination(page, limits, orderBy, fields);
        List<ClientListDto> list = clientAssembler.convertToDto(clients);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable(value = "id") Long id) throws ParseException {
        Client client = clientService.getClientById(id);
        ClientDto clientDto = clientAssembler.convertToDto(client);
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }

    @GetMapping("/statuses")
    public ResponseEntity<?> getAllClientStatuses() {
        return new ResponseEntity<>(clientService.getAllClientStatus(), HttpStatus.OK);
    }

    @GetMapping("/test/123")
    public ResponseEntity<?> getEmployees() {
        System.out.println(employeeFeign.getAllEmployee().getBody());
        return new ResponseEntity<>("", HttpStatus.OK);
    }

}
