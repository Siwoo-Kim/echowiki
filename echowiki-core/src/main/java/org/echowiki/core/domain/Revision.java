package org.echowiki.core.domain;

public interface Revision {

    boolean isTrunk();

    Revision getMaster();

    String getVersion();

    String commitBy();

}
