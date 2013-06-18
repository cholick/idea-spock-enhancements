package com.cholick.idea.spock.template;

import com.intellij.ide.fileTemplates.*;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.util.ArrayUtil;
import com.intellij.util.IncorrectOperationException;
import icons.JetgroovyIcons;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

/**
 * User: fpape
 * Date: 6/18/13
 * Time: 9:41 AM
 */
public class SpockTemplatesFactory implements FileTemplateGroupDescriptorFactory {

    public void registerCustromTemplates(String... templates) {
        Collections.addAll(myCustomTemplates, templates);
    }

    private static class SpockTemplatesFactoryHolder {
        private static final SpockTemplatesFactory myInstance = new SpockTemplatesFactory();
    }

    public static SpockTemplatesFactory getInstance() {
        return SpockTemplatesFactoryHolder.myInstance;
    }

    private final ArrayList<String> myCustomTemplates = new ArrayList<String>();

    @NonNls
    static final String NAME_TEMPLATE_PROPERTY = "NAME";
    static final String LOW_CASE_NAME_TEMPLATE_PROPERTY = "lowCaseName";

    public FileTemplateGroupDescriptor getFileTemplatesDescriptor() {
        final FileTemplateGroupDescriptor group = new FileTemplateGroupDescriptor("Spock", JetgroovyIcons.Groovy.Groovy_16x16);
        final FileTypeManager fileTypeManager = FileTypeManager.getInstance();
        group.addTemplate(new FileTemplateDescriptor(SpockTemplates.SPOCK_SPEC, fileTypeManager.getFileTypeByFileName(SpockTemplates.SPOCK_SPEC).getIcon()));

        // register custom templates
        for (String template : getInstance().getCustomTemplates()) {
            group.addTemplate(new FileTemplateDescriptor(template, fileTypeManager.getFileTypeByFileName(template).getIcon()));
        }
        return group;
    }


    public static PsiFile createFromTemplate(@NotNull final PsiDirectory directory,
                                             @NotNull final String name,
                                             @NotNull String fileName,
                                             @NotNull String templateName,
                                             @NonNls String... parameters) throws IncorrectOperationException {
        final FileTemplate template = FileTemplateManager.getInstance().getInternalTemplate(templateName);

        Properties properties = new Properties(FileTemplateManager.getInstance().getDefaultProperties(directory.getProject()));
        JavaTemplateUtil.setPackageNameAttribute(properties, directory);
        properties.setProperty(NAME_TEMPLATE_PROPERTY, name);
        properties.setProperty(LOW_CASE_NAME_TEMPLATE_PROPERTY, name.substring(0, 1).toLowerCase() + name.substring(1));
        for (int i = 0; i < parameters.length; i += 2) {
            properties.setProperty(parameters[i], parameters[i + 1]);
        }
        String text;
        try {
            text = template.getText(properties);
        } catch (Exception e) {
            throw new RuntimeException("Unable to load template for " + FileTemplateManager.getInstance().internalTemplateToSubject(templateName),
                    e);
        }

        final PsiFileFactory factory = PsiFileFactory.getInstance(directory.getProject());
        final PsiFile file = factory.createFileFromText(fileName, text);

        return (PsiFile) directory.add(file);
    }

    public String[] getCustomTemplates() {
        return ArrayUtil.toStringArray(myCustomTemplates);
    }
}
