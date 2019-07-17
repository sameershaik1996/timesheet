package us.redshift.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import us.redshift.employee.domain.Employee;
import us.redshift.employee.domain.Skill;

import java.util.List;
import java.util.Set;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    List<Skill> findByIdIn(List<Long> id);

    Set<Skill> findByEmployeesIn(List<Employee> employees);

    @Query(value = "select distinct(employee_id) from emp_employees_skill where skill_id in (select id from emp_skills where skill like '%:name' )",nativeQuery = true)
    List<Long> findEmployeesBySkill(@Param("name") String name);
}
