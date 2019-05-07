package us.redshift.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.redshift.employee.domain.Skill;
import us.redshift.employee.repository.SkillRepository;


import javax.transaction.Transactional;
import java.util.List;

@Component
@Transactional
public class SkillService implements ISkillService {

    @Autowired
    private SkillRepository skillRepository;
    @Override
    public Skill createSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    @Override
    public Skill updateSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    @Override
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    @Override
    public Skill getSkillById(Long id) {
        return skillRepository.findById(id).get();
    }

    @Override
    public List<Skill> getSkillByIds(List<Long> id) {
        return skillRepository.findByIdIn(id);
    }
}
