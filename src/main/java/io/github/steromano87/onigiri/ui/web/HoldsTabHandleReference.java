package io.github.steromano87.onigiri.ui.web;

public interface HoldsTabHandleReference {
    void returnToOwnTab() throws NavigationException;

    void registerTabHandle();
}
