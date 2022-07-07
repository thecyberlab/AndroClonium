.class public LEnumWithConstantClassBodiesTest;
.super Ljava/lang/Object;
.source "EnumWithConstantClassBodiesTest.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static test1()D
    .locals 2

    .line 6
    sget-object v0, LEnumWithConstantClassBodies;->STATE1:LEnumWithConstantClassBodies;

    invoke-virtual {v0}, LEnumWithConstantClassBodies;->testMethod()D

    move-result-wide v0

    return-wide v0
.end method

.method public static test2()D
    .locals 2

    .line 10
    sget-object v0, LEnumWithConstantClassBodies;->STATE2:LEnumWithConstantClassBodies;

    invoke-virtual {v0}, LEnumWithConstantClassBodies;->testMethod()D

    move-result-wide v0

    return-wide v0
.end method

.method public static test3()D
    .locals 2

    .line 14
    sget-object v0, LEnumWithConstantClassBodies;->STATE3:LEnumWithConstantClassBodies;

    invoke-virtual {v0}, LEnumWithConstantClassBodies;->testMethod()D

    move-result-wide v0

    return-wide v0
.end method
