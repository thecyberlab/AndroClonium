.class public LInvokeSuper2Parent;
.super LInvokeSuper2GrandParent;
.source "InvokeSuper2Parent.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 3
    invoke-direct {p0}, LInvokeSuper2GrandParent;-><init>()V

    return-void
.end method


# virtual methods
.method public test1()I
    .locals 1

    .line 7
    invoke-super {p0}, LInvokeSuper2GrandParent;->test1()I

    move-result v0

    add-int/lit8 v0, v0, 0x10

    return v0
.end method

.method public test4()I
    .locals 1

    .line 11
    const/16 v0, 0x64

    return v0
.end method

.method public test6(Ljava/lang/String;)Ljava/lang/String;
    .locals 2

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "|InvokeSuper2Parent"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method
