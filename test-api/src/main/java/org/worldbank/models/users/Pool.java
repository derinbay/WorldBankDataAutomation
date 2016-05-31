package org.worldbank.models.users;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Stack;

public abstract class Pool<V extends Account> {
    private static final Logger logger = LogManager.getLogger(VisitorPool.class);

    private String VISITOR_LOCK = "LOCK";

    private Stack<V> visitors = new Stack<V>();

    private String poolName;

    public Pool(String poolName) {
        this.poolName = poolName;
    }

    public void add(V visitor) {
        visitors.add(visitor);
        visitor.setPool(this);
    }

    public abstract void postPop(V account);

    public abstract void preFree(V account);

    public V pop() {
        synchronized (VISITOR_LOCK) {
            V visitor = null;

            int counter = 0;
            while (true) {
                if (counter > 300) {
                    logger.error("I had tried to fetch a " + poolName + " about 5 min., but I hadn't.");
                    break;
                }
                if (!visitors.isEmpty()) {
                    visitor = visitors.pop();
                    if (visitor != null) {
                        logger.trace("A " + poolName + " is popped from pool.");
                        break;
                    }
                } else {
                    logger.trace(poolName + " pool is empty. I'm waiting...");
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                counter++;
            }
            postPop(visitor);
            return visitor;
        }
    }

    public void free(V visitor) {
        preFree(visitor);
        add(visitor);
    }
}
