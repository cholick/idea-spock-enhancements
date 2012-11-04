package com.cholick.idea.spock.inspections;

import org.jetbrains.plugins.groovy.GroovyFileType;

public class GivenInspectionTest extends BaseInspectionTest {

    public void testNoHighlight() {
        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, "" +
                "    def test() {\n" +
                "        given:\n" +
                "        def a = 1\n" +
                "        expect:\n" +
                "        a == 1\n" +
                "    }"
        );
        BaseLabelInspection inspection = new GivenInspection(true);
        myFixture.enableInspections(inspection);

        assertFalse(hasHighlightingFor("expect", myFixture.doHighlighting(), inspection));
    }

    public void testHighlightForSetup() {
        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, "" +
                "    def test() {\n" +
                "        given:\n" +
                "        def a = 1\n" +
                "        setup:\n" +
                "        a == 1\n" +
                "    }"
        );
        BaseLabelInspection inspection = new GivenInspection(true);
        myFixture.enableInspections(inspection);

        assertTrue(hasHighlightingFor("setup", myFixture.doHighlighting(), inspection));
    }


}
