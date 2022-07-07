.class public LReturnTest;
.super Ljava/lang/Object;
.source "ReturnTest.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static test1()V
    .locals 0

    return-void
.end method

.method public static test2()I
    .locals 1

    #const/4 v0, 0x0

    return v0
.end method

.method public static test3()F
    .locals 1

    #const/4 v0, 0x0

    return v0
.end method

.method public static test4()J
    .locals 2

    #const-wide/16 v0, 0x0

    return-wide v0
.end method

.method public static test5()D
    .locals 2

    #const-wide/16 v0, 0x0

    return-wide v0
.end method

.method public static test6()Z
    .locals 1

    #const/4 v0, 0x0

    return v0
.end method

.method public static test7()C
    .locals 1

    #const/4 v0, 0x0

    return v0
.end method

.method public static test8()S
    .locals 1

    #const/4 v0, 0x0

    return v0
.end method

.method public static test9()B
    .locals 1

    #const/4 v0, 0x0

    return v0
.end method

.method public static test10()Ljava/lang/Object;
    .locals 1

    #const/4 v0, 0x0

    return-object v0
.end method
