package com.cholick.idea.spock.inspections;

import com.cholick.idea.spock.data.SpockLabel;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.groovy.codeInspection.BaseInspection;
import org.jetbrains.plugins.groovy.codeInspection.BaseInspectionVisitor;
import org.jetbrains.plugins.groovy.lang.psi.api.auxiliary.GrLabel;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.GrLabeledStatement;

public abstract class BaseLabelInspection extends BaseInspection {

    @NotNull
    protected abstract SpockLabel getSpockLabel();

    @NotNull
    protected String buildErrorString(Object... args) {
        return getSpockLabel().toString() + " must be followed by " + getSpockLabel().getSuccessors();
    }

    protected Visitor buildVisitor() {
        return new Visitor(getSpockLabel());
    }

    private static class Visitor extends BaseInspectionVisitor {

        private SpockLabel spockLabel;

        protected Visitor(@NotNull SpockLabel spockLabel) {
            this.spockLabel = spockLabel;
        }

        @Override
        public void visitLabeledStatement(GrLabeledStatement labeledStatement) {
            super.visitLabeledStatement(labeledStatement);

            if (applyInspection(labeledStatement)) {
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

        private boolean applyInspection(@NotNull GrLabeledStatement labeledStatement) {
            return labeledStatement.getLabel().getText().toLowerCase().equals(spockLabel.toString());
        }

    }

}
