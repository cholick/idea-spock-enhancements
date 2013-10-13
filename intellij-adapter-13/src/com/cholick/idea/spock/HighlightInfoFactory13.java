package com.cholick.idea.spock;

import com.intellij.codeInsight.daemon.impl.HighlightInfo;
import com.intellij.codeInsight.daemon.impl.HighlightInfoType;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.codeInsight.daemon.impl.HighlightInfo.Builder;

public class HighlightInfoFactory13 extends HighlightInfoFactory {

    @Override
    public HighlightInfo createHighlightInfo(@NotNull HighlightInfoType type, @NotNull PsiElement element, @Nullable String message, @Nullable TextAttributes attributes) {
        Builder builder = HighlightInfo.newHighlightInfo(type)
                .range(element);
        if(message != null) {
            builder.description(message);
        }
        if(attributes != null) {
            builder.textAttributes(attributes);
        }
        return builder.create();
    }
}
