package us.redshift.timesheet.assembler;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;
import us.redshift.timesheet.domain.Client;
import us.redshift.timesheet.dto.ClientDto;
import us.redshift.timesheet.dto.ClientListDto;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.List;

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

    public List<ClientListDto> convertToDto(List<Client> clients) throws ParseException {
        Type targetListType = new TypeToken<List<ClientListDto>>() {
        }.getType();
        List<ClientListDto> list = mapper.map(clients, targetListType);
        return list;
    }
}
