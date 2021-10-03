package com.tnicacio.springapiinitializr.mapper;

import com.tnicacio.springapiinitializr.util.ListUtil;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public interface Mapper<T, U> {

    /**
     * Makes a copy from attributes from one object to another.
     *
     * @param from object that will have your attributes copied. This object can not be null.
     * @param to   object that will receive new values. This object can not be null.
     * @return the object that received new values.
     */
    U mapNonNullFromTo(@NonNull T from, @NonNull U to);

    /**
     * Makes a copy from attributes from one object to another.
     *
     * @param from object that will have your attributes copied. This object can be null.
     * @param to   object that will receive new values. This object can be null.
     * @return the object that received new values.
     */
    default U mapNullableFromTo(@Nullable T from, @Nullable U to) {
        if (from == null || to == null) {
            return null;
        }
        return mapNonNullFromTo(from, to);
    }

    /**
     * Maps, in order, an object from one list to another object from another list.
     * The lists must have the same size.
     *
     * @param fromCollectionOfObjects first object list.
     * @param toCollectionOfObjects   second object list.
     * @return a list containing the objects after the mapping process.
     */
    default List<U> mapListFromTo(@Nullable List<T> fromCollectionOfObjects, @Nullable List<U> toCollectionOfObjects) {
        if (CollectionUtils.isEmpty(fromCollectionOfObjects) || CollectionUtils.isEmpty(toCollectionOfObjects)) {
            return new ArrayList<>();
        }
        return ListUtil.zip(fromCollectionOfObjects, toCollectionOfObjects)
                .stream()
                .map(item -> mapNullableFromTo(item.getFirst(), item.getSecond()))
                .collect(Collectors.toList());
    }

}
