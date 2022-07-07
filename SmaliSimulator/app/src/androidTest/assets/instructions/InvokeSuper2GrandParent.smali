.class public LInvokeSuper2GrandParent;
.super Ljava/lang/Object;
.source "InvokeSuper2GrandParent.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public test1()I
    .locals 1

    .line 6
    const/16 v0, 0x20

    return v0
.end method

.method public test2()I
    .locals 1

    .line 10
    const/16 v0, 0x28

    return v0
.end method

.method public test3()I
    .locals 1

    .line 14
    const/16 v0, -0xa

    return v0
.end method

.method public test5(I)I
    .locals 1

    add-int/lit8 v0, p1, 0xa

    return v0
.end method
