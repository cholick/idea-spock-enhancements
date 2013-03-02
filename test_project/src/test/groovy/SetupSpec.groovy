
class SetupSpec extends Specification {

    def test() {
        expect:
        a < 5
        where:
        a << [1, 2]
        and:
        a << [3, 4]
    }

    def "Test setup spec"() {
        setup:

        println 'hello'

        expect:

        println 'hello'

    }

    def "Test given spec"() {
        given:

        println 'hello'

        given:
        println 'hello'

    }

}
