.class public LcyclicInheritanceCtorDependency/cyclicInheritanceCtorDependencyParent;
.super Ljava/lang/Object;
.source "cyclicInheritanceCtorDependencyParent.java"


# instance fields
.field child:LcyclicInheritanceCtorDependency/cyclicInheritanceCtorDependencyChild;

.field n:I


# direct methods
.method public constructor <init>(I)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput p1, p0, LcyclicInheritanceCtorDependency/cyclicInheritanceCtorDependencyParent;->n:I

    return-void
.end method

.method public constructor <init>(LcyclicInheritanceCtorDependency/cyclicInheritanceCtorDependencyChild;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, LcyclicInheritanceCtorDependency/cyclicInheritanceCtorDependencyParent;->child:LcyclicInheritanceCtorDependency/cyclicInheritanceCtorDependencyChild;

    return-void
.end method
