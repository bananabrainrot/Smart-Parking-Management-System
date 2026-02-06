package entities;

import java.util.Collections;
import java.util.List;

public class ListResult<T> {
    private final List<T> items;
    private final int totalCount;

    public ListResult(List<T> items, int totalCount) {
        this.items = items == null ? Collections.emptyList() : items;
        this.totalCount = totalCount;
    }

    public List<T> getItems() {
        return items;
    }

    public int getTotalCount() {
        return totalCount;
    }
}
