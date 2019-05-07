package us.redshift.timesheet.controller;

import org.springframework.web.bind.annotation.*;
import us.redshift.timesheet.domain.Client;
import us.redshift.timesheet.service.IClientService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("timesheet/v1/api/")
public class ClientController {

    private final IClientService clientService;


    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("client/save")
    public Client saveClient(@Valid @RequestBody Client client) {
        return clientService.saveClient(client);
    }

    @PutMapping("client/update")
    public Client updateClient(@Valid @RequestBody Client client) {
        return clientService.updateClient(client);
    }

    @GetMapping({"client/"})
    public List<Client> getAllClient() {
        return clientService.getAllClient();
    }

    @GetMapping("client/{id}")
    public Client getClient(@PathVariable(value = "id") Long id) {
        return clientService.getClient(id);
    }

}
