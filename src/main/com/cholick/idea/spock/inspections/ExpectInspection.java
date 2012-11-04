package com.cholick.idea.spock.inspections;

import com.cholick.idea.spock.data.SpockLabel;
import org.jetbrains.annotations.NotNull;

public class ExpectInspection extends BaseLabelInspection {

    public ExpectInspection() {
        super();
    }

    public ExpectInspection(boolean overrideSpockClassCheck) {
        super();
        this.overrideSpockClassCheck = overrideSpockClassCheck;
    }

    @NotNull
    protected SpockLabel getSpockLabel() {
        return SpockLabel.EXPECT;
    }

    @NotNull
    public String getShortName() {
        return "SpExpectInspection";
    }

    @NotNull
    public String getDisplayName() {
        return "Expect block";
    }

}
