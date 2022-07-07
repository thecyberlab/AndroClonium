.class public LConstructorExecutionTraceUnderExceptionTest;
.super Ljava/lang/Object;
.source "ConstructorExecutionTraceUnderExceptionTest.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static test(Ljava/lang/String;)V
    .locals 1

    new-instance v0, LConstructorExecutionTraceUnderExceptionTest_C;

    invoke-direct {v0, p0}, LConstructorExecutionTraceUnderExceptionTest_C;-><init>(Ljava/lang/String;)V

    return-void
.end method
