.class public LcyclicInheritanceParentGrandchildDependency2/TestClassParent;
.super Ljava/lang/Object;
.source "TestClassParent.java"


# static fields
.field static children:[LcyclicInheritanceParentGrandchildDependency2/TestClassChild;


# direct methods
.method static constructor <clinit>()V
    .locals 3

    .line 4
    const/4 v0, 0x2

    new-array v0, v0, [LcyclicInheritanceParentGrandchildDependency2/TestClassChild;

    new-instance v1, LcyclicInheritanceParentGrandchildDependency2/TestClassChild;

    invoke-direct {v1}, LcyclicInheritanceParentGrandchildDependency2/TestClassChild;-><init>()V

    const/4 v2, 0x0

    aput-object v1, v0, v2

    new-instance v1, LcyclicInheritanceParentGrandchildDependency2/TestClassChild;

    invoke-direct {v1}, LcyclicInheritanceParentGrandchildDependency2/TestClassChild;-><init>()V

    const/4 v2, 0x1

    aput-object v1, v0, v2

    sput-object v0, LcyclicInheritanceParentGrandchildDependency2/TestClassParent;->children:[LcyclicInheritanceParentGrandchildDependency2/TestClassChild;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method
