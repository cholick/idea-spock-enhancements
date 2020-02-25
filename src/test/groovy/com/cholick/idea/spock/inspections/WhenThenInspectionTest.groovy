package com.cholick.idea.spock.inspections

import org.jetbrains.plugins.groovy.GroovyFileType

class WhenThenInspectionTest extends BaseInspectionTest {

    public void testNoHighlight() {
        String spockMethod = makeSpecFile('''
            def test() {
                when:
                def a = 1
                then:
                a == 1
            }
        ''')

        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, spockMethod)
        WhenThenInspection inspection = new WhenThenInspection()
        myFixture.enableInspections(inspection)

        assertFalse(hasHighlightingFor('then', myFixture.doHighlighting(), inspection))
    }

    public void testNoHighlightAnd() {
        String spockMethod = makeSpecFile('''
            def test() {
                when:
                def a = 1
                and:
                a = 2
                then:
                a == 2
            }
        ''')

        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, spockMethod)
        WhenThenInspection inspection = new WhenThenInspection()
        myFixture.enableInspections(inspection)

        assertFalse(hasHighlightingFor('then', myFixture.doHighlighting(), inspection))
    }

    public void testHighlightMethodEnd() {
        String spockMethod = makeSpecFile('''
            def test() {
                when:
                def a = 1
            }
        ''')

        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, spockMethod)
        WhenThenInspection inspection = new WhenThenInspection()
        myFixture.enableInspections(inspection)

        assertTrue(hasHighlightingFor('when', myFixture.doHighlighting(), inspection))
    }

}
