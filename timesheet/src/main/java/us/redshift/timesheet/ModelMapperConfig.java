package us.redshift.timesheet;

//@Configuration
public class ModelMapperConfig {

       /* @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();


//      BaseDto to Long
        Converter<Set<BaseDto>, Set<Long>> BaseToLong = mappingContext -> {

            Set<BaseDto> source = new HashSet<>();
            if (mappingContext.getSource() != null)
                source = mappingContext.getSource();

//            Set<Long> dest = new HashSet<>();
//            source.forEach(baseDto -> {
//                dest.add(baseDto.getId());
//            });

            Set<Long> dest = source.stream().map(baseDto -> baseDto.getId()).collect(Collectors.toSet());
            return dest;
        };

//      adding BaseDto to long conversion property
        mapper.addMappings(new PropertyMap<TaskDto, Task>() {
            protected void configure() {
                using(BaseToLong).map(source.getEmployees()).setEmployeeId(null);
                using(BaseToLong).map(source.getSkills()).setSkillId(null);
            }
        });

        mapper.addMappings(new PropertyMap<ProjectDto, Project>() {
            protected void configure() {
                using(BaseToLong).map(source.getEmployees()).setEmployeeId(null);
            }
        });


//      Long to BaseDto
        Converter<Set<Long>, Set<BaseDto>> LongToBase = mappingContext -> {
            Set<Long> source = new HashSet<>();
            if (mappingContext.getSource() != null) {
                source = mappingContext.getSource();
            }
            Set<BaseDto> dest = source.stream().map(id -> new BaseDto(id, "")).collect(Collectors.toSet());
            return dest;
        };

//      adding long to BaseDto conversion property
        mapper.addMappings(new PropertyMap<Task, TaskDto>() {
            protected void configure() {
                using(LongToBase).map(source.getEmployeeId()).setEmployees(null);
                using(LongToBase).map(source.getSkillId()).setSkills(null);
            }
        });


//      Project to BaseDto
        Converter<Project, BaseDto> projectToBase =
                project -> {
                    return new BaseDto(project.getSource().getId(), project.getSource().getName());
                };
//      Client to BaseDto
        Converter<Client, BaseDto> clientToBase =
                client -> {
                    return new BaseDto(client.getSource().getId(), client.getSource().getName());
                };

//      adding toBase Property
        mapper.addMappings(new PropertyMap<Task, ProjectTaskListDto>() {
            protected void configure() {
                using(projectToBase).map(source.getProject()).setProject(null);
                using(clientToBase).map(source.getProject().getClient()).setClient(null);
            }
        });
        return mapper;
    }

*/
}
