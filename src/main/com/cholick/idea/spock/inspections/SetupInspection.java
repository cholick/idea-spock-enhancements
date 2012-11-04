package com.cholick.idea.spock.inspections;

import com.cholick.idea.spock.data.SpockLabel;
import org.jetbrains.annotations.NotNull;

public class SetupInspection extends BaseLabelInspection {

    public SetupInspection() {
        super();
    }

    public SetupInspection(boolean overrideSpockClassCheck) {
        super();
        this.overrideSpockClassCheck = overrideSpockClassCheck;
    }

    @NotNull
    protected SpockLabel getSpockLabel() {
        return SpockLabel.SETUP;
    }

    @NotNull
    public String getShortName() {
        return "SpSetupInspection";
    }

    @NotNull
    public String getDisplayName() {
        return "Setup block";
    }

}
