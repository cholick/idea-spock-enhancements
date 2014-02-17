package com.cholick.idea.spock.inspections;

import com.cholick.idea.spock.data.SpockLabel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.groovy.codeInspection.BaseInspection;

public abstract class BaseLabelInspection extends BaseInspection {

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
    protected abstract SpockLabel getSpockLabel();

    @NotNull
    protected String buildErrorString(Object... args) {
        return getSpockLabel().toString() + " must be followed by " + getSpockLabel().getSuccessors();
    }

    @Override @NotNull
    protected BaseLabelInspectionVisitor buildVisitor() {
        return new BaseLabelInspectionVisitor(getSpockLabel());
    }

}
