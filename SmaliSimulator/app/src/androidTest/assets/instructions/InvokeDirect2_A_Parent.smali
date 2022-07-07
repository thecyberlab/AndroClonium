.class public LInvokeDirect2_A_Parent;
.super Ljava/lang/Object;
.source "InvokeDirect2_A_Parent.java"


# instance fields
.field b:LInvokeDirect2_B;


# direct methods
.method constructor <init>(LInvokeDirect2_B;)V
    .locals 0
    
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, LInvokeDirect2_A_Parent;->b:LInvokeDirect2_B;

    return-void
.end method
