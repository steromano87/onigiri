/**
 * Package containing the {@link io.github.steromano87.onigiri.enhancers.Enhancer}
 * interfaces and their related annotations.
 * <p>
 * Enhancers are special classes that can modify at runtime the behaviour of a command
 * or a class. Enhancers leverage the Java {@link java.lang.reflect.Proxy} concept to intercept
 * method calls and perform additional actions. Some additional actions may include:
 * <ul>
 *     <li>Measures of method duration</li>
 *     <li>Automatic method syncing to wait for correct page loading</li>
 * </ul>
 * and so on.
 */
package io.github.steromano87.onigiri.enhancers;