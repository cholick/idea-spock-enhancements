package com.cholick.idea.spock.inspections;

import com.cholick.idea.spock.GrLabeledStatementAdapter;
import com.cholick.idea.spock.data.SpockLabel;
import com.cholick.idea.spock.util.SpockClassCheck;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.groovy.codeInspection.BaseInspectionVisitor;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.GrLabeledStatement;

public class BaseLabelInspectionVisitor extends BaseInspectionVisitor {

    private SpockLabel spockLabel;

    public BaseLabelInspectionVisitor(@NotNull SpockLabel spockLabel) {
        this.spockLabel = spockLabel;
    }

    @Override
    public void visitLabeledStatement(GrLabeledStatement labeledStatement) {
        super.visitLabeledStatement(labeledStatement);
        if (labeledStatement != null && getInSpockClass(labeledStatement) && isSpockLabel(labeledStatement)) {
            GrLabeledStatement nextLabeledStatement = getNextLabel(labeledStatement.getNextSibling());
            doVisitLabeledStatement(labeledStatement, nextLabeledStatement);
        }
    }

    private boolean getInSpockClass(GrLabeledStatement labeledStatement) {
        return new SpockClassCheck(labeledStatement.getContainingFile()).getIsSpockClass();
    }

    protected boolean isSpockLabel(@NotNull GrLabeledStatement labeledStatement) {
        String label = labeledStatement.getName() != null ? labeledStatement.getName() : "";
        return label.toLowerCase().equals(spockLabel.toString());
    }

    protected void doVisitLabeledStatement(@NotNull GrLabeledStatement labeledStatement, GrLabeledStatement nextLabeledStatement) {
        if (nextLabeledStatement != null) {
            checkSuccessorValid(nextLabeledStatement);
        }
    }

    protected GrLabeledStatement getNextLabel(@Nullable PsiElement element) {
        GrLabeledStatement nextLabel = null;
        while (element != null && nextLabel == null) {
            element = element.getNextSibling();
            if (element instanceof GrLabeledStatement) {
                nextLabel = ((GrLabeledStatement) element);
            }
        }
        return nextLabel;
    }

    private void checkSuccessorValid(@NotNull GrLabeledStatement nextLabeledStatement) {
        if (SpockLabel.contains(nextLabeledStatement)) {
            SpockLabel nextSpockLabel = SpockLabel.valueOf(nextLabeledStatement);
            if (nextSpockLabel != null && !spockLabel.getSuccessors().contains(nextSpockLabel)) {
                PsiElement nextLabel = GrLabeledStatementAdapter.getInstance().getLabel(nextLabeledStatement);
                registerError(nextLabel);
            }
        }
    }

}
