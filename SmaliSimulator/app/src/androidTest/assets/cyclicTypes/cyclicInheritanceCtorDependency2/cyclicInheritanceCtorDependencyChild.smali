.class public LcyclicInheritanceCtorDependency2/cyclicInheritanceCtorDependencyChild;
.super LcyclicInheritanceCtorDependency2/cyclicInheritanceCtorDependencyParent;
.source "cyclicInheritanceCtorDependencyChild.java"


# direct methods
.method public constructor <init>(LcyclicInheritanceCtorDependency2/cyclicInheritanceCtorDependencyParent;)V
    .locals 1

    .line 6
    const/16 v0, 0x9

    invoke-direct {p0, v0}, LcyclicInheritanceCtorDependency2/cyclicInheritanceCtorDependencyParent;-><init>(I)V

    .line 7
    return-void
.end method

.method public constructor <init>(LcyclicInheritanceCtorDependency2/cyclicInheritanceCtorDependencyChild;)V
    .locals 0

    invoke-direct {p0, p1}, LcyclicInheritanceCtorDependency2/cyclicInheritanceCtorDependencyParent;-><init>(LcyclicInheritanceCtorDependency2/cyclicInheritanceCtorDependencyChild;)V

    .line 11
    return-void
.end method
