package com.github.steromano87.onigiri.ui;

import java.util.List;

public interface Select extends ExtendedElement {
    void selectAll();

    void selectByIndex(int index);

    void selectByValue(String value);

    void selectByText(String text);

    void deselectByIndex(int index);

    void deselectByValue(String value);

    void deselectByText(String text);

    String getSelectedOption();

    List<String> getSelectedOptions();

    List<String> getAvailableOptions();

    boolean isMultiple();
}
