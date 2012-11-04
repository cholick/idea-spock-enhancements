package com.cholick.idea.spock.inspections;

import com.cholick.idea.spock.data.SpockLabel;
import com.cholick.idea.spock.util.SpockClassCheck;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.groovy.codeInspection.BaseInspection;
import org.jetbrains.plugins.groovy.codeInspection.BaseInspectionVisitor;
import org.jetbrains.plugins.groovy.lang.psi.api.auxiliary.GrLabel;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.GrLabeledStatement;

public abstract class BaseLabelInspection extends BaseInspection {

    private static final String GROUP_NAME = "Spock";
    //overrideSpockClassCheck is just for unit testing. todo: Work out a better way.
    protected boolean overrideSpockClassCheck = false;

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
    protected abstract SpockLabel getSpockLabel();

    @NotNull
    protected String buildErrorString(Object... args) {
        return getSpockLabel().toString() + " must be followed by " + getSpockLabel().getSuccessors();
    }

    protected Visitor buildVisitor() {
        return new Visitor(getSpockLabel(), overrideSpockClassCheck);
    }

    private static class Visitor extends BaseInspectionVisitor {

        private SpockLabel spockLabel;
        private SpockClassCheck spockClassCheck;
        private boolean overrideSpockClassCheck;

        protected Visitor(@NotNull SpockLabel spockLabel, boolean overrideSpockClassCheck) {
            this.spockLabel = spockLabel;
            this.overrideSpockClassCheck = overrideSpockClassCheck;
        }

        @Override
        public void visitLabeledStatement(GrLabeledStatement labeledStatement) {
            super.visitLabeledStatement(labeledStatement);

            boolean isSpockClass = overrideSpockClassCheck
                    || new SpockClassCheck(labeledStatement.getContainingFile()).getIsSpockClass();
            if (isSpockClass && isSpockLabel(labeledStatement)) {
                GrLabel nextLabel = getNextLabel(labeledStatement.getNextSibling());
                if (nextLabel != null) {
                    checkSuccessorValid(nextLabel);
                }
            }
        }

        private GrLabel getNextLabel(@Nullable PsiElement element) {
            GrLabel nextLabel = null;
            while (element != null && nextLabel == null) {
                element = element.getNextSibling();
                if (element instanceof GrLabeledStatement) {
                    nextLabel = ((GrLabeledStatement) element).getLabel();
                }
            }
            return nextLabel;
        }

        private void checkSuccessorValid(@NotNull GrLabel nextLabel) {
            if (SpockLabel.contains(nextLabel)) {
                SpockLabel nextSpockLabel = SpockLabel.valueOf(nextLabel);
                if (!spockLabel.getSuccessors().contains(nextSpockLabel)) {
                    registerError(nextLabel);
                }
            } else {
                //todo: some other error?
            }
        }

        private boolean isSpockLabel(@NotNull GrLabeledStatement labeledStatement) {
            return labeledStatement.getLabel().getText().toLowerCase().equals(spockLabel.toString());
        }

    }

}
