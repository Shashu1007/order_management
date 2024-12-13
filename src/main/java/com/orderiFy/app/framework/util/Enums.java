package com.orderiFy.app.framework.util;

public class Enums {

    public enum OrderStatus {
        NEW,
        PARTIALLY_COMPLETED,
        COMPLETED,
        PENDING,
        CANCELLED,

    }

    public enum UserRole {
        ADMIN,
        USER,
        MANAGER,
        BRANCH_ADMIN,
        STAFF
    }

    public enum Uom{
        KG,
        MTR,
        CM,
        IN,
        GMS,
        NOS,
        SET,
        PACKETS,
        LTR
    }

    public enum UserStatus {
        ACTIVE,
        INACTIVE,
        DELETED

    }


    public enum OrderPriority{
        LOW,
        MEDIUM,

        HIGH,

        CRITICAL

    }





}
