package com.cholick.idea.spock.inspections;

import org.jetbrains.plugins.groovy.GroovyFileType;

public class SetupInspectionTest extends BaseInspectionTest {

    public void testNoHighlight() {
        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, "" +
                "    def test() {\n" +
                "        setup:\n" +
                "        def a = 1\n" +
                "        expect:\n" +
                "        a == 1\n" +
                "    }"
        );
        BaseLabelInspection inspection = new SetupInspection(true);
        myFixture.enableInspections(inspection);

        assertFalse(hasHighlightingFor("expect", myFixture.doHighlighting(), inspection));
    }

    public void testHighlightForGiven() {
        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, "" +
                "    def test() {\n" +
                "        setup:\n" +
                "        def a = 1\n" +
                "        given:\n" +
                "        a == 1\n" +
                "    }"
        );
        BaseLabelInspection inspection = new SetupInspection(true);
        myFixture.enableInspections(inspection);

        assertTrue(hasHighlightingFor("given", myFixture.doHighlighting(), inspection));
    }

}
