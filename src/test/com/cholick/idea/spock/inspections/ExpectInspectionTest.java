package com.cholick.idea.spock.inspections;

import org.jetbrains.plugins.groovy.GroovyFileType;

public class ExpectInspectionTest extends BaseInspectionTest {

    public void testNoHighlight() {
        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, "" +
                "    def test() {\n" +
                "        given:\n" +
                "        def a = 1\n" +
                "        expect:\n" +
                "        a == 1\n" +
                "        cleanup:\n" +
                "        a = null\n" +
                "    }"
        );
        BaseLabelInspection inspection = new ExpectInspection();
        myFixture.enableInspections(inspection);

        assertFalse(hasHighlightingFor("cleanup", myFixture.doHighlighting(), inspection));
    }

    public void testHighlightForGiven() {
        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, "" +
                "    def test() {\n" +
                "        setup:\n" +
                "        def a = 1\n" +
                "        expect:\n" +
                "        a == 1\n" +
                "        given:\n" +
                "        a = null\n" +
                "    }"
        );
        BaseLabelInspection inspection = new ExpectInspection();
        myFixture.enableInspections(inspection);

        assertTrue(hasHighlightingFor("given", myFixture.doHighlighting(), inspection));
    }

    public void testHighlightForThen() {
        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, "" +
                "    def test() {\n" +
                "        given:\n" +
                "        def a = 1\n" +
                "        expect:\n" +
                "        a == 1\n" +
                "        then:\n" +
                "        a = null\n" +
                "    }"
        );
        BaseLabelInspection inspection = new ExpectInspection();
        myFixture.enableInspections(inspection);

        assertTrue(hasHighlightingFor("then", myFixture.doHighlighting(), inspection));
    }

}
