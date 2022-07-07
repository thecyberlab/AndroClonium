.class LInvokeInterface$1;
.super Ljava/lang/Object;
.source "InvokeInterface.java"

# interfaces
.implements LTestInterface;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = LInvokeInterface;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:LInvokeInterface;


# direct methods
.method constructor <init>(LInvokeInterface;)V
    .locals 0
    .param p1, "this$0"    # LInvokeInterface;

    .line 8
    iput-object p1, p0, LInvokeInterface$1;->this$0:LInvokeInterface;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public testInterfaceMethod()I
    .locals 1

    .line 11
    const/4 v0, 0x7

    return v0
.end method

.method public throwException()V
    .locals 1
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .line 16
    new-instance v0, Ljava/lang/IllegalArgumentException;

    invoke-direct {v0}, Ljava/lang/IllegalArgumentException;-><init>()V

    throw v0
.end method
