.class public LInvokeInterface;
.super Ljava/util/ArrayList;
.source "InvokeInterface.java"

# interfaces
.implements LTestInterface;


# instance fields
.field testInterface:LTestInterface;


.method public constructor <init>()V
    .locals 1

    .line 6
    invoke-direct {p0}, Ljava/util/ArrayList;-><init>()V

    .line 8
    new-instance v0, LInvokeInterface$1;

    invoke-direct {v0, p0}, LInvokeInterface$1;-><init>(LInvokeInterface;)V

    iput-object v0, p0, LInvokeInterface;->testInterface:LTestInterface;

    return-void
.end method

.method public static test0()Ljava/lang/Object;
    .locals 1

    new-instance v0, LInvokeInterface;

    invoke-direct {v0}, LInvokeInterface;-><init>()V

    return-object v0
.end method

.method public static sizeOfNewList1()I
    .locals 2

    .line 52
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    .line 53
    .local v0, "l":Ljava/util/List;
    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v1

    return v1
.end method

.method public static sizeOfNewList2()I
    .locals 2

    .line 57
    new-instance v0, LInvokeInterface;

    invoke-direct {v0}, LInvokeInterface;-><init>()V

    .line 58
    .local v0, "l":Ljava/util/List;
    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v1

    return v1
.end method

.method public static test1()I
    .locals 2

    .line 36
    new-instance v0, LInvokeInterface;

    invoke-direct {v0}, LInvokeInterface;-><init>()V

    #.line 37
    #.local v0, "testInterface":LTestInterface;
    invoke-interface {v0}, LTestInterface;->testInterfaceMethod()I

    move-result v1

    return v1
.end method

.method public static test2()I
    .locals 2

    .line 41
    new-instance v0, LInvokeInterface;

    invoke-direct {v0}, LInvokeInterface;-><init>()V

    .line 42
    .local v0, "ii":LInvokeInterface;
    iget-object v1, v0, LInvokeInterface;->testInterface:LTestInterface;

    invoke-interface {v1}, LTestInterface;->testInterfaceMethod()I

    move-result v1

    return v1
.end method

.method public static test3()I
    .locals 2
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .line 46
    new-instance v0, LInvokeInterface;

    invoke-direct {v0}, LInvokeInterface;-><init>()V

    .line 47
    .local v0, "ii":LTestInterface;
    invoke-interface {v0}, LTestInterface;->throwException()V

    .line 48
    const/4 v1, 0x0

    return v1
.end method

.method public static test4()I
    .locals 2
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .line 52
    new-instance v0, LInvokeInterface;

    invoke-direct {v0}, LInvokeInterface;-><init>()V

    .line 53
    .local v0, "ii":LInvokeInterface;
    iget-object v1, v0, LInvokeInterface;->testInterface:LTestInterface;

    invoke-interface {v1}, LTestInterface;->throwException()V

    .line 54
    const/4 v1, 0x0

    return v1
.end method

# virtual methods
.method public size()I
    .locals 1

    .line 32
    const/4 v0, -0x1

    return v0
.end method

.method public testInterfaceMethod()I
    .locals 1

    .line 22
    const/16 v0, 0xd

    return v0
.end method

.method public throwException()V
    .locals 1
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .line 27
    new-instance v0, Ljava/lang/Exception;

    invoke-direct {v0}, Ljava/lang/Exception;-><init>()V

    throw v0
.end method
