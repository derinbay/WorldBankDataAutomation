package org.worldbank.models.users;

public interface Account<P extends Pool> {
    void setPool(P pool);

    void free();
}
