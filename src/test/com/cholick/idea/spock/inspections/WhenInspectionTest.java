package com.cholick.idea.spock.inspections;

import org.jetbrains.plugins.groovy.GroovyFileType;

public class WhenInspectionTest extends BaseInspectionTest {

    public void testNoHighlight() {
        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, "" +
                "    def test() {\n" +
                "        when:\n" +
                "        def a = 1\n" +
                "        then:\n" +
                "        a == 1\n" +
                "    }"
        );
        BaseLabelInspection inspection = new WhenInspection();
        myFixture.enableInspections(inspection);

        assertFalse(hasHighlightingFor("then", myFixture.doHighlighting(), inspection));
    }

    public void testHighlightForCleanup() {
        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, "" +
                "    def test() {\n" +
                "        when:\n" +
                "        def a = 1\n" +
                "        cleanup:\n" +
                "        a == 1\n" +
                "    }"
        );
        BaseLabelInspection inspection = new WhenInspection();
        myFixture.enableInspections(inspection);

        assertTrue(hasHighlightingFor("cleanup", myFixture.doHighlighting(), inspection));
    }

    public void testHighlightForExpect() {
        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, "" +
                "    def test() {\n" +
                "        when:\n" +
                "        def a = 1\n" +
                "        expect:\n" +
                "        a == 1\n" +
                "    }"
        );
        BaseLabelInspection inspection = new WhenInspection();
        myFixture.enableInspections(inspection);

        assertTrue(hasHighlightingFor("expect", myFixture.doHighlighting(), inspection));
    }

}
