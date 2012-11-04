package com.cholick.idea.spock.inspections;

import com.cholick.idea.spock.data.SpockLabel;
import org.jetbrains.annotations.NotNull;

public class CleanupInspection extends BaseLabelInspection {

    public CleanupInspection() {
        super();
    }

    public CleanupInspection(boolean overrideSpockClassCheck) {
        super();
        this.overrideSpockClassCheck = overrideSpockClassCheck;
    }

    @NotNull
    protected SpockLabel getSpockLabel() {
        return SpockLabel.CLEANUP;
    }

    @NotNull
    public String getShortName() {
        return "SpCleanupInspection";
    }

    @NotNull
    public String getDisplayName() {
        return "Cleanup block";
    }

}
