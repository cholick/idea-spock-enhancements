package com.cholick.idea.spock.config;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.ui.ColorPanel;
import org.intellij.images.options.Options;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpockConfigForm implements SearchableConfigurable {

    private JPanel myPanel;

    private ColorPanel labelColor;
    private JButton defaultsButton;
    private JCheckBox labelBold;
    private JCheckBox labelItalics;

    private SpockConfig spockConfig;

    public SpockConfigForm() {
        spockConfig = SpockConfig.getInstance();
        defaultsButton.addActionListener(new DefaultsButtonActionListener());
    }

    public ColorPanel getLabelColor() {
        return labelColor;
    }

    public JButton getDefaultsButton() {
        return defaultsButton;
    }

    public JCheckBox getLabelBold() {
        return labelBold;
    }

    public JCheckBox getLabelItalics() {
        return labelItalics;
    }

    @Override
    public String getDisplayName() {
        return "Spock";
    }

    @Override
    public Icon getIcon() {
        return null;
    }

    @Override
    @NotNull
    public String getHelpTopic() {
        return "preferences.lookFeel";
    }

    @Override
    public JComponent createComponent() {
        setupFromConfig();
        return myPanel;
    }

    @Override
    public void reset() {
        setupFromConfig();
    }

    private void setupFromConfig() {
        labelBold.setSelected(spockConfig.labelBold);
        labelItalics.setSelected(spockConfig.labelItalics);
        labelColor.setSelectedColor(spockConfig.labelColor);
    }

    @Override
    public boolean isModified() {
        return labelBold.isSelected() != spockConfig.labelBold
                || labelItalics.isSelected() != spockConfig.labelItalics
                || !labelColor.getSelectedColor().equals(spockConfig.labelColor);
    }

    @Override
    public void apply() throws ConfigurationException {
        spockConfig.labelBold = labelBold.isSelected();
        spockConfig.labelItalics = labelItalics.isSelected();
        spockConfig.labelColor = labelColor.getSelectedColor();
    }


    @Override
    public void disposeUIResources() {
    }

    @NotNull
    @Override
    public String getId() {
        return getHelpTopic();
    }

    @Override
    public Runnable enableSearch(String option) {
        return null;
    }

    class DefaultsButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            SpockConfig defaultConfig = new SpockConfig();
            labelBold.setSelected(defaultConfig.labelBold);
            labelItalics.setSelected(defaultConfig.labelItalics);
            labelColor.setSelectedColor(defaultConfig.labelColor);
        }
    }

}
