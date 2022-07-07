.class public LcyclicInheritanceParentGrandchildDependency4/TestClass;
.super LcyclicInheritanceParentGrandchildDependency4/TestClassParent;
.source "TestClass.java"


# direct methods
.method constructor <init>()V
    .locals 0

    invoke-direct {p0}, LcyclicInheritanceParentGrandchildDependency4/TestClassParent;-><init>()V

    return-void
.end method

.method constructor <init>(LcyclicInheritanceParentGrandchildDependency4/TestClassChild;)V
    .locals 0

    invoke-direct {p0}, LcyclicInheritanceParentGrandchildDependency4/TestClassParent;-><init>()V

    return-void
.end method
