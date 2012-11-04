package com.cholick.idea.spock.util;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

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

    public boolean getIsSpockClass() {
        return isSpockClass;
    }

    private boolean determineSpockClass(PsiClass superClass) {
        boolean hasSpecificationParent = false;
        while (superClass != null && !hasSpecificationParent) {
            hasSpecificationParent = "spock.lang.Specification".equals(superClass.getQualifiedName());
            superClass = superClass.getSuperClass();
        }
        return hasSpecificationParent;
    }

}
