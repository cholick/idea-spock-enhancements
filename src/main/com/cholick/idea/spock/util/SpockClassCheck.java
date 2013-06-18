package com.cholick.idea.spock.util;

import com.cholick.idea.spock.testIntegration.SpockTestFramework;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;

public class SpockClassCheck {

    private boolean isSpockClass;

    public SpockClassCheck(PsiFile file) {
        isSpockClass = false;
        for (PsiElement child : file.getChildren()) {
            if (child instanceof PsiClass) {
                isSpockClass = isSpockClass || determineSpockClass((PsiClass) child);
            }
        }
    }

    public SpockClassCheck(PsiElement element) {
        isSpockClass = false;
        PsiClass topLevelClass = (PsiClass) PsiTreeUtil.findFirstParent(element, true, new Condition<PsiElement>() {
            @Override
            public boolean value(PsiElement psiElement) {
                return psiElement instanceof PsiClass;
            }
        });

        if (topLevelClass != null) {
            isSpockClass = determineSpockClass(topLevelClass);
        }
    }

    public boolean getIsSpockClass() {
        return isSpockClass;
    }

    private boolean determineSpockClass(PsiClass superClass) {
        boolean hasSpecificationParent = false;
        while (superClass != null && !hasSpecificationParent) {
            hasSpecificationParent = SpockConstants.SPOCK_BASE_CLASS.equals(superClass.getQualifiedName());
            superClass = superClass.getSuperClass();
        }
        return hasSpecificationParent;
    }

}
