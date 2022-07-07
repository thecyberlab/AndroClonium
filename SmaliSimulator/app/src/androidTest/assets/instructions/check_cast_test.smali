.class Lcheck_cast_test;
.super Ljava/lang/Object;

.method public static castToObject()V
    .locals 1

    check-cast v0, Ljava/lang/Object;

    return-void
.end method

.method public static castToString()V
    .locals 1

    check-cast v0, Ljava/lang/String;

    return-void
.end method


.method public static castToInteger()V
    .locals 1

    check-cast v0, Ljava/lang/Integer;

    return-void
.end method


.method public static castToIntArray()V
    .locals 1

    check-cast v0, [I

    return-void
.end method

.method public static castToObjectArray()V
    .locals 1

    check-cast v0, [Ljava/lang/Object;

    return-void
.end method

.method public static castToStringArray()V
    .locals 1

    check-cast v0, [Ljava/lang/String;

    return-void
.end method

.method public static castToSelfArray()V
    .locals 1

    check-cast v0, [Lcheck_cast_test;

    return-void
.end method