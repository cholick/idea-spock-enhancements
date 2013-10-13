package com.cholick.idea.spock;

import com.intellij.codeInsight.daemon.impl.HighlightInfo;
import com.intellij.codeInsight.daemon.impl.HighlightInfoType;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HighlightInfoFactory11 extends HighlightInfoFactory {

    public HighlightInfo createHighlightInfo(@NotNull HighlightInfoType type, @NotNull PsiElement element, @Nullable String message, @Nullable TextAttributes attributes) {
        return HighlightInfo.createHighlightInfo(type, element, message, attributes);
    }

}
