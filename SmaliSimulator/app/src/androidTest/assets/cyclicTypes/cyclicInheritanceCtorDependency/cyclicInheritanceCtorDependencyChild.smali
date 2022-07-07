.class public LcyclicInheritanceCtorDependency/cyclicInheritanceCtorDependencyChild;
.super LcyclicInheritanceCtorDependency/cyclicInheritanceCtorDependencyParent;
.source "cyclicInheritanceCtorDependencyChild.java"


# direct methods
.method public constructor <init>()V
    .locals 1

    .line 6
    const/16 v0, 0xc

    invoke-direct {p0, v0}, LcyclicInheritanceCtorDependency/cyclicInheritanceCtorDependencyParent;-><init>(I)V

    .line 7
    return-void
.end method

.method public constructor <init>(LcyclicInheritanceCtorDependency/cyclicInheritanceCtorDependencyChild;)V
    .locals 0

    invoke-direct {p0, p1}, LcyclicInheritanceCtorDependency/cyclicInheritanceCtorDependencyParent;-><init>(LcyclicInheritanceCtorDependency/cyclicInheritanceCtorDependencyChild;)V

    .line 11
    return-void
.end method
