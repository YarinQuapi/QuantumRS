package dev.yarinlevi.quantumrs.setup;

public enum ItemStorageType {
    QUANTUM("quantum", Integer.MAX_VALUE);

    private final int capacity;
    private final String name;


    ItemStorageType(String name, int capacity) {
        this.capacity = capacity;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }
}
