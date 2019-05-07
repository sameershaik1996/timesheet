package us.redshift.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import us.redshift.employee.domain.Skill;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    List<Skill> findByIdIn(List<Long> id);
}
