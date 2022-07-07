.class public LConstructorExecutionTraceWithNonSuperNestedCallsTest_B;
.super LConstructorExecutionTraceWithNonSuperNestedCallsTest_A;
.source "ConstructorExecutionTraceWithNonSuperNestedCallsTest_B.java"


# direct methods
.method public constructor <init>(LConstructorExecutionTraceWithNonSuperNestedCallsTest_Collection;)V
    .locals 1

    new-instance v0, LConstructorExecutionTraceWithNonSuperNestedCallsTest_Collection;

    invoke-direct {v0}, LConstructorExecutionTraceWithNonSuperNestedCallsTest_Collection;-><init>()V

    invoke-direct {p0, v0}, LConstructorExecutionTraceWithNonSuperNestedCallsTest_A;-><init>(LConstructorExecutionTraceWithNonSuperNestedCallsTest_Collection;)V

    new-instance v0, LConstructorExecutionTraceWithNonSuperNestedCallsTest_Collection;

    invoke-direct {v0}, LConstructorExecutionTraceWithNonSuperNestedCallsTest_Collection;-><init>()V

    return-void
.end method
