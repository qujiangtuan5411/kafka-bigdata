package com.dada.dm.qujia.config;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.dada.dm.qujia.constant.ResourceConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jt.Qu
 * @description sentinel 配置规则
 * @program: kafka-demo
 * @date 2023-03-22 19:58
 */
@Component
public class SentinelConfig implements ApplicationListener<ContextRefreshedEvent> {


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //设置限流规则
        FlowRule flowRule = new FlowRule();
        flowRule.setResource(ResourceConstant.SENTINEL_TEST);
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setCount(1);
        List<FlowRule> rules = new ArrayList<>();
        rules.add(flowRule);
        FlowRuleManager.loadRules(rules);


    }

}
