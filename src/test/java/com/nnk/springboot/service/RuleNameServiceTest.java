package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class RuleNameServiceTest {

    @Mock
    private RuleNameRepository ruleNameRepository;

    @InjectMocks
    private RuleNameService ruleNameService;

    @Test
    public void return_all_ruleNames(){
        when(ruleNameRepository.findAll()).thenReturn(List.of(new RuleName()));
        List<RuleName> ruleNames = ruleNameService.getAllRuleName();

        assertThat(ruleNames).hasSize(1);
    }

    @Test
    public void return_empty_ruleName_list(){
        when(ruleNameRepository.findAll()).thenReturn(List.of());
        List<RuleName> ruleNames = ruleNameService.getAllRuleName();

        assertThat(ruleNames).isEmpty();
    }

    @Test
    public void get_ruleName_ok(){
        when(ruleNameRepository.findRuleNameById(anyInt())).thenReturn(new RuleName());
        RuleName ruleName = ruleNameService.getRuleName(1);

        assertThat(ruleName).isNotNull();
    }

    @Test
    public void get_ruleName_failure(){
        when(ruleNameRepository.findRuleNameById(anyInt())).thenReturn(null);
        RuleName ruleName = ruleNameService.getRuleName(1);

        assertThat(ruleName).isNull();
    }

    @Test
    public void add_ruleName_ok(){
        RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(rule);
        RuleName savedRuleName = ruleNameService.addRuleName(rule);

        assertThat(savedRuleName).isNotNull();
    }

    @Test
    public void update_ruleName_ok(){
        RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        rule.setId(1);
        RuleName rule2 = new RuleName("Rule Name", "Updated Description", "Json", "Template", "SQL", "SQL Part");
        when(ruleNameRepository.findRuleNameById(anyInt())).thenReturn(rule);
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(rule2);
        RuleName updatedRuleName = ruleNameService.updateRuleName(rule);

        assertThat(updatedRuleName.getDescription()).isEqualTo("Updated Description");
    }

    @Test
    public void update_ruleName_failure(){
        RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        rule.setId(1);
        when(ruleNameRepository.findRuleNameById(anyInt())).thenReturn(null);
        RuleName updatedRuleName = ruleNameService.updateRuleName(rule);

        assertThat(updatedRuleName).isNull();
    }

    @Test
    public void delete_ruleName_ok(){
        RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        rule.setId(1);
        when(ruleNameRepository.findRuleNameById(anyInt())).thenReturn(rule);
        doNothing().when(ruleNameRepository).delete(any(RuleName.class));

        assertThat(ruleNameService.deleteRuleName(1)).isTrue();
    }

    @Test
    public void delete_ruleName_failure(){
        when(ruleNameRepository.findRuleNameById(anyInt())).thenReturn(null);

        assertThat(ruleNameService.deleteRuleName(1)).isFalse();
    }
}
