package com.cholick.idea.spock.inspections;

import com.cholick.idea.spock.data.SpockLabel;
import org.jetbrains.annotations.NotNull;

public class WhenInspection extends BaseLabelInspection {

    public WhenInspection() {
        super();
    }

    public WhenInspection(boolean overrideSpockClassCheck) {
        super();
        this.overrideSpockClassCheck = overrideSpockClassCheck;
    }

    @NotNull
    protected SpockLabel getSpockLabel() {
        return SpockLabel.WHEN;
    }

    @NotNull
    public String getShortName() {
        return "SpWhenInspection";
    }

    @NotNull
    public String getDisplayName() {
        return "When block";
    }

}
