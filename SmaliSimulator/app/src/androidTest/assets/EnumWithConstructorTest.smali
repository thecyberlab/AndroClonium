.class public LEnumWithConstructorTest;
.super Ljava/lang/Object;
.source "EnumWithConstructorTest.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static getSignature1()Ljava/lang/String;
    .locals 1

    .line 30
    const-string v0, "State1"

    invoke-static {v0}, LEnumWithConstructor;->valueOf(Ljava/lang/String;)LEnumWithConstructor;

    move-result-object v0

    invoke-virtual {v0}, LEnumWithConstructor;->getSignature()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static getSignature2()Ljava/lang/String;
    .locals 1

    .line 34
    const-string v0, "State2"

    invoke-static {v0}, LEnumWithConstructor;->valueOf(Ljava/lang/String;)LEnumWithConstructor;

    move-result-object v0

    invoke-virtual {v0}, LEnumWithConstructor;->getSignature()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static getSignature3()Ljava/lang/String;
    .locals 1

    .line 38
    const-string v0, "State3"

    invoke-static {v0}, LEnumWithConstructor;->valueOf(Ljava/lang/String;)LEnumWithConstructor;

    move-result-object v0

    invoke-virtual {v0}, LEnumWithConstructor;->getSignature()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static testName1()Ljava/lang/String;
    .locals 1

    .line 18
    sget-object v0, LEnumWithConstructor;->State1:LEnumWithConstructor;

    iget-object v0, v0, LEnumWithConstructor;->name:Ljava/lang/String;

    return-object v0
.end method

.method public static testName2()Ljava/lang/String;
    .locals 1

    .line 22
    sget-object v0, LEnumWithConstructor;->State2:LEnumWithConstructor;

    iget-object v0, v0, LEnumWithConstructor;->name:Ljava/lang/String;

    return-object v0
.end method

.method public static testName3()Ljava/lang/String;
    .locals 1

    .line 26
    sget-object v0, LEnumWithConstructor;->State3:LEnumWithConstructor;

    iget-object v0, v0, LEnumWithConstructor;->name:Ljava/lang/String;

    return-object v0
.end method

.method public static testNumber1()I
    .locals 1

    .line 6
    sget-object v0, LEnumWithConstructor;->State1:LEnumWithConstructor;

    iget v0, v0, LEnumWithConstructor;->number:I

    return v0
.end method

.method public static testNumber2()I
    .locals 1

    .line 10
    sget-object v0, LEnumWithConstructor;->State2:LEnumWithConstructor;

    iget v0, v0, LEnumWithConstructor;->number:I

    return v0
.end method

.method public static testNumber3()I
    .locals 1

    .line 14
    sget-object v0, LEnumWithConstructor;->State3:LEnumWithConstructor;

    iget v0, v0, LEnumWithConstructor;->number:I

    return v0
.end method
