.class public LSimpleEnumTest;
.super Ljava/lang/Object;
.source "SimpleEnumTest.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static test1()V
    .locals 3

    .line 5
    sget-object v0, LSimpleEnum;->STATE1:LSimpleEnum;

    .line 6
    .local v0, "s1":LSimpleEnum;
    sget-object v1, LSimpleEnum;->STATE2:LSimpleEnum;

    .line 7
    .local v1, "s2":LSimpleEnum;
    sget-object v2, LSimpleEnum;->STATE3:LSimpleEnum;

    .line 8
    .local v2, "s3":LSimpleEnum;
    return-void
.end method

.method public static test10()I
    .locals 1

    .line 42
    sget-object v0, LSimpleEnum;->STATE1:LSimpleEnum;

    invoke-virtual {v0}, LSimpleEnum;->ordinal()I

    move-result v0

    return v0
.end method

.method public static test11()I
    .locals 1

    .line 46
    sget-object v0, LSimpleEnum;->STATE1:LSimpleEnum;

    invoke-virtual {v0}, LSimpleEnum;->hashCode()I

    move-result v0

    return v0
.end method

.method public static test12()I
    .locals 2

    .line 50
    sget-object v0, LSimpleEnum;->STATE2:LSimpleEnum;

    sget-object v1, LSimpleEnum;->STATE2:LSimpleEnum;

    invoke-virtual {v0, v1}, LSimpleEnum;->compareTo(Ljava/lang/Enum;)I

    move-result v0

    return v0
.end method

.method public static test13()I
    .locals 2

    .line 54
    sget-object v0, LSimpleEnum;->STATE2:LSimpleEnum;

    sget-object v1, LSimpleEnum;->STATE1:LSimpleEnum;

    invoke-virtual {v0, v1}, LSimpleEnum;->compareTo(Ljava/lang/Enum;)I

    move-result v0

    return v0
.end method

.method public static test14()I
    .locals 2

    .line 58
    sget-object v0, LSimpleEnum;->STATE2:LSimpleEnum;

    sget-object v1, LSimpleEnum;->STATE3:LSimpleEnum;

    invoke-virtual {v0, v1}, LSimpleEnum;->compareTo(Ljava/lang/Enum;)I

    move-result v0

    return v0
.end method

.method public static test2()V
    .locals 3

    const-string v0, "STATE1"

    invoke-static {v0}, LSimpleEnum;->valueOf(Ljava/lang/String;)LSimpleEnum;

    move-result-object v0

    const-string v1, "STATE2"

    invoke-static {v1}, LSimpleEnum;->valueOf(Ljava/lang/String;)LSimpleEnum;

    move-result-object v1

    const-string v2, "STATE3"

    invoke-static {v2}, LSimpleEnum;->valueOf(Ljava/lang/String;)LSimpleEnum;

    move-result-object v2

    return-void
.end method

.method public static test3()Ljava/lang/String;
    .locals 1

    .line 17
    sget-object v0, LSimpleEnum;->STATE1:LSimpleEnum;

    invoke-virtual {v0}, LSimpleEnum;->name()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static test4()Ljava/lang/String;
    .locals 1

    .line 21
    sget-object v0, LSimpleEnum;->STATE1:LSimpleEnum;

    invoke-virtual {v0}, LSimpleEnum;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static test5()V
    .locals 1

    .line 25
    sget-object v0, LSimpleEnum;->STATE1:LSimpleEnum;

    invoke-static {}, LSimpleEnum;->values()[LSimpleEnum;

    move-result-object v0

    .line 26
    .local v0, "vals":[LSimpleEnum;
    return-void
.end method

.method public static test6()Z
    .locals 3

    .line 29
    sget-object v0, LSimpleEnum;->STATE1:LSimpleEnum;

    invoke-static {}, LSimpleEnum;->values()[LSimpleEnum;

    move-result-object v0

    .line 30
    .local v0, "vals":[LSimpleEnum;
    sget-object v1, LSimpleEnum;->STATE1:LSimpleEnum;

    const/4 v2, 0x0

    aget-object v2, v0, v2

    invoke-virtual {v1, v2}, LSimpleEnum;->equals(Ljava/lang/Object;)Z

    move-result v1

    return v1
.end method

.method public static test7()Ljava/lang/Class;
    .locals 1

    .line 34
    sget-object v0, LSimpleEnum;->STATE1:LSimpleEnum;

    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v0

    return-object v0
.end method

.method public static test8()Ljava/lang/Class;
    .locals 1

    .line 38
    sget-object v0, LSimpleEnum;->STATE1:LSimpleEnum;

    invoke-virtual {v0}, LSimpleEnum;->getDeclaringClass()Ljava/lang/Class;

    move-result-object v0

    return-object v0
.end method

.method public static test9()LSimpleEnum;
    .locals 2

    .line 42
    const-class v0, LSimpleEnum;

    const-string v1, "STATE1"

        invoke-static {v0, v1}, LSimpleEnum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object v0

    check-cast v0, LSimpleEnum;

    return-object v0
.end method
