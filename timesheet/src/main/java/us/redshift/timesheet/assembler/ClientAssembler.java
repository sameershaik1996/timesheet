package us.redshift.timesheet.assembler;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import us.redshift.timesheet.domain.client.Client;
import us.redshift.timesheet.dto.client.ClientDto;
import us.redshift.timesheet.dto.client.ClientListDto;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.List;

@Component
public class ClientAssembler {

    private final ModelMapper mapper;

    public ClientAssembler(ModelMapper mapper) {
        this.mapper = mapper;
//        this.mapper.typeMap(Poc.class, Poc.class).setPropertyCondition(Conditions.isNotNull());

    }


    public Client convertToEntity(ClientDto clientDto) throws ParseException {
        return mapper.map(clientDto, Client.class);
    }

    public Client convertToEntity(ClientDto clientDto, Client client) throws ParseException {
        mapper.map(clientDto, client);
        return client;
    }

    public ClientDto convertToDto(Client client) throws ParseException {
        return mapper.map(client, ClientDto.class);
    }

    public List<Client> convertToEntity(List<ClientDto> clientDtos) throws ParseException {
        Type targetListType = new TypeToken<List<Client>>() {
        }.getType();
        List<Client> clientList = mapper.map(clientDtos, targetListType);
        return clientList;
    }


    public List<ClientListDto> convertToDto(List<Client> clients) throws ParseException {
        Type targetListType = new TypeToken<List<ClientListDto>>() {
        }.getType();
        List<ClientListDto> clientList = mapper.map(clients, targetListType);
        return clientList;
    }

    public Page<ClientListDto> convertToPagedDto(Page<Client> clientPage) {

        Type targetListType = new TypeToken<List<ClientListDto>>() {
        }.getType();
        List<ClientListDto> dtos = mapper.map(clientPage.getContent(), targetListType);

        Page<ClientListDto> page = new PageImpl<>(dtos,
                new PageRequest(clientPage.getPageable().getPageNumber(), clientPage.getPageable().getPageSize(), clientPage.getPageable().getSort()),
                dtos.size());

        return page;
    }
}
