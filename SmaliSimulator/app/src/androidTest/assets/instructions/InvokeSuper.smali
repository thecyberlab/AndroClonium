.class public LInvokeSuper;
.super LInvokeParent;
.source "InvokeSuper.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 3
    invoke-direct {p0}, LInvokeParent;-><init>()V

    return-void
.end method

.method public static test1()Ljava/lang/String;
    .locals 2

    new-instance v0, LInvokeSuper;

    invoke-direct {v0}, LInvokeSuper;-><init>()V

    invoke-super {v0}, LInvokeParent;->myClassName()Ljava/lang/String;

    move-result-object v1

    return-object v1
.end method

.method public static test2()I
    .locals 2

    new-instance v0, LInvokeSuper;

    invoke-direct {v0}, LInvokeSuper;-><init>()V

    invoke-super {v0}, LInvokeParent;->hashCode()I

    move-result v1

    return v1
.end method


# virtual methods
.method public myClassName()Ljava/lang/String;
    .locals 1

    .line 6
    const-string v0, "InvokeSuper"

    return-object v0
.end method
