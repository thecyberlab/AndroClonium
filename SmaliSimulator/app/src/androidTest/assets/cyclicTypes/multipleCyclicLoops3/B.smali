.class public LmultipleCyclicLoops3/B;
.super LmultipleCyclicLoops3/A;
.source "B.java"


# direct methods
.method constructor <init>()V
    .locals 0

    invoke-direct {p0}, LmultipleCyclicLoops3/A;-><init>()V

    return-void
.end method

.method constructor <init>(LmultipleCyclicLoops3/C;)V
    .locals 0

    invoke-direct {p0}, LmultipleCyclicLoops3/A;-><init>()V

    return-void
.end method
