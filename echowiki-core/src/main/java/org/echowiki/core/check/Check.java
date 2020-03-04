package org.echowiki.core.check;

/**
 * Verify invariant state of the domain.
 *
 * @param <Domain>
 */
public interface Check<Domain> {

    <S extends Domain> void checkRelation(S association);

    /**
     * verify invariant of the properties in the Domain class.
     * Note that invariant is more about memory state and  should be not related
     * with persistent state.
     * Note that checking invariant state should not be constructor.
     *
     * @throws IllegalArgumentException if any invariants of the Domain fails.
     * @param <S>
     * @return
     */
    <S extends Domain> S checkState();


}
