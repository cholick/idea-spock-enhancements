package com.cholick.idea.spock.inspections;

import org.jetbrains.plugins.groovy.GroovyFileType;

public class CleanupInspectionTest extends BaseInspectionTest {

    public void _testNoHighlight() {
        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, "" +
                " class Test extends spock.lang.Specification {" +
                "    def test() {\n" +
                "        setup:\n" +
                "        def a = 1\n" +
                "        expect:\n" +
                "        a == 1\n" +
                "        cleanup:\n" +
                "        a = newValue\n" +
                "        where:\n" +
                "        newValue << [null]\n" +
                "    } \n" +
                "} "

        );
        BaseLabelInspection inspection = new CleanupInspection(true);
        myFixture.enableInspections(inspection);

        assertFalse(hasHighlightingFor("where", myFixture.doHighlighting(), inspection));
    }

    public void testHighlightForExpect() {
        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, "" +
                "    def test() {\n" +
                "        setup:\n" +
                "        def a = 1\n" +
                "        expect:\n" +
                "        a == 1\n" +
                "        cleanup:\n" +
                "        a = newValue\n" +
                "        expect:\n" +
                "        a == null\n" +
                "    }\n"
        );
        BaseLabelInspection inspection = new CleanupInspection(true);
        myFixture.enableInspections(inspection);

        assertTrue(hasHighlightingFor("expect", myFixture.doHighlighting(), inspection));
    }

}
