package us.redshift.employee.service;

import us.redshift.employee.domain.Employee;
import us.redshift.employee.domain.Skill;

import java.util.List;
import java.util.Set;

public interface ISkillService {
    Skill createSkill(Skill skill);

    Skill updateSkill(Skill skill);

    List<Skill> getAllSkills();

    Skill getSkillById(Long id);

    List<Skill> getSkillByIds(List<Long> id);

    Set<Skill> findByEmployeesIn(List<Employee> employeeByIds);
}
