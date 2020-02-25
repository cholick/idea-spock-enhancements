package com.cholick.idea.spock.inspections;

import com.cholick.idea.spock.data.SpockLabel;
import com.cholick.idea.spock.util.SpockConstants;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.groovy.codeInspection.BaseInspection;
import org.jetbrains.plugins.groovy.lang.psi.api.auxiliary.modifiers.GrModifierList;
import org.jetbrains.plugins.groovy.lang.psi.api.auxiliary.modifiers.annotation.GrAnnotation;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.GrLabeledStatement;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.typedef.members.GrMethod;

public class WhereUnrollInspection extends BaseInspection {
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
        return "SpWhereUnrollInspection";
    }

    @NotNull
    public String getDisplayName() {
        return "Parameterized feature methods include @Unroll";
    }

    @NotNull
    protected String buildErrorString(Object... args) {
        return "parameterized feature methods should include @Unroll";
    }

    @Override
    @NotNull
    protected BaseLabelInspectionVisitor buildVisitor() {
        return new WhereUnrollInspectionVisitor();
    }

    private class WhereUnrollInspectionVisitor extends BaseLabelInspectionVisitor {

        public WhereUnrollInspectionVisitor() {
            super(SpockLabel.WHERE);
        }

        @Override
        protected void doVisitLabeledStatement(@NotNull GrLabeledStatement labeledStatement, GrLabeledStatement nextLabeledStatement) {
            GrMethod method = null;
            PsiElement parent = labeledStatement.getParent();
            while (parent != null && method == null) {
                if (GrMethod.class.isAssignableFrom(parent.getClass())) {
                    method = (GrMethod) parent;
                } else {
                    parent = parent.getParent();
                }
            }
            if (method != null) {
                GrModifierList modifierList = method.getModifierList();
                boolean foundUnroll = false;
                for (GrAnnotation annotation : modifierList.getAnnotations()) {
                    if (SpockConstants.UNROLL_ANNOTATION.equals(annotation.getShortName())) {
                        foundUnroll = true;
                    }
                }
                if (!foundUnroll) {
                    PsiElement label = labeledStatement.getLabel();
                    registerError(label);
                }
            }
        }

    }

}
