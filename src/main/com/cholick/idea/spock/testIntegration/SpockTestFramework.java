package com.cholick.idea.spock.testIntegration;

import com.cholick.idea.spock.GroovyIcons;
import com.cholick.idea.spock.util.SpockConstants;
import com.intellij.execution.junit.JUnitUtil;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateDescriptor;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.lang.Language;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.projectRoots.ex.JavaSdkUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.JVMElementFactory;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.util.InheritanceUtil;
import com.intellij.psi.util.PsiUtil;
import com.intellij.testIntegration.JavaTestFramework;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.groovy.GroovyFileType;
import org.jetbrains.plugins.groovy.lang.psi.GroovyPsiElementFactory;

import javax.swing.*;


/**
 * User: fpape
 * Date: 6/6/13
 */
public class SpockTestFramework extends JavaTestFramework {
    private static final Logger LOG = Logger.getInstance(SpockTestFramework.class);


    @Override
    protected String getMarkerClassFQName() {
        return SpockConstants.SPOCK_BASE_CLASS;
    }

    @Override
    protected boolean isTestClass(PsiClass clazz, boolean canBePotential) {
        return clazz.getLanguage() == GroovyFileType.GROOVY_LANGUAGE &&
                InheritanceUtil.isInheritor(clazz, SpockConstants.SPOCK_BASE_CLASS);
    }

    @Override
    protected PsiMethod findSetUpMethod(@NotNull PsiClass clazz) {
        for (PsiMethod method : clazz.getMethods()) {
            if (method.getName().equals("setup")) return method;
        }
        return null;
    }

    @Override
    protected PsiMethod findTearDownMethod(@NotNull PsiClass clazz) {
        for (PsiMethod method : clazz.getMethods()) {
            if (method.getName().equals("cleanup")) return method;
        }
        return null;
    }

    //todo: taken from 12 implementation, remove when retire 11.1 compatibility
    public PsiMethod createSetUpPatternMethod(JVMElementFactory factory) {
        final FileTemplate template = FileTemplateManager.getInstance().getCodeTemplate(getSetUpMethodFileTemplateDescriptor().getFileName());
        return factory.createMethodFromText(StringUtil.replace(template.getText(), "${BODY}\n", ""), null);
    }

    @Override
    protected PsiMethod findOrCreateSetUpMethod(PsiClass clazz) throws IncorrectOperationException {
        LOG.assertTrue(clazz.getLanguage() == GroovyFileType.GROOVY_LANGUAGE);
        final GroovyPsiElementFactory factory = GroovyPsiElementFactory.getInstance(clazz.getProject());

        final PsiMethod patternMethod = createSetUpPatternMethod(factory);

        final PsiClass baseClass = clazz.getSuperClass();
        if (baseClass != null) {
            final PsiMethod baseMethod = baseClass.findMethodBySignature(patternMethod, false);
            if (baseMethod != null && baseMethod.hasModifierProperty(PsiModifier.PUBLIC)) {
                PsiUtil.setModifierProperty(patternMethod, PsiModifier.PROTECTED, false);
                PsiUtil.setModifierProperty(patternMethod, PsiModifier.PUBLIC, true);
            }
        }

        PsiMethod inClass = clazz.findMethodBySignature(patternMethod, false);
        if (inClass == null) {
            PsiMethod testMethod = JUnitUtil.findFirstTestMethod(clazz);
            if (testMethod != null) {
                return (PsiMethod) clazz.addBefore(patternMethod, testMethod);
            }
            return (PsiMethod) clazz.add(patternMethod);
        } else if (inClass.getBody() == null) {
            return (PsiMethod) inClass.replace(patternMethod);
        }
        return inClass;
    }

    @NotNull
    @Override
    public String getName() {
        return "Spock";
    }

    @NotNull
    @Override
    public Icon getIcon() {
        return GroovyIcons.getInstance().getGroovy16Icon();
    }

    @NotNull
    @Override
    public String getLibraryPath() {
        return JavaSdkUtil.getJunit3JarPath();
    }

    @Override
    public String getDefaultSuperClass() {
        return SpockConstants.SPOCK_BASE_CLASS;
    }

    @Override
    public FileTemplateDescriptor getSetUpMethodFileTemplateDescriptor() {
        return new FileTemplateDescriptor("Spock SetUp Method.groovy");
    }

    public FileTemplateDescriptor getTearDownMethodFileTemplateDescriptor() {
        return new FileTemplateDescriptor("Spock CleanUp Method.groovy");
    }

    public FileTemplateDescriptor getTestMethodFileTemplateDescriptor() {
        return new FileTemplateDescriptor("Spock Test Method.groovy");
    }

    @Override
    public boolean isTestMethod(PsiElement element) {
        return element instanceof PsiMethod && JUnitUtil.getTestMethod(element) != null;
    }

    @Override
    @NotNull
    public Language getLanguage() {
        return GroovyFileType.GROOVY_LANGUAGE;
    }

    //only @Override in 13+
    public char getMnemonic() {
        return 'S';
    }

}
