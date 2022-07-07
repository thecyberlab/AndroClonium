.class public LcyclicInheritanceCtorDependency3/cyclicInheritanceCtorDependencyChild;
.super LcyclicInheritanceCtorDependency3/cyclicInheritanceCtorDependencyParent;
.source "cyclicInheritanceCtorDependencyChild.java"


# direct methods
.method public constructor <init>()V
    .locals 1

    const/16 v0, 0x13

    invoke-direct {p0, v0}, LcyclicInheritanceCtorDependency3/cyclicInheritanceCtorDependencyParent;-><init>(I)V

    return-void
.end method

.method public constructor <init>([LcyclicInheritanceCtorDependency3/cyclicInheritanceCtorDependencyChild;)V
    .locals 0

    invoke-direct {p0, p1}, LcyclicInheritanceCtorDependency3/cyclicInheritanceCtorDependencyParent;-><init>([LcyclicInheritanceCtorDependency3/cyclicInheritanceCtorDependencyChild;)V

    return-void
.end method
