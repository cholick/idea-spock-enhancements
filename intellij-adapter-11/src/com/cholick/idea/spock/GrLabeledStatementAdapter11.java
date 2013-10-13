package com.cholick.idea.spock;

import com.intellij.psi.PsiElement;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.GrLabeledStatement;

public class GrLabeledStatementAdapter11 extends GrLabeledStatementAdapter {

    @Override
    public PsiElement getLabel(GrLabeledStatement labeledStatement) {
        return labeledStatement.getLabel();
    }

}
