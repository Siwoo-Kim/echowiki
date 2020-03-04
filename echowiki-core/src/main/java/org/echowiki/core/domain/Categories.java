package org.echowiki.core.domain;

import java.util.List;

/**
 * The class is a factory for {@link Category}
 */
public interface Categories {

    /**
     * returns new {@link Document}.
     *
     * @throws IllegalArgumentException
     *  if name is blank or {@link NameSpace} is null
     *  if there is {@link Category} which has given name
     *
     * @param name
     * @param nameSpace
     * @return
     */
    Category newCategory(String name, NameSpace nameSpace);

    /**
     * returns new {@link Document}.
     *
     * @throws IllegalArgumentException
     *  if name is blank or {@link NameSpace} is null
     *  if parent in the list doesn't exists
     *  if there is {@link Category} which has given name
     *
     * @param name
     * @param nameSpace
     * @param parents
     * @return
     */
    Category newCategory(String name, NameSpace nameSpace, List<Category> parents);

    /**
     *  returns new {@link Document} which is copied from given {@link Document}.
     *
     * @param category
     * @return
     */
    Category newCategory(Category category);

}
