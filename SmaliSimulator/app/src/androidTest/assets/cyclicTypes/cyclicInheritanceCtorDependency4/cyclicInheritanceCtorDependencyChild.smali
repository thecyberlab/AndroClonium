.class public LcyclicInheritanceCtorDependency4/cyclicInheritanceCtorDependencyChild;
.super LcyclicInheritanceCtorDependency4/cyclicInheritanceCtorDependencyParent;
.source "cyclicInheritanceCtorDependencyChild.java"


# direct methods
.method public constructor <init>()V
    .locals 1

    const/16 v0, 0x2b

    invoke-direct {p0, v0}, LcyclicInheritanceCtorDependency4/cyclicInheritanceCtorDependencyParent;-><init>(I)V

    return-void
.end method

.method public constructor <init>(LcyclicInheritanceCtorDependency4/cyclicInheritanceCtorDependencyIntermediate;)V
    .locals 0
    invoke-direct {p0, p1}, LcyclicInheritanceCtorDependency4/cyclicInheritanceCtorDependencyParent;-><init>(LcyclicInheritanceCtorDependency4/cyclicInheritanceCtorDependencyIntermediate;)V

    return-void
.end method
