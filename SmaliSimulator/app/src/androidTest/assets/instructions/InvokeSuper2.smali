.class public LInvokeSuper2;
.super LInvokeSuper2Parent;
.source "InvokeSuper2.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 3
    invoke-direct {p0}, LInvokeSuper2Parent;-><init>()V

    return-void
.end method


# virtual methods
.method public test1()I
    .locals 1

    .line 7
    invoke-super {p0}, LInvokeSuper2Parent;->test1()I

    move-result v0

    add-int/lit8 v0, v0, 0x8

    return v0
.end method

.method public test2()I
    .locals 1

    .line 12
    invoke-super {p0}, LInvokeSuper2Parent;->test2()I

    move-result v0

    add-int/lit8 v0, v0, 0x16

    return v0
.end method

.method public test4_1()I
    .locals 1

    .line 16
    invoke-super {p0}, LInvokeSuper2Parent;->test4()I

    move-result v0

    add-int/lit8 v0, v0, 0xa

    return v0
.end method


.method public test5(I)I
    .locals 1

    invoke-super {p0, p1}, LInvokeSuper2Parent;->test5(I)I

    move-result v0

    add-int/lit8 v0, v0, 0x14

    return v0
.end method
