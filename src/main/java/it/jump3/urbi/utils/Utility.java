package it.jump3.urbi.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Utility {

    public static final char START_LIST_JSON = '[';
    public static final char END_LIST_JSON = ']';


    /**
     * Return chunked list
     *
     * @param completeList
     * @return
     */
    public static Collection<? extends List<?>> getChunkedList(List<?> completeList) {
        return getChunkedList(completeList, 1000);
    }

    public static Collection<? extends List<?>> getChunkedList(List<?> completeList, Integer size) {

        AtomicInteger counter = new AtomicInteger(0);
        Collection<? extends List<?>> chunkedList = completeList.stream()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / size))
                .values();

        return chunkedList;
    }

    public static String capitalize(String s) {
        return StringUtils.capitalize(s);
    }

    /**
     * Produce IN CLAUSE as string
     *
     * @param field
     * @param ids
     * @return
     */
    public static String appendIds(String field, Long... ids) {
        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append(field + " IN ( ");
        for (int i = 0; i < ids.length; i++) {
            if (i != 0) {
                queryBuilder.append(",");
            }
            queryBuilder.append(ids[i]);
        }
        queryBuilder.append(" ) ");

        return queryBuilder.toString();
    }

    public static String appendIds(String field, List<Long> idList) {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(field + " IN ( ");

        boolean first = true;
        for (Long id : idList) {
            if (first) {
                first = false;
            } else {
                queryBuilder.append(",");
            }
            queryBuilder.append(Long.toString(id));
        }
        queryBuilder.append(" ) ");

        return queryBuilder.toString();
    }

    public static String joinListWithChar(List<String> list, String c) {
        return String.join(c, list);
    }

    public static String joinListWithCharAsJson(List<String> list, String c) {
        StringBuilder sb = new StringBuilder();
        sb.append(START_LIST_JSON);
        sb.append(String.join(c, list));
        sb.append(END_LIST_JSON);
        return sb.toString();
    }

    public static String convertListInString(List<?> list, String delimeter, String prefix, String suffix) {
        Assert.notNull(prefix, "The prefix must not be null");
        Assert.notNull(suffix, "The suffix must not be null");
        return list.stream()
                .filter(n -> n != null)
                .map(n -> String.valueOf(n))
                .collect(Collectors.joining(delimeter, prefix, suffix));
    }

    public static String convertListInString(List<?> list, String delimeter) {
        return list.stream()
                .filter(n -> n != null)
                .map(n -> String.valueOf(n))
                .collect(Collectors.joining(delimeter));
    }

    public static String convertListInString(List<?> list, String prefix, String suffix) {
        return convertListInString(list, ",", prefix, suffix);
    }

    public static String convertListInString(List<?> list) {
        return convertListInString(list, ",");
    }
}
