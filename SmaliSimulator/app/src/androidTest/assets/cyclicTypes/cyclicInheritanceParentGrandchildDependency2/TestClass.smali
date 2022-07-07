.class public LcyclicInheritanceParentGrandchildDependency2/TestClass;
.super LcyclicInheritanceParentGrandchildDependency2/TestClassParent;
.source "TestClass.java"


# static fields
.field static child:LcyclicInheritanceParentGrandchildDependency2/TestClassChild;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 4
    new-instance v0, LcyclicInheritanceParentGrandchildDependency2/TestClassChild;

    invoke-direct {v0}, LcyclicInheritanceParentGrandchildDependency2/TestClassChild;-><init>()V

    sput-object v0, LcyclicInheritanceParentGrandchildDependency2/TestClass;->child:LcyclicInheritanceParentGrandchildDependency2/TestClassChild;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 3
    invoke-direct {p0}, LcyclicInheritanceParentGrandchildDependency2/TestClassParent;-><init>()V

    return-void
.end method
