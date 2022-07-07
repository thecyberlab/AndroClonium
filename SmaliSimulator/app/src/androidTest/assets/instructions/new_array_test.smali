.class Lnew_array_test;
.super Ljava/lang/Object;

.method public static createIntegerArray()V
    .locals 1

    const/4 v0, 0x3

    new-array v0, v0, [I

    return-void
.end method

.method public static create2DIntegerArray()V
    .locals 1

    const/4 v0, 0x4

    new-array v0, v0, [[I

    return-void
.end method

.method public static createLocalInstanceArray()V
    .locals 1

    const/4 v0, 0x5

    new-array v0, v0, [Lnew_array_test;

    return-void
.end method

.method public static create2DLocalInstanceArray()V
    .locals 1

    const/4 v0, 0x6

    new-array v0, v0, [[Lnew_array_test;

    return-void
.end method

.method public static createNonExistentArrayClass()V
    .locals 1

    const/4 v0, 0x7

    new-array v0, v0, [Ldoes/not/exist;

    return-void
.end method

.method public static createIntegerArrayAmbiguousSize()V
    .locals 1

    new-array v0, v0, [I

    return-void
.end method
