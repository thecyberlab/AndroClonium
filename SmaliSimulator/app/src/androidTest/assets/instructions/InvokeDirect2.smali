.class public LInvokeDirect2;
.super Ljava/lang/Object;
.source "InvokeDirect2.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static test()LInvokeDirect2_A;
    .locals 1

    new-instance v0, LInvokeDirect2_A;

    invoke-direct {v0}, LInvokeDirect2_A;-><init>()V

    return-object v0
.end method
