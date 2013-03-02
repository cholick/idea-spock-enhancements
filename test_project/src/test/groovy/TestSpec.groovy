import spock.lang.Specification

class TestSpec extends Specification {

    def "Some passing specification"() {
        //before
        given: //after
        def a = 4

        when:
        def b = a * a

        then:
        b == 4 * 4
    }

    def "Some failing specification"() {
        expect:
        3 == 2
    }

    def "Paramaterized"() {
        expect:
        a < 4

        where:
        a << [1, 2, 3]
    }

    def "Test cleanup"() {
        cleanup:
        println 'a'

        cleanup:
        println 'b'
    }

    def "Test setup"() {
        setup:

        given:


    }

}
