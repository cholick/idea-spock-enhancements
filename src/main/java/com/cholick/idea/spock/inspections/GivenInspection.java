package com.cholick.idea.spock.inspections;

import com.cholick.idea.spock.data.SpockLabel;
import org.jetbrains.annotations.NotNull;

public class GivenInspection extends BaseLabelInspection {

    @NotNull
    protected SpockLabel getSpockLabel() {
        return SpockLabel.GIVEN;
    }

    @NotNull
    public String getShortName() {
        return "SpGivenInspection";
    }

    @NotNull
    public String getDisplayName() {
        return "Given block";
    }

}
