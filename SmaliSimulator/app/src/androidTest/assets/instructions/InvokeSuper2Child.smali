.class public LInvokeSuper2Child;
.super LInvokeSuper2;
.source "InvokeSuper2Child.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 3
    invoke-direct {p0}, LInvokeSuper2;-><init>()V

    return-void
.end method


# virtual methods
.method public test1()I
    .locals 1

    .line 7
    invoke-super {p0}, LInvokeSuper2;->test1()I

    move-result v0

    add-int/lit8 v0, v0, 0x4

    return v0
.end method

.method public test3()I
    .locals 1

    .line 12
    invoke-super {p0}, LInvokeSuper2;->test3()I

    move-result v0

    add-int/lit8 v0, v0, -0xa

    return v0
.end method

.method public test4_2()I
    .locals 1

    .line 16
    invoke-super {p0}, LInvokeSuper2;->test4()I

    move-result v0

    add-int/lit8 v0, v0, 0x14

    return v0
.end method


.method public test5(I)I
    .locals 1

    invoke-super {p0, p1}, LInvokeSuper2;->test5(I)I

    move-result v0

    add-int/lit8 v0, v0, 0x28

    return v0
.end method

.method public test6(Ljava/lang/String;)Ljava/lang/String;
    .locals 2

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-super {p0, p1}, LInvokeSuper2;->test6(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "|InvokeSuper2Child"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method
