.class public LConstructorExecutionTraceWithNonSuperNestedCallsTest;
.super Ljava/lang/Object;
.source "ConstructorExecutionTraceWithNonSuperNestedCallsTest.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static test()V
    .locals 1

    new-instance v0, LConstructorExecutionTraceWithNonSuperNestedCallsTest_C;

    invoke-direct {v0}, LConstructorExecutionTraceWithNonSuperNestedCallsTest_C;-><init>()V

    return-void
.end method
