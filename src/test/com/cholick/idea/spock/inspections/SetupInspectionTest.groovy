package com.cholick.idea.spock.inspections

import org.jetbrains.plugins.groovy.GroovyFileType

class SetupInspectionTest extends BaseInspectionTest {

    public void testNoHighlight() {
        String spockMethod = makeSpecFile('''
            def test() {
                setup:
                def a = 1
                expect:
                a == 1
            }
        ''')

        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, spockMethod)
        BaseLabelInspection inspection = new SetupInspection()
        myFixture.enableInspections(inspection)

        assertFalse(hasHighlightingFor('expect', myFixture.doHighlighting(), inspection))
    }

    public void testHighlightForGiven() {
        String spockMethod = makeSpecFile('''
            def test() {
                setup:
                def a = 1
                given:
                a == 1
            }
        ''')

        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, spockMethod)
        BaseLabelInspection inspection = new SetupInspection()
        myFixture.enableInspections(inspection)

        assertTrue(hasHighlightingFor('given', myFixture.doHighlighting(), inspection))
    }

}
