.class public LCtorLoopTest;
.super Ljava/lang/Object;


.method public constructor <init>()V
    .locals 2

    new-instance v0, Ljava/lang/Object;

    invoke-direct {v0}, Ljava/lang/Object;-><init>()V

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x5

    const/4 v1, 0x1

    :goto_0
    if-eqz v0, :end

    sub-int v0, v0, v1

    goto :goto_0

    :end
    return-void
.end method
