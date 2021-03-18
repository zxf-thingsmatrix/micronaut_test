package lost.canvas.micronaut_test

import io.micronaut.core.naming.NameUtils
import spock.lang.Specification

class NameUtils_testSpec extends Specification {

    def 'test camel to underscore'() {
        expect:
        NameUtils.underscoreSeparate('userId').toLowerCase(Locale.ENGLISH) == 'user_id'
    }
}
