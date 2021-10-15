package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
public class RuleNameController {
    // TODO: Inject RuleName service

    @Autowired
    private RuleNameService ruleNameService;

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        // TODO: find all RuleName, add to model

        log.info("Request all ruleNames");
        List<RuleName> ruleNames = ruleNameService.getAllRuleName();
        if (!ruleNames.isEmpty()){
            model.addAttribute("ruleNames", ruleNames);
        }
        model.addAttribute("ruleName", new RuleName());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        log.info("Request add ruleName");
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return RuleName list
        if (result.hasErrors()){
            log.error("Error(s) in the form");
            return "ruleName/add";
        }
        ruleNameService.addRuleName(ruleName);
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get RuleName by Id and to model then show to the form
        log.info("Request add ruleName");
        RuleName ruleName = ruleNameService.getRuleName(id);
        if (ruleName != null){
            model.addAttribute("ruleName", ruleName);
        }
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update RuleName and return RuleName list
       if (result.hasErrors()){
           log.error("Error(s) in the form");
           model.addAttribute("ruleName", ruleName);
           return "ruleName/update";
       }
       ruleNameService.updateRuleName(ruleName);
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        // TODO: Find RuleName by Id and delete the RuleName, return to Rule list
        log.info("Request delete ruleName");
        ruleNameService.deleteRuleName(id);
        return "redirect:/ruleName/list";
    }
}
