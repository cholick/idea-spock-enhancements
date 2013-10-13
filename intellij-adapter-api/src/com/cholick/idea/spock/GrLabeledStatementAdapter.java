package com.cholick.idea.spock;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.psi.PsiElement;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.GrLabeledStatement;

public abstract class GrLabeledStatementAdapter {

    public static GrLabeledStatementAdapter getInstance() {
        return ServiceManager.getService(GrLabeledStatementAdapter.class);
    }

    public abstract PsiElement getLabel(GrLabeledStatement labeledStatement);

}
