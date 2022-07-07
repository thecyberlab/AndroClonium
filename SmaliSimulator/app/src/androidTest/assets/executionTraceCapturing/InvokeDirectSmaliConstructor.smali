.class public LInvokeDirectSmaliConstructor;
.super Ljava/lang/Object;


.method public static test1()LTestClass;
    .locals 2

    const v0, 0x64

    new-instance v1, LTestClass;

    invoke-direct {v1, v0}, LTestClass;-><init>(I)V

    return-object v1
.end method

