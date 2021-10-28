package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RuleNameService {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    public List<RuleName> getAllRuleName(){
        List<RuleName> ruleNames = ruleNameRepository.findAll();
        if (!ruleNames.isEmpty()){
            log.info("RuleNames found.");
            return ruleNames;
        }
        log.error("No RuleName found.");
        return List.of();
    }

    public RuleName getRuleName(int id){
        RuleName ruleName = ruleNameRepository.findRuleNameById(id);
        if (ruleName != null){
            log.info("RuleName found.");
            return ruleName;
        }
        log.error("No RuleName found.");
        return null;
    }

    public RuleName addRuleName(RuleName ruleName) {
        try{
            RuleName savedRuleName = ruleNameRepository.save(ruleName);
            log.info("RuleName successfully saved.");
            return savedRuleName;
        } catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    public RuleName updateRuleName(RuleName ruleName) {
        RuleName ruleNameToUpdate = ruleNameRepository.findRuleNameById(ruleName.getId());
        if (ruleNameToUpdate != null){
            log.info("Update in progress ...");
            ruleNameToUpdate.setName(ruleName.getName());
            ruleNameToUpdate.setDescription(ruleName.getDescription());
            ruleNameToUpdate.setJson(ruleName.getJson());
            ruleNameToUpdate.setTemplate(ruleName.getTemplate());
            ruleNameToUpdate.setSqlStr(ruleName.getSqlStr());
            ruleNameToUpdate.setSqlPart(ruleName.getSqlPart());
            return ruleNameRepository.save(ruleNameToUpdate);
        }
        log.error("Unable to update the RuleName.");
        return null;
    }

    public boolean deleteRuleName(int id){
        RuleName ruleName = ruleNameRepository.findRuleNameById(id);
        if (ruleName != null){
            ruleNameRepository.delete(ruleName);
            log.info("RuleName successfully deleted.");
            return true;
        }
        log.error("Deletion failure");
        return false;
    }
}
