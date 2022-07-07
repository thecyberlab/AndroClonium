.class public LmultipleCyclicLoops2/C;
.super LmultipleCyclicLoops2/B;
.source "C.java"


# direct methods
.method constructor <init>(LmultipleCyclicLoops2/C;)V
    .locals 0

    invoke-direct {p0, p1}, LmultipleCyclicLoops2/B;-><init>(LmultipleCyclicLoops2/A;)V

    return-void
.end method

.method constructor <init>([LmultipleCyclicLoops2/B;)V
    .locals 1

    const/4 v0, 0x0

    check-cast v0, LmultipleCyclicLoops2/A;

    invoke-direct {p0, v0}, LmultipleCyclicLoops2/B;-><init>(LmultipleCyclicLoops2/A;)V

    return-void
.end method

.method constructor <init>([LmultipleCyclicLoops2/K;)V
    .locals 1

    const/4 v0, 0x0

    check-cast v0, LmultipleCyclicLoops2/A;

    invoke-direct {p0, v0}, LmultipleCyclicLoops2/B;-><init>(LmultipleCyclicLoops2/A;)V

    return-void
.end method
