.class public LcyclicInheritanceParentGrandchildDependency2/TestClassChild;
.super LcyclicInheritanceParentGrandchildDependency2/TestClass;
.source "TestClassChild.java"


# static fields
.field static parents:[LcyclicInheritanceParentGrandchildDependency2/TestClassParent;


# direct methods
.method static constructor <clinit>()V
    .locals 3

    .line 4
    const/4 v0, 0x3

    new-array v0, v0, [LcyclicInheritanceParentGrandchildDependency2/TestClassParent;

    new-instance v1, LcyclicInheritanceParentGrandchildDependency2/TestClassParent;

    invoke-direct {v1}, LcyclicInheritanceParentGrandchildDependency2/TestClassParent;-><init>()V

    const/4 v2, 0x0

    aput-object v1, v0, v2

    new-instance v1, LcyclicInheritanceParentGrandchildDependency2/TestClass;

    invoke-direct {v1}, LcyclicInheritanceParentGrandchildDependency2/TestClass;-><init>()V

    const/4 v2, 0x1

    aput-object v1, v0, v2

    new-instance v1, LcyclicInheritanceParentGrandchildDependency2/TestClassChild;

    invoke-direct {v1}, LcyclicInheritanceParentGrandchildDependency2/TestClassChild;-><init>()V

    const/4 v2, 0x2

    aput-object v1, v0, v2

    sput-object v0, LcyclicInheritanceParentGrandchildDependency2/TestClassChild;->parents:[LcyclicInheritanceParentGrandchildDependency2/TestClassParent;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 3
    invoke-direct {p0}, LcyclicInheritanceParentGrandchildDependency2/TestClass;-><init>()V

    return-void
.end method
