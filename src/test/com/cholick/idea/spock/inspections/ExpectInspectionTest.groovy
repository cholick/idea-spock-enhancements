package com.cholick.idea.spock.inspections

import org.jetbrains.plugins.groovy.GroovyFileType

class ExpectInspectionTest extends BaseInspectionTest {

    public void testNoHighlight() {
        String spockMethod = '''
            def test() {
                given:
                expect:
                a == 1
                cleanup:
                a = null
            }
        '''

        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, spockMethod)
        BaseLabelInspection inspection = new ExpectInspection(true)
        myFixture.enableInspections(inspection)

        assertFalse(hasHighlightingFor('cleanup', myFixture.doHighlighting(), inspection))
    }

    public void testHighlightForGiven() {
        String spockMethod = '''
            def test() {
                setup:
                def a = 1
                expect:
                a == 1
                given:
                a = null
            }
        '''

        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, spockMethod)
        BaseLabelInspection inspection = new ExpectInspection(true)
        myFixture.enableInspections(inspection)

        assertTrue(hasHighlightingFor('given', myFixture.doHighlighting(), inspection))
    }

    public void testHighlightForThen() {
        String spockMethod = '''
            def test() {
                given:
                def a = 1
                expect:
                a == 1
                then:
                a = null
            }
        '''

        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, spockMethod)
        BaseLabelInspection inspection = new ExpectInspection(true)
        myFixture.enableInspections(inspection)

        assertTrue(hasHighlightingFor('then', myFixture.doHighlighting(), inspection))
    }

}
