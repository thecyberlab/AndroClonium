.class public LInvokeParent;
.super Ljava/lang/Object;
.source "InvokeParent.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public myClassName()Ljava/lang/String;
    .locals 1

    .line 5
    const-string v0, "InvokeParent"

    return-object v0
.end method

.method public hashCode()I
    .locals 1

    .line 10
    invoke-super {p0}, Ljava/lang/Object;->hashCode()I

    move-result v0

    return v0
.end method