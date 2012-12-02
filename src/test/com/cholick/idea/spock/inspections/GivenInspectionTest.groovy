package com.cholick.idea.spock.inspections

import org.jetbrains.plugins.groovy.GroovyFileType

class GivenInspectionTest extends BaseInspectionTest {

    public void testNoHighlight() {
        String spockMethod = '''
            def test() {
                given:
                def a = 1
                expect:
                a == 1
            }
        '''

        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, spockMethod)
        BaseLabelInspection inspection = new GivenInspection(true)
        myFixture.enableInspections(inspection)

        assertFalse(hasHighlightingFor('expect', myFixture.doHighlighting(), inspection))
    }

    public void testHighlightForSetup() {
        String spockMethod = '''
            def test() {
                given:
                def a = 1
                setup:
                a = 1
            }
        '''

        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, spockMethod)
        BaseLabelInspection inspection = new GivenInspection(true)
        myFixture.enableInspections(inspection)

        assertTrue(hasHighlightingFor('setup', myFixture.doHighlighting(), inspection))
    }

}
