package com.github.steromano87.onigiri.ui.web;

public interface HoldsTabHandleReference {
    void returnToOwnTab() throws NavigationException;

    void registerTabHandle();
}
