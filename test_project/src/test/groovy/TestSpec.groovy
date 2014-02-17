import spock.lang.Specification
import spock.lang.Unroll

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

    def "When only"() {
        when:
        def b = 2

        and:
        def a = b
    }


    def "Parameterization (positive)"() {
        expect:
        a
        where:
        a << [0, 1, 2]
    }

    @Unroll
    def "Parameterization (negative)"() {
        expect:
        a
        where:
        a << [0, 1, 2]
    }

}
