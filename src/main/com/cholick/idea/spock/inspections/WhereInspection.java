package com.cholick.idea.spock.inspections;

import com.cholick.idea.spock.data.SpockLabel;
import org.jetbrains.annotations.NotNull;

public class WhereInspection extends BaseLabelInspection {

    @NotNull
    protected SpockLabel getSpockLabel() {
        return SpockLabel.WHERE;
    }

    @NotNull
    public String getShortName() {
        return "SpWhereInspection";
    }

    @NotNull
    public String getDisplayName() {
        return "Where block";
    }

}
