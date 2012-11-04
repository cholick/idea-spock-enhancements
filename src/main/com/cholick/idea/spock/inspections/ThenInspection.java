package com.cholick.idea.spock.inspections;

import com.cholick.idea.spock.data.SpockLabel;
import org.jetbrains.annotations.NotNull;

public class ThenInspection extends BaseLabelInspection {

    @NotNull
    protected SpockLabel getSpockLabel() {
        return SpockLabel.THEN;
    }

    @NotNull
    public String getShortName() {
        return "SpThenInspection";
    }

    @NotNull
    public String getDisplayName() {
        return "Then block";
    }

}
