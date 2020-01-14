package io.github.steromano87.onigiri.ui.web;

import java.util.Map;

public interface Visitable {
    void visit() throws NavigationException;

    void visit(Map<String, String> parameters) throws NavigationException;
}
