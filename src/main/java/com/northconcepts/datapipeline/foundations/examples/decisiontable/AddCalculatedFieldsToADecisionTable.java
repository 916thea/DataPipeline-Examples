package com.northconcepts.datapipeline.foundations.examples.decisiontable;

import com.northconcepts.datapipeline.core.Record;
import com.northconcepts.datapipeline.foundations.decisiontable.DecisionTable;
import com.northconcepts.datapipeline.foundations.decisiontable.DecisionTableCondition;
import com.northconcepts.datapipeline.foundations.decisiontable.DecisionTableOutcome;
import com.northconcepts.datapipeline.foundations.decisiontable.DecisionTableResult;
import com.northconcepts.datapipeline.foundations.decisiontable.DecisionTableRule;
import com.northconcepts.datapipeline.foundations.expression.CalculatedField;
import com.northconcepts.datapipeline.internal.expression.DefaultExpressionContext;

public class AddCalculatedFieldsToADecisionTable {

    public static void main(String[] args) {
        DefaultExpressionContext input = new DefaultExpressionContext();
        input.setValue("Age", 49);
        input.setValue("House Owned", true);
        input.setValue("Income", 1000.0);
        
        DecisionTable table = new DecisionTable()
        		
                .addField(new CalculatedField("ageThreshold", "40"))
                .addField(new CalculatedField("overAgeThreshold", "Age >= ageThreshold"))
                
                .addRule(new DecisionTableRule()
                        .addCondition(new DecisionTableCondition("overAgeThreshold", "? == true"))
                        .addCondition(new DecisionTableCondition("House Owned", "? == true"))
                        .addOutcome(new DecisionTableOutcome("Eligible", "true"))
                        )
                .addRule(new DecisionTableRule()
                        .addCondition(new DecisionTableCondition("overAgeThreshold", "? == true"))
                        .addCondition(new DecisionTableCondition("House Owned", "? == false"))
                        .addCondition(new DecisionTableCondition("Income", "? >= 2000"))
                        .addOutcome(new DecisionTableOutcome("Eligible", "true"))
                        )
                .addRule(new DecisionTableRule()
                        .addCondition(new DecisionTableCondition("overAgeThreshold", "? == true"))
                        .addCondition(new DecisionTableCondition("House Owned", "? == false"))
                        .addCondition(new DecisionTableCondition("Income", "? < 2000")) 
                        .addOutcome(new DecisionTableOutcome("Eligible", "false"))
                        )
                .addRule(new DecisionTableRule()
                        .addCondition(new DecisionTableCondition("overAgeThreshold", "? == false"))
                        .addCondition(new DecisionTableCondition("Income", "? >= 3000"))
                        .addOutcome(new DecisionTableOutcome("Eligible", "true"))
                        )
                .addRule(new DecisionTableRule()
                        .addCondition(new DecisionTableCondition("overAgeThreshold", "? == false"))
                        .addCondition(new DecisionTableCondition("Income", "? < 3000"))
                        .addOutcome(new DecisionTableOutcome("Eligible", "false"))
                        )
                ;
        
        DecisionTableResult result = table.evaluate(input);
        Record outcome = result.getOutcome();

        System.out.println("outcome = " + outcome);
    }

}
