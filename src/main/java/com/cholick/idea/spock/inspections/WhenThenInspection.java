package com.cholick.idea.spock.inspections;

import com.cholick.idea.spock.data.SpockLabel;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.groovy.codeInspection.BaseInspection;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.GrLabeledStatement;

public class WhenThenInspection extends BaseInspection {
    private static final String GROUP_NAME = "Spock";

    @Override
    @NotNull
    public String[] getGroupPath() {
        return new String[]{"Groovy", GROUP_NAME};
    }

    @Override
    @NotNull
    public String getGroupDisplayName() {
        return GROUP_NAME;
    }

    @NotNull
    public String getShortName() {
        return "SpWhenThenInspection";
    }

    @NotNull
    public String getDisplayName() {
        return "When has corresponding Then";
    }


    @NotNull
    protected String buildErrorString(Object... args) {
        return "when requires a corresponding then";
    }

    @Override @NotNull
    protected BaseLabelInspectionVisitor buildVisitor()  {
        return new WhenThenInspectionVisitor();
    }

    private class WhenThenInspectionVisitor extends BaseLabelInspectionVisitor {

        public WhenThenInspectionVisitor() {
            super(SpockLabel.WHEN);
        }

        @Override
        protected void doVisitLabeledStatement(@NotNull GrLabeledStatement labeledStatement, GrLabeledStatement nextLabeledStatement) {
            while(nextLabeledStatement != null && SpockLabel.valueOf(nextLabeledStatement) == SpockLabel.AND) {
                nextLabeledStatement = getNextLabel(nextLabeledStatement);
            }
            if(nextLabeledStatement == null) {
                PsiElement label = labeledStatement.getLabel();
                registerError(label);
            }
        }
    }

}
