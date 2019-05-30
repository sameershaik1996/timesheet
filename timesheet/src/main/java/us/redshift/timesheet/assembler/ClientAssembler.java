package us.redshift.timesheet.assembler;

import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import us.redshift.timesheet.domain.client.Client;
import us.redshift.timesheet.dto.client.ClientDto;
import us.redshift.timesheet.dto.client.ClientListDto;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

@Component
public class ClientAssembler {

    private final ModelMapper mapper;

    public ClientAssembler(ModelMapper mapper) {
        this.mapper = mapper;
    }


    public Client convertToEntity(ClientDto clientDto) {
        return mapper.map(clientDto, Client.class);
    }

    public Client convertToEntity(ClientDto clientDto, Client client) {
        mapper.map(clientDto, client);
        return client;
    }

    public List<Client> convertToEntity(List<ClientDto> clientDtos) {
        Type targetListType = new TypeToken<List<Client>>() {
        }.getType();
        return mapper.map(clientDtos, targetListType);
    }

    public ClientDto convertToDto(Client client) {
        return mapper.map(client, ClientDto.class);
    }

    public List<ClientListDto> convertToDto(List<Client> clients) {
        Type targetListType = new TypeToken<List<ClientListDto>>() {
        }.getType();
        return mapper.map(clients, targetListType);
    }

    public Page<ClientListDto> convertToPagedDto(Page<Client> clientPage) {

        Type targetListType = new TypeToken<List<ClientListDto>>() {
        }.getType();
        List<ClientListDto> dtos = mapper.map(clientPage.getContent(), targetListType);

        Page<ClientListDto> page = new Page<ClientListDto>() {
            @Override
            public int getTotalPages() {
                return clientPage.getTotalPages();
            }

            @Override
            public long getTotalElements() {
                return clientPage.getTotalElements();
            }

            @Override
            public <U> Page<U> map(Function<? super ClientListDto, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return clientPage.getNumber();
            }

            @Override
            public int getSize() {
                return clientPage.getSize();
            }

            @Override
            public int getNumberOfElements() {
                return clientPage.getNumberOfElements();
            }

            @Override
            public List<ClientListDto> getContent() {
                return dtos;
            }

            @Override
            public boolean hasContent() {
                return clientPage.hasContent();
            }

            @Override
            public Sort getSort() {
                return clientPage.getSort();
            }

            @Override
            public boolean isFirst() {
                return clientPage.isFirst();
            }

            @Override
            public boolean isLast() {
                return clientPage.isLast();
            }

            @Override
            public boolean hasNext() {
                return clientPage.hasNext();
            }

            @Override
            public boolean hasPrevious() {
                return clientPage.hasPrevious();
            }

            @Override
            public Pageable nextPageable() {
                return clientPage.nextPageable();
            }

            @Override
            public Pageable previousPageable() {
                return clientPage.previousPageable();
            }

            @NotNull
            @Override
            public Iterator<ClientListDto> iterator() {
                return dtos.iterator();
            }
        };

        return page;
    }
}
