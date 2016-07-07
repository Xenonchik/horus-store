package parser;

import parser.source.ParseSource;

import java.util.List;

/**
 * Created by serge on 3/27/16.
 */
public interface Parser<E> {
    public List<E> parse(ParseSource source);

    public boolean processCategory();

    public void setCategory(Long category);
}
