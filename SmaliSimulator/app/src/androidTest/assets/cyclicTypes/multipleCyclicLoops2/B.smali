.class public LmultipleCyclicLoops2/B;
.super LmultipleCyclicLoops2/A;
.source "B.java"


# direct methods
.method constructor <init>(LmultipleCyclicLoops2/A;)V
    .locals 1

    const/4 v0, 0x0

    invoke-direct {p0, v0}, LmultipleCyclicLoops2/A;-><init>([LmultipleCyclicLoops2/C;)V

    return-void
.end method

.method constructor <init>([LmultipleCyclicLoops2/C;)V
    .locals 0

    invoke-direct {p0, p1}, LmultipleCyclicLoops2/A;-><init>([LmultipleCyclicLoops2/C;)V

    return-void
.end method
