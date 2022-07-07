.class public LInvokeBaseObjectMethods;
.super Ljava/lang/Object;
.source "InvokeBaseObjectMethods.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 5
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static test1()Ljava/lang/String;
    .locals 2

    .line 8
    new-instance v0, Ljava/lang/Object;

    invoke-direct {v0}, Ljava/lang/Object;-><init>()V

    .line 9
    .local v0, "o":Ljava/lang/Object;
    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    return-object v1
.end method

.method public static test2()Ljava/lang/String;
    .locals 2

    .line 13
    new-instance v0, LInvokeBaseObjectMethods;

    invoke-direct {v0}, LInvokeBaseObjectMethods;-><init>()V

    .line 14
    .local v0, "o":LInvokeBaseObjectMethods;
    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    return-object v1
.end method

.method public static test3()Ljava/lang/Object;
    .locals 2
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/CloneNotSupportedException;
        }
    .end annotation

    new-instance v0, LInvokeBaseObjectMethods;

    invoke-direct {v0}, LInvokeBaseObjectMethods;-><init>()V

    invoke-virtual {v0}, Ljava/lang/Object;->clone()Ljava/lang/Object;

    move-result-object v1

    return-object v1
.end method

.method public static test4()Ljava/lang/Object;
    .locals 2
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/CloneNotSupportedException;
        }
    .end annotation

    .line 24
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    const/4 v1, 0x1

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    const/4 v1, 0x2

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    invoke-virtual {v0}, Ljava/util/ArrayList;->clone()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/util/ArrayList;

    return-object v1
.end method

.method public static test5()Z
    .locals 2

    new-instance v0, LInvokeBaseObjectMethods;

    invoke-direct {v0}, LInvokeBaseObjectMethods;-><init>()V

    invoke-virtual {v0, v0}, Ljava/lang/Object;->equals(Ljava/lang/Object;)Z

    move-result v1

    return v1
.end method

.method public static test6()Z
    .locals 2

    .line 37
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    .line 38
    .local v0, "o":Ljava/util/ArrayList;
    invoke-virtual {v0, v0}, Ljava/util/ArrayList;->equals(Ljava/lang/Object;)Z

    move-result v1

    return v1
.end method

.method public static test7()Z
    .locals 3

    .line 42
    new-instance v0, LInvokeBaseObjectMethods;

    invoke-direct {v0}, LInvokeBaseObjectMethods;-><init>()V

    .line 43
    .local v0, "o":LInvokeBaseObjectMethods;
    new-instance v1, Ljava/util/ArrayList;

    invoke-direct {v1}, Ljava/util/ArrayList;-><init>()V

    .line 44
    .local v1, "al":Ljava/util/ArrayList;
    invoke-virtual {v0, v1}, Ljava/lang/Object;->equals(Ljava/lang/Object;)Z

    move-result v2

    return v2
.end method


.method public static test8()[I
    .locals 3

    const/4 v0, 0x2

    new-array v0, v0, [I

    const/4 v1, 0x3

    const/4 v2, 0x0

    aput v1, v0, v2

    invoke-virtual {v0}, [I->clone()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, [I

    return-object v1
.end method


.method public static test9()Ljava/lang/String;
    .locals 1

    sget-object v0, Ljava/awt/font/NumericShaper$Range;->EUROPEAN:Ljava/awt/font/NumericShaper$Range;

    invoke-virtual {v0}, Ljava/awt/font/NumericShaper$Range;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static test10()Ljava/lang/Class;
    .locals 1

    new-instance v0, Ljava/lang/Object;

    invoke-direct {v0}, Ljava/lang/Object;-><init>()V

    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v0

    return-object v0
.end method

.method public static test11()Ljava/lang/Class;
    .locals 1

    new-instance v0, LInvokeBaseObjectMethods;

    invoke-direct {v0}, LInvokeBaseObjectMethods;-><init>()V

    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v0

    return-object v0
.end method
