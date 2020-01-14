package io.github.steromano87.onigiri.ui;

import java.util.List;
import java.util.function.Function;

public interface Table extends ExtendedElement {
    int rowCount();

    List<TableRow> getRows();

    TableRow getRow(int index);

    List<TableRow> filter(Function<? extends TableRow, Boolean> filter);

    List<String> getColumns();
}
