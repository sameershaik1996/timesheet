package us.redshift.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.employee.domain.Skill;
import us.redshift.employee.repository.SkillRepository;
import us.redshift.employee.service.ISkillService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("employee/v1/api/skill")
public class SkillController {
    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private ISkillService skillService;


    @PostMapping("/save")
    public ResponseEntity<?> createSkill(@Valid @RequestBody Skill skill){

       return new ResponseEntity<>(skillService.createSkill(skill), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateSkill(@Valid @RequestBody Skill skill){

        return new ResponseEntity<>(skillService.updateSkill(skill), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllSkills(){

        return new ResponseEntity<>(skillService.getAllSkills(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getSkillById(@PathVariable Long id){

        return new ResponseEntity<>(skillService.getSkillById(id), HttpStatus.OK);
    }

    @GetMapping("ids")
    public ResponseEntity<?> getSkillByIds(@RequestParam(value = "id") List<Long> id){

        return new ResponseEntity<>(skillService.getSkillByIds(id), HttpStatus.OK);
    }
}
