package utils.converters;

import exceptions.FixedDataNotFoundException;

/**
 *
 * @author Nicklas Nielsen
 */
public interface Converter {

    public Object toDTO(Object object);

    public Object fromDTO(Object object) throws FixedDataNotFoundException;
}
