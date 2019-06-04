package us.redshift.timesheet.controller.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.List;

@RestController
@RequestMapping("timesheet/v1/api/")
public class ClientController {

    private final IClientService clientService;
    private final ClientAssembler clientAssembler;
    private final ObjectMapper objectMapper;
    private final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);


    public ClientController(IClientService clientService, ClientAssembler clientAssembler, ObjectMapper objectMapper) {
        this.clientService = clientService;
        this.clientAssembler = clientAssembler;
        this.objectMapper = objectMapper;
    }

    @PostMapping("client/save")
    public ResponseEntity<?> saveClient(@Valid @RequestBody ClientDto clientDto) throws ParseException, JsonProcessingException {
        LOGGER.info("Client Insert {} ", objectMapper.writeValueAsString(clientDto));
        Client client = clientAssembler.convertToEntity(clientDto);
        LOGGER.info("Client After Assembler {} ", objectMapper.writeValueAsString(client));
        Client clientSaved = clientService.saveClient(client);
        return new ResponseEntity<>(clientAssembler.convertToDto(clientSaved), HttpStatus.CREATED);
    }

    @PutMapping("client/update")
    public ResponseEntity<?> updateClientStatus(@Valid @RequestBody List<ClientDto> clientDtos,
                                                @RequestParam(value = "status", required = false) String status) throws ParseException, JsonProcessingException {
        LOGGER.info("Client Update {} ", objectMapper.writeValueAsString(clientDtos));
        List<Client> clients = clientAssembler.convertToEntity(clientDtos);
        List<Client> clientSaved = clientService.updateClient(clients, ClientStatus.get(status.toUpperCase()));
        return new ResponseEntity<>(clientAssembler.convertToDto(clientSaved), HttpStatus.OK);
    }

    @PutMapping("client/update/{id}")
    public ResponseEntity<?> updateClient(@RequestBody ClientDto clientDto, @PathVariable("id") Long clientId) throws ParseException, JsonProcessingException {
        LOGGER.info("Client Update input {} ", objectMapper.writeValueAsString(clientDto));
        Client client = clientAssembler.convertToEntity(clientDto, clientService.getClientById(clientId));
        LOGGER.info("Client Update after conversion {} ", objectMapper.writeValueAsString(client));
        Client clientSaved = clientService.updateClient(client);
        return new ResponseEntity<>(clientAssembler.convertToDto(clientSaved), HttpStatus.OK);
    }


    @GetMapping("client/get")
    public ResponseEntity<?> getAllClientByPagination(@RequestParam(value = "status", defaultValue = "ALL", required = false) String status,
                                                      @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                      @RequestParam(value = "limits", defaultValue = "0") Integer limits,
                                                      @RequestParam(value = "orderBy", defaultValue = "ASC", required = false) String orderBy,
                                                      @RequestParam(value = "fields", defaultValue = "id", required = false) String... fields) throws ParseException {

        if ("ALL".equalsIgnoreCase(status)) {
            Page<Client> clients = clientService.getAllClientByPagination(page, limits, orderBy, fields);
            return new ResponseEntity<>(clientAssembler.convertToPagedDto(clients), HttpStatus.OK);
        } else {
            List<Client> clients = clientService.findAllByStatus(ClientStatus.get(status.toUpperCase()));
            List<ClientListDto> list = clientAssembler.convertToDto(clients);
            return new ResponseEntity<>(list, HttpStatus.OK);
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
