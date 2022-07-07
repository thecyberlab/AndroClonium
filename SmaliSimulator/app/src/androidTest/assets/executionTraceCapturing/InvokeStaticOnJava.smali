.class public LInvokeStaticOnJava;
.super Ljava/lang/Object;


.method public static test1()Ljava/lang/Integer;
    .locals 1

    const-string v0, "32"

    invoke-static {v0}, Ljava/lang/Integer;->getInteger(Ljava/lang/String;)Ljava/lang/Integer;

    move-result-object v0

    return-object v0
.end method