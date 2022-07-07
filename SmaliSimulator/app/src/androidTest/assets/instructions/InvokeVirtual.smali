.class public LInvokeVirtual;
.super Ljava/lang/Object;
.source "InvokeVirtual.java"


# static fields
.field public static testField:I


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 6
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static test()I
    .locals 2

    .line 23
    new-instance v0, LInvokeVirtual;

    invoke-direct {v0}, LInvokeVirtual;-><init>()V

    .line 24
    .local v0, "iv":LInvokeVirtual;
    invoke-virtual {v0}, LInvokeVirtual;->getSomeRandomNumber()I

    move-result v1

    return v1
.end method

.method public static test2()I
    .locals 2

    .line 28
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    .line 29
    .local v0, "al":Ljava/util/ArrayList;
    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    .line 30
    const/4 v1, 0x2

    return v1
.end method

.method public static test3()I
    .locals 2
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .line 34
    new-instance v0, LInvokeVirtual;

    invoke-direct {v0}, LInvokeVirtual;-><init>()V

    .line 35
    .local v0, "iv":LInvokeVirtual;
    invoke-virtual {v0}, LInvokeVirtual;->throwException()V

    .line 36
    const/4 v1, 0x2

    return v1
.end method

.method public static test4()Ljava/lang/Object;
    .locals 2

    .line 40
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    .line 41
    .local v0, "list":Ljava/util/ArrayList;
    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 42
    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v1

    return-object v1
.end method


# virtual methods
.method public getSomeRandomNumber()I
    .locals 2

    .line 11
    new-instance v0, Ljava/util/Random;

    invoke-direct {v0}, Ljava/util/Random;-><init>()V

    .line 12
    .local v0, "ran":Ljava/util/Random;
    invoke-virtual {v0}, Ljava/util/Random;->nextInt()I

    move-result v1

    .line 13
    .local v1, "a":I
    sput v1, LInvokeVirtual;->testField:I

    .line 14
    return v1
.end method

.method public throwException()V
    .locals 1
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .line 19
    new-instance v0, Ljava/lang/Exception;

    invoke-direct {v0}, Ljava/lang/Exception;-><init>()V

    throw v0
.end method

.method public static printSomething()V
    .locals 2

    .line 36
    sget-object v0, Ljava/lang/System;->out:Ljava/io/PrintStream;

    const-string v1, "hello"

    invoke-virtual {v0, v1}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    .line 37
    return-void
.end method