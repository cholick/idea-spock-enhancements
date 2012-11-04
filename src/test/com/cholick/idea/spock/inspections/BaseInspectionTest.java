package com.cholick.idea.spock.inspections;

import com.intellij.codeInsight.daemon.impl.HighlightInfo;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class BaseInspectionTest extends LightCodeInsightFixtureTestCase {

    public boolean hasHighlightingFor(@NotNull String label, @NotNull List<HighlightInfo> highlightInfos, @NotNull BaseLabelInspection inspection) {
        boolean found = false;
        for (HighlightInfo highlightInfo : highlightInfos) {
            if (label.equals(highlightInfo.text) && inspection.buildErrorString().equals(highlightInfo.description)) {
                found = true;
            }
        }
        return found;
    }


}
