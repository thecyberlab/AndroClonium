.class public LcyclicInheritanceParentGrandchildDependency3/TestClassParent;
.super Ljava/lang/Object;
.source "TestClassParent.java"


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

    sput-object v0, LcyclicInheritanceParentGrandchildDependency3/TestClassParent;->testParent:LcyclicInheritanceParentGrandchildDependency3/TestClassParent;

    .line 6
    new-instance v0, LcyclicInheritanceParentGrandchildDependency3/TestClass;

    invoke-direct {v0}, LcyclicInheritanceParentGrandchildDependency3/TestClass;-><init>()V

    sput-object v0, LcyclicInheritanceParentGrandchildDependency3/TestClassParent;->test:LcyclicInheritanceParentGrandchildDependency3/TestClass;

    .line 7
    new-instance v0, LcyclicInheritanceParentGrandchildDependency3/TestClassChild;

    invoke-direct {v0}, LcyclicInheritanceParentGrandchildDependency3/TestClassChild;-><init>()V

    sput-object v0, LcyclicInheritanceParentGrandchildDependency3/TestClassParent;->testChild:LcyclicInheritanceParentGrandchildDependency3/TestClassChild;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 9
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public constructor <init>(LcyclicInheritanceParentGrandchildDependency3/TestClassChild;)V
    .locals 0
    .param p1, "child"    # LcyclicInheritanceParentGrandchildDependency3/TestClassChild;
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0
        }
        names = {
            "child"
        }
    .end annotation

    .line 11
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 13
    return-void
.end method
