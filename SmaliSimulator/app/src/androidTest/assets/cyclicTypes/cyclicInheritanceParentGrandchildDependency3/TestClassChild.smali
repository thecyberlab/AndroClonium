.class public LcyclicInheritanceParentGrandchildDependency3/TestClassChild;
.super LcyclicInheritanceParentGrandchildDependency3/TestClass;
.source "TestClassChild.java"


# static fields
.field static test:LcyclicInheritanceParentGrandchildDependency3/TestClass;

.field static testChild:LcyclicInheritanceParentGrandchildDependency3/TestClassChild;

.field static testParent:LcyclicInheritanceParentGrandchildDependency3/TestClassParent;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 5
    new-instance v0, LcyclicInheritanceParentGrandchildDependency3/TestClassParent;

    invoke-direct {v0}, LcyclicInheritanceParentGrandchildDependency3/TestClassParent;-><init>()V

    sput-object v0, LcyclicInheritanceParentGrandchildDependency3/TestClassChild;->testParent:LcyclicInheritanceParentGrandchildDependency3/TestClassParent;

    .line 6
    new-instance v0, LcyclicInheritanceParentGrandchildDependency3/TestClass;

    invoke-direct {v0}, LcyclicInheritanceParentGrandchildDependency3/TestClass;-><init>()V

    sput-object v0, LcyclicInheritanceParentGrandchildDependency3/TestClassChild;->test:LcyclicInheritanceParentGrandchildDependency3/TestClass;

    .line 7
    new-instance v0, LcyclicInheritanceParentGrandchildDependency3/TestClassChild;

    invoke-direct {v0}, LcyclicInheritanceParentGrandchildDependency3/TestClassChild;-><init>()V

    sput-object v0, LcyclicInheritanceParentGrandchildDependency3/TestClassChild;->testChild:LcyclicInheritanceParentGrandchildDependency3/TestClassChild;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 3
    invoke-direct {p0}, LcyclicInheritanceParentGrandchildDependency3/TestClass;-><init>()V

    return-void
.end method
