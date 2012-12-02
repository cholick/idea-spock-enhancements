package com.cholick.idea.spock.inspections

import org.jetbrains.plugins.groovy.GroovyFileType

class WhenInspectionTest extends BaseInspectionTest {

    public void testNoHighlight() {
        String spockMethod = '''
            def test() {
                when:
                def a = 1
                then:
                a == 1
            }
        '''

        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, spockMethod)
        BaseLabelInspection inspection = new WhenInspection(true)
        myFixture.enableInspections(inspection)

        assertFalse(hasHighlightingFor('then', myFixture.doHighlighting(), inspection))
    }

    public void testHighlightForCleanup() {
        String spockMethod = '''
            def test() {
                when:
                def a = 1
                cleanup:
                a == 1
            }
        '''

        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, spockMethod)
        BaseLabelInspection inspection = new WhenInspection(true)
        myFixture.enableInspections(inspection)

        assertTrue(hasHighlightingFor('cleanup', myFixture.doHighlighting(), inspection))
    }

    public void testHighlightForExpect() {
        String spockMethod = '''
            def test() {
                when:
                def a = 1
                expect:
                a == 1
            }
        '''

        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, spockMethod)
        BaseLabelInspection inspection = new WhenInspection(true)
        myFixture.enableInspections(inspection)

        assertTrue(hasHighlightingFor('expect', myFixture.doHighlighting(), inspection))
    }

}
