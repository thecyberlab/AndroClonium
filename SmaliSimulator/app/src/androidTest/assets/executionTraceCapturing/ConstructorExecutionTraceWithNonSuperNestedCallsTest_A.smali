.class public LConstructorExecutionTraceWithNonSuperNestedCallsTest_A;
.super Ljava/util/ArrayList;
.source "ConstructorExecutionTraceWithNonSuperNestedCallsTest_A.java"


# direct methods
.method public constructor <init>(LConstructorExecutionTraceWithNonSuperNestedCallsTest_Collection;)V
    .locals 1

    new-instance v0, LConstructorExecutionTraceWithNonSuperNestedCallsTest_Collection;

    invoke-direct {v0}, LConstructorExecutionTraceWithNonSuperNestedCallsTest_Collection;-><init>()V

    invoke-direct {p0, v0}, Ljava/util/ArrayList;-><init>(Ljava/util/Collection;)V

    new-instance v0, LConstructorExecutionTraceWithNonSuperNestedCallsTest_Collection;

    invoke-direct {v0}, LConstructorExecutionTraceWithNonSuperNestedCallsTest_Collection;-><init>()V

    return-void
.end method
