package org.checkerframework.checker.fenum.qual;

import java.lang.annotation.*;

import org.checkerframework.framework.qual.*;

/**
 * Justifications for the title text of a
 * {@link javax.swing.border.TitledBorder}.
 * @see javax.swing.border.TitledBorder#DEFAULT_POSITION
 * @see javax.swing.border.TitledBorder#ABOVE_TOP
 * @see javax.swing.border.TitledBorder#TOP
 * @see javax.swing.border.TitledBorder#BELOW_TOP
 * @see javax.swing.border.TitledBorder#ABOVE_BOTTOM
 * @see javax.swing.border.TitledBorder#BOTTOM
 * @see javax.swing.border.TitledBorder#BELOW_BOTTOM
 * @checker_framework.manual #fenum-checker Fake Enum Checker
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@TypeQualifier
@SubtypeOf(FenumTop.class)
public @interface SwingTitlePosition {}
