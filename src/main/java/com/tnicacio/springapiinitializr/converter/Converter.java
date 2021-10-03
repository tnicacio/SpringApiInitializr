package com.tnicacio.springapiinitializr.converter;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public interface Converter<T, U> {

    /**
     * Makes a copy from attributes from one object to an object of another type.
     *
     * @param object object that will have your attributes copied. This object can be null.
     * @return the new object with copied values.
     */
    default U convertNullable(@Nullable T object) {
        if (object == null) {
            return null;
        } else {
            return convertNonNull(object);
        }
    }

    /**
     * Makes a copy from attributes from one object to an object of another type.
     *
     * @param object object that will have your attributes copied. This object can not be null.
     * @return the new object with copied values.
     */
    U convertNonNull(@NonNull T object);

    /**
     * Convert a list from objects to another list of another type objects.
     *
     * @param collectionOfObjects original object list.
     * @return a list containing the objects after the mapping process.
     */
    default List<U> convertList(@Nullable List<T> collectionOfObjects) {
        if (CollectionUtils.isEmpty(collectionOfObjects)) {
            return new ArrayList<>();
        } else {
            return collectionOfObjects
                    .stream()
                    .map(this::convertNullable)
                    .collect(Collectors.toList());
        }
    }

}
