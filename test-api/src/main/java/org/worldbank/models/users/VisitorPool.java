package org.worldbank.models.users;

import java.util.ArrayList;
import java.util.List;

public class VisitorPool<V extends Visitor> extends Pool<V> {

    private static final ThreadLocal<List<Visitor>> threadLocal = new ThreadLocal<List<Visitor>>();

    public VisitorPool(String poolName) {
        super(poolName);
    }

    @Override
    public void postPop(Visitor visitor) {
        List<Visitor> visitors = threadLocal.get();
        if (visitors == null) {
            visitors = new ArrayList<>();
            threadLocal.set(visitors);
        }
        visitors.add(visitor);
    }

    @Override
    public void preFree(Visitor account) {
        threadLocal.remove();
    }
}
