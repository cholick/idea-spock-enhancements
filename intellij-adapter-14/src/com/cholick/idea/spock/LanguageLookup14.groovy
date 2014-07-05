package com.cholick.idea.spock

import com.intellij.lang.Language
import org.jetbrains.plugins.groovy.GroovyLanguage;

class LanguageLookup14 extends LanguageLookup {

    Language groovy() {
        return GroovyLanguage.INSTANCE;
    }

}
