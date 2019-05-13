package us.redshift.timesheet.assembler;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;
import us.redshift.timesheet.domain.client.Client;
import us.redshift.timesheet.dto.client.ClientDto;
import us.redshift.timesheet.dto.client.ClientListDto;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Set;

@Component
public class ClientAssembler {

    private final ModelMapper mapper;

    public ClientAssembler(ModelMapper mapper) {
        this.mapper = mapper;

//        Converter<Instant, Long> InstantToLong =
//                mappingContext -> {
//
//                    Instant source = null;
//                    Long epoch = new Long(0);
//
//                    if (mappingContext.getSource() != null)
//                        source = mappingContext.getSource();
//                    epoch=source.toEpochMilli();
//                    System.out.println(epoch);
//
//                    return epoch;
//                };
//
//        mapper.addMappings(new PropertyMap<BaseEntity, BaseDto>() {
//            protected void configure() {
//                using(InstantToLong).map(source.getCreatedAt()).setCreatedAt(null);
//                using(InstantToLong).map(source.getUpdatedAt()).setUpdatedAt(null);
//            }
//        });
    }


    public Client convertToEntity(ClientDto clientDto) throws ParseException {
        return mapper.map(clientDto, Client.class);
    }

    public ClientDto convertToDto(Client client) throws ParseException {
        return mapper.map(client, ClientDto.class);
    }

    public Set<ClientListDto> convertToDto(Set<Client> clients) throws ParseException {
        Type targetListType = new TypeToken<Set<ClientListDto>>() {
        }.getType();
        Set<ClientListDto> set = mapper.map(clients, targetListType);
        return set;
    }
}
