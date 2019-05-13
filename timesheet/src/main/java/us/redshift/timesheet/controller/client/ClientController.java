package us.redshift.timesheet.controller.client;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.timesheet.assembler.ClientAssembler;
import us.redshift.timesheet.domain.client.Client;
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
    public ResponseEntity<?> updateClient(@Valid @RequestBody ClientDto clientDto) throws ParseException {
        Client client = clientAssembler.convertToEntity(clientDto);
        Client clientSaved = clientService.updateClient(client);
        return new ResponseEntity<>(clientAssembler.convertToDto(clientSaved), HttpStatus.OK);
    }

    @GetMapping("clients")
    public ResponseEntity<?> getAllClientByPagination(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limits", defaultValue = "1") int limits, @RequestParam(value = "orderBy", required = false) String orderBy, @RequestParam(value = "fields", defaultValue = "id", required = false) String... fields) throws ParseException {
        Set<Client> clients = clientService.getAllClientByPagination(page, limits, orderBy, fields);
        Set<ClientListDto> list = clientAssembler.convertToDto(clients);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("client/{id}")
    public ResponseEntity<?> getClientById(@PathVariable(value = "id") Long id) throws ParseException {
        Client client = clientService.getClientById(id);
        ClientDto clientDto = clientAssembler.convertToDto(client);
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }


    @GetMapping("client/statuses")
    public ResponseEntity<?> getAllClientStatuses() {
//        System.out.println(ClientStatus.getLookup().keySet());
        return new ResponseEntity<>(clientService.getAllClientStatus(), HttpStatus.OK);
    }

    @PutMapping("client/test/123")
    public ResponseEntity<?> getEmployees(@RequestBody Client client) {
        int i = 0;
        return new ResponseEntity<>(i, HttpStatus.OK);
    }


}
