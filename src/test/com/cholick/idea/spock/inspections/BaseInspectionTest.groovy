package com.cholick.idea.spock.inspections

import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase
import org.jetbrains.annotations.NotNull
import com.intellij.codeInsight.daemon.impl.HighlightInfo
import org.jetbrains.plugins.groovy.codeInspection.BaseInspection

abstract class BaseInspectionTest extends LightCodeInsightFixtureTestCase {

    public boolean hasHighlightingFor(@NotNull String label, @NotNull List<HighlightInfo> highlightInfos, @NotNull BaseInspection inspection) {
        boolean found = false
        highlightInfos.each { highlightInfo ->
            if (label.equals(highlightInfo.text) && inspection.buildErrorString().equals(highlightInfo.description)) {
                found = true
            }
        }
        return found
    }

    protected String makeSpecFile(String method) {
        return """
            package spock.lang
            class Specification { }

            class Spec extends Specification {
                $method
            }"""
    }

}
