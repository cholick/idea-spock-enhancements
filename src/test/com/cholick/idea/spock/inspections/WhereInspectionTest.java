package com.cholick.idea.spock.inspections;

import org.jetbrains.plugins.groovy.GroovyFileType;

public class WhereInspectionTest extends BaseInspectionTest {

    public void testNoHighlight() {
        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, "" +
                "    def test() {\n" +
                "        expect:\n" +
                "        a < 5\n" +
                "        where:\n" +
                "        a << [1, 2]\n" +
                "        and:\n" +
                "        a << [3, 4]\n" +
                "    }"
        );
        BaseLabelInspection inspection = new WhereInspection(true);
        myFixture.enableInspections(inspection);

        assertFalse(hasHighlightingFor("and", myFixture.doHighlighting(), inspection));
    }

    public void testHighlightForCleanup() {
        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, "" +
                "    def test() {\n" +
                "        expect:\n" +
                "        a < 5\n" +
                "        where:\n" +
                "        a << [1, 2]\n" +
                "        cleanup:\n" +
                "        a = null\n" +
                "    }"
        );
        BaseLabelInspection inspection = new WhereInspection(true);
        myFixture.enableInspections(inspection);

        assertTrue(hasHighlightingFor("cleanup", myFixture.doHighlighting(), inspection));
    }

}
