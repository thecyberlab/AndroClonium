.class public LConstructorExecutionTraceWithNonSuperNestedCallsTest_C;
.super LConstructorExecutionTraceWithNonSuperNestedCallsTest_B;
.source "ConstructorExecutionTraceWithNonSuperNestedCallsTest_C.java"


# direct methods
.method public constructor <init>()V
    .locals 1

    new-instance v0, LConstructorExecutionTraceWithNonSuperNestedCallsTest_Collection;

    invoke-direct {v0}, LConstructorExecutionTraceWithNonSuperNestedCallsTest_Collection;-><init>()V

    invoke-direct {p0, v0}, LConstructorExecutionTraceWithNonSuperNestedCallsTest_B;-><init>(LConstructorExecutionTraceWithNonSuperNestedCallsTest_Collection;)V

    new-instance v0, LConstructorExecutionTraceWithNonSuperNestedCallsTest_Collection;

    invoke-direct {v0}, LConstructorExecutionTraceWithNonSuperNestedCallsTest_Collection;-><init>()V

    return-void
.end method
