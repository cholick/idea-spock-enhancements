package com.cholick.idea.spock.inspections

import org.jetbrains.plugins.groovy.GroovyFileType

class ThenInspectionTest extends BaseInspectionTest {

    public void testNoHighlight() {
        String spockMethod = '''
            def test() {
                when:
                def a = 1
                then:
                a == 1
                cleanup:
                a = null
            }
        '''

        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, spockMethod)
        BaseLabelInspection inspection = new ThenInspection(true)
        myFixture.enableInspections(inspection)

        assertFalse(hasHighlightingFor('cleanup', myFixture.doHighlighting(), inspection))
    }

    public void testHighlightForSetup() {
        String spockMethod = '''
            def test() {
                when:
                def a = 1
                then:
                a == 1
                setup:
                a = null
            }
        '''

        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, spockMethod)
        BaseLabelInspection inspection = new ThenInspection(true)
        myFixture.enableInspections(inspection)

        assertTrue(hasHighlightingFor('setup', myFixture.doHighlighting(), inspection))
    }

}
