.class public LCastingTest;
.super Ljava/lang/Object;
.source "CastingTest.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 6
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static test1()V
    .locals 4

    .line 9
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    .line 10
    .local v0, "l":Ljava/util/List;
    move-object v1, v0

    .line 11
    .local v1, "o":Ljava/lang/Object;
    move-object v2, v1

    check-cast v2, Ljava/util/ArrayList;

    .line 12
    .local v2, "al":Ljava/util/ArrayList;
    move-object v3, v1

    check-cast v3, Ljava/util/List;

    .line 13
    .local v3, "l2":Ljava/util/List;
    return-void
.end method

.method public static test2()V
    .locals 3

    .line 16
    new-instance v0, LComplexClass;

    invoke-direct {v0}, LComplexClass;-><init>()V

    .line 17
    .local v0, "sc":LSimpleClass;
    move-object v1, v0

    .line 18
    .local v1, "o":Ljava/lang/Object;
    move-object v2, v1

    check-cast v2, LComplexClass;

    .line 19
    .local v2, "cc":LComplexClass;
    return-void
.end method

.method public static test3()V
    .locals 2

    .line 27
    sget-object v0, Ljava/lang/System;->out:Ljava/io/PrintStream;

    .line 28
    .local v0, "o":Ljava/lang/Object;
    move-object v1, v0

    check-cast v1, Ljava/io/PrintStream;

    .line 29
    .local v1, "ps":Ljava/io/PrintStream;
    return-void
.end method

.method public static test4()V
    .locals 3

    .line 32
    new-instance v0, Ljava/util/LinkedHashMap;

    invoke-direct {v0}, Ljava/util/LinkedHashMap;-><init>()V

    .line 33
    .local v0, "o":Ljava/lang/Object;
    move-object v1, v0

    check-cast v1, Ljava/util/Map;

    .line 34
    .local v1, "m":Ljava/util/Map;
    move-object v2, v0

    check-cast v2, Ljava/util/LinkedHashMap;

    .line 35
    .local v2, "lhm":Ljava/util/LinkedHashMap;
    return-void
.end method

.method public static test5()V
    .locals 2

    .line 38
    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    .line 39
    .local v0, "o":Ljava/lang/Object;
    move-object v1, v0

    check-cast v1, Ljava/util/LinkedHashMap;

    .line 40
    .local v1, "hm":Ljava/util/LinkedHashMap;
    return-void
.end method

.method public static test6()V
    .locals 2

    .line 43
    new-instance v0, LSimpleClass;

    invoke-direct {v0}, LSimpleClass;-><init>()V

    .line 44
    .local v0, "sc":LSimpleClass;
    move-object v1, v0

    check-cast v1, LComplexClass;

    .line 45
    .local v1, "cc":LComplexClass;
    return-void
.end method
