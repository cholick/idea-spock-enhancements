package com.cholick.idea.spock.highlight;

import com.intellij.codeInsight.daemon.impl.HighlightInfoType;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.colors.TextAttributesKey;

public class SpockLabelHighlightInfoTypes {

    private static final TextAttributesKey SPOCK_LABELS = TextAttributesKey.createTextAttributesKey("SPOCK_LABELS");

    public static final HighlightInfoType SPOCK_LABEL = new HighlightInfoType.HighlightInfoTypeImpl(HighlightSeverity.INFORMATION, SPOCK_LABELS);

}
