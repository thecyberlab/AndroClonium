.class public LInvokeDirect2_A;
.super LInvokeDirect2_A_Parent;
.source "InvokeDirect2_A.java"


# direct methods
.method constructor <init>()V
    .locals 1

    new-instance v0, LInvokeDirect2_B;

    invoke-direct {v0}, LInvokeDirect2_B;-><init>()V

    invoke-direct {p0, v0}, LInvokeDirect2_A_Parent;-><init>(LInvokeDirect2_B;)V

    return-void
.end method
