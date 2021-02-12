package lost.canvas.micronaut_test

import spock.lang.Specification

class Locale_testSpec extends Specification {

    def 'test forLanguageTag'() {
        expect:
        println "$languageTag->" + Locale.forLanguageTag(languageTag)

        where:
        languageTag << ['zh', 'zh-CN', 'zh-CN,zh', 'en', 'en-US', 'en-US,en', 'zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6,ja;q=0.5']
    }
}
