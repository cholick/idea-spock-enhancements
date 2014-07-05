package com.cholick.idea.spock

import com.intellij.lang.Language
import org.jetbrains.plugins.groovy.GroovyFileType

public class LanguageLookup11 extends LanguageLookup {

    Language groovy() {
        return GroovyFileType.GROOVY_LANGUAGE;
    }

}
