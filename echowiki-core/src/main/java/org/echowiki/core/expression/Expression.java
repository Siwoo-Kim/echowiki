package org.echowiki.core.expression;

import javafx.beans.Observable;

import java.util.List;

public interface Expression extends Observable {

    List<Expression> children();

    String open();

    String close();

}
