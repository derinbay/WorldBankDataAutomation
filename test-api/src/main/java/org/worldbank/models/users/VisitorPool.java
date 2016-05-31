package org.worldbank.models.users;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class VisitorPool<V extends Visitor> extends Pool<V> {

    private static final Logger logger = LogManager.getLogger(VisitorPool.class);

    private static final ThreadLocal<List<Visitor>> threadLocal = new ThreadLocal<List<Visitor>>();

    private String poolName;

    public VisitorPool(String poolName) {
        super(poolName);
    }

    public static void freeCurrentVisitor() {
        List<Visitor> visitors = threadLocal.get();
        if (visitors == null) {
            return;
        }
        for (Visitor visitor : visitors) {
            visitor.free();
        }
    }

    public static void closeBrowserOfCurrentVisitor() {
        List<Visitor> visitors = threadLocal.get();
        if (visitors == null) {
            return;
        }
        for (Visitor visitor : visitors) {
            visitor = visitor.origin();
            visitor.closeBrowser();
        }
    }

    public static Visitor getVisitorInThreadLocal() {
        List<Visitor> visitors = threadLocal.get();

        return visitors != null ? visitors.get(visitors.size() - 1) : null;
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
