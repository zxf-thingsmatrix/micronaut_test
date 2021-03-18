package lost.canvas.micronaut_test

import lost.canvas.micronaut_test.entity.User
import spock.lang.Specification

class User_testSpec extends Specification {

    def 'test 继承equals'() {
        given:
        User u1 = User.builder().id(1L).build()
        User u2 = User.builder().id(1L).build()
        expect:
        u1 == u2
    }
}
