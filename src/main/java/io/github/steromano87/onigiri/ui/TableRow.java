package io.github.steromano87.onigiri.ui;

public interface TableRow extends ExtendedElement {
    String getCell(int index);

    String getCell(String column);

    int cellCount();
}
