.class public LInvokeDirectJavaConstructor;
.super Ljava/lang/Object;

.method public static test1()Ljava/lang/Object;
    .locals 2

    const v0, 0x64

    new-instance v1, Ljava/util/ArrayList;

    invoke-direct {v1, v0}, Ljava/util/ArrayList;-><init>(I)V

    return-object v1
.end method
