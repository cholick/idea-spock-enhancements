import spock.lang.Specification

class BigBrotherSpec extends Specification {

    def "Two plus two equals five"() {
        when:
        def two = 2

        expect:
        two + two == 5
    }

}
