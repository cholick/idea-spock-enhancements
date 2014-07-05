package com.cholick.idea.spock.template;

import com.cholick.idea.spock.LanguageLookup;
import com.cholick.idea.spock.util.SpockClassCheck;
import com.intellij.codeInsight.template.TemplateContextType;
import com.intellij.lang.Language;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtilBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.blocks.GrCodeBlock;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.typedef.GrTypeDefinition;

public class SpockTemplateContextType extends TemplateContextType {
    public SpockTemplateContextType() {
        super("SPOCK_SPECIFICATION", "Spock Specification");
    }

    @Override
    public boolean isInContext(@NotNull PsiFile file, int offset) {
        Language groovy = LanguageLookup.getInstance().groovy();
        if (PsiUtilBase.getLanguageAtOffset(file, offset).isKindOf(groovy)) {
            PsiElement element = file.findElementAt(offset);
            if (element == null || element instanceof PsiWhiteSpace) {
                return false;
            }

            if (PsiTreeUtil.getParentOfType(element, GrCodeBlock.class, false, GrTypeDefinition.class) != null) {
                return false;
            }

            if (element instanceof PsiComment) {
                return false;
            }

            return new SpockClassCheck(element).getIsSpockClass();
        }

        return false;
    }

}
