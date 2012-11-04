package com.cholick.idea.spock.inspections;

import org.jetbrains.plugins.groovy.GroovyFileType;

public class ThenInspectionTest extends BaseInspectionTest {

    public void testNoHighlight() {
        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, "" +
                "    def test() {\n" +
                "        when:\n" +
                "        def a = 1\n" +
                "        then:\n" +
                "        a == 1\n" +
                "        cleanup:\n" +
                "        a = null\n" +
                "    }"
        );
        BaseLabelInspection inspection = new ThenInspection(true);
        myFixture.enableInspections(inspection);

        assertFalse(hasHighlightingFor("cleanup", myFixture.doHighlighting(), inspection));
    }

    public void testHighlightForSetup() {
        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, "" +
                "    def test() {\n" +
                "        when:\n" +
                "        def a = 1\n" +
                "        then:\n" +
                "        a == 1\n" +
                "        setup:\n" +
                "        a = null\n" +
                "    }"
        );
        BaseLabelInspection inspection = new ThenInspection(true);
        myFixture.enableInspections(inspection);

        assertTrue(hasHighlightingFor("setup", myFixture.doHighlighting(), inspection));
    }

}
