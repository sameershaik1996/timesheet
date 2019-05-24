package us.redshift.timesheet.controller.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.timesheet.assembler.ClientAssembler;
import us.redshift.timesheet.domain.client.Client;
import us.redshift.timesheet.domain.client.ClientStatus;
import us.redshift.timesheet.dto.client.ClientDto;
import us.redshift.timesheet.dto.client.ClientListDto;
import us.redshift.timesheet.service.client.IClientService;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Set;

@RestController
@RequestMapping("timesheet/v1/api/")
public class ClientController {

    private final IClientService clientService;
    private final ClientAssembler clientAssembler;


    private final Logger log = LoggerFactory.getLogger(getClass());


    public ClientController(IClientService clientService, ClientAssembler clientAssembler) {
        this.clientService = clientService;
        this.clientAssembler = clientAssembler;
    }

    @PostMapping("client/save")
    public ResponseEntity<?> saveClient(@Valid @RequestBody ClientDto clientDto) throws ParseException {
        System.out.println(clientDto);
        Client client = clientAssembler.convertToEntity(clientDto);
        System.out.println(client);
        Client clientSaved = clientService.saveClient(client);

        return new ResponseEntity<>(clientAssembler.convertToDto(clientSaved), HttpStatus.CREATED);
    }

    @PutMapping("client/update")
    public ResponseEntity<?> updateClient(@Valid @RequestBody Set<ClientDto> clientDtos, @RequestParam(value = "status", required = false) String status) throws ParseException {
        clientDtos.forEach(clientDto -> System.out.println(clientDto));
        System.out.println(status);
        System.out.println(ClientStatus.get(status.toUpperCase()));
        Set<Client> clients = clientAssembler.convertToEntity(clientDtos);
        Set<Client> clientSaved = clientService.updateClient(clients, ClientStatus.get(status.toUpperCase()));
        return new ResponseEntity<>(clientAssembler.convertToDto(clientSaved), HttpStatus.OK);
    }

    @GetMapping("client/get")
    public ResponseEntity<?> getAllClientByPagination(@RequestParam(value = "status", defaultValue = "ALL", required = false) String status, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limits", defaultValue = "0") int limits, @RequestParam(value = "orderBy", required = false) String orderBy, @RequestParam(value = "fields", defaultValue = "id", required = false) String... fields) throws ParseException {

        if ("ALL".equalsIgnoreCase(status)) {
            Page<Client> clients = clientService.getAllClientByPagination(page, limits, orderBy, fields);
//            Set<ClientListDto> set = clientAssembler.convertToDto(clients);
            return new ResponseEntity<>(clients, HttpStatus.OK);
        } else {
            Set<Client> clients = clientService.findAllByStatus(ClientStatus.get(status.toUpperCase()));
            Set<ClientListDto> set = clientAssembler.convertToDto(clients);
            return new ResponseEntity<>(set, HttpStatus.OK);
        }

    }

    @GetMapping("client/get/{id}")
    public ResponseEntity<?> getClientById(@PathVariable(value = "id") Long id) throws ParseException {
        Client client = clientService.getClientById(id);
        ClientDto clientDto = clientAssembler.convertToDto(client);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }


    @GetMapping("client/get/statuses")
    public ResponseEntity<?> getAllClientStatuses() {
        return new ResponseEntity<>(clientService.getAllClientStatus(), HttpStatus.OK);
    }


}
