.class public LcyclicInheritanceCtorDependency4/cyclicInheritanceCtorDependencyParent;
.super Ljava/lang/Object;
.source "cyclicInheritanceCtorDependencyParent.java"


# instance fields
.field intermediate:LcyclicInheritanceCtorDependency4/cyclicInheritanceCtorDependencyIntermediate;

.field n:I


# direct methods
.method public constructor <init>(I)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput p1, p0, LcyclicInheritanceCtorDependency4/cyclicInheritanceCtorDependencyParent;->n:I

    return-void
.end method

.method public constructor <init>(LcyclicInheritanceCtorDependency4/cyclicInheritanceCtorDependencyIntermediate;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, LcyclicInheritanceCtorDependency4/cyclicInheritanceCtorDependencyParent;->intermediate:LcyclicInheritanceCtorDependency4/cyclicInheritanceCtorDependencyIntermediate;

    return-void
.end method
