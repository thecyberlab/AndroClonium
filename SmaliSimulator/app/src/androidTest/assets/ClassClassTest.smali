.class public LClassClassTest;
.super LClassClassParent;
.source "ClassClassTest.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 10
    invoke-direct {p0}, LClassClassParent;-><init>()V

    return-void
.end method

.method private a(Ljava/lang/Class;)Ljava/lang/Object;
    .locals 0
    .param p1, "c"    # Ljava/lang/Class;

    .line 128
    return-object p1
.end method

.method public static b(Ljava/lang/Class;)Ljava/lang/Object;
    .locals 0
    .param p0, "c"    # Ljava/lang/Class;

    .line 132
    return-object p0
.end method

.method public static test1()V
    .locals 1

    .line 13
    const-class v0, Ljava/util/LinkedList;

    .line 14
    .local v0, "cls":Ljava/lang/Class;
    return-void
.end method

.method public static test10()Ljava/lang/Object;
    .locals 3

    .line 64
    const-class v0, LClassClassTest;

    .line 65
    .local v0, "c":Ljava/lang/Class;
    new-instance v1, LClassClassTest;

    invoke-direct {v1}, LClassClassTest;-><init>()V

    .line 66
    .local v1, "ct":LClassClassTest;
    invoke-virtual {v1, v0}, LClassClassTest;->c(Ljava/lang/Class;)Ljava/lang/Object;

    move-result-object v2

    return-object v2
.end method

.method public static test11()Ljava/lang/Object;
    .locals 3

    .line 70
    const-class v0, LClassClassTest;

    .line 71
    .local v0, "c":Ljava/lang/Class;
    new-instance v1, LClassClassTest;

    invoke-direct {v1}, LClassClassTest;-><init>()V

    .line 72
    .local v1, "ct":LClassClassTest;
    invoke-virtual {v1, v0}, LClassClassTest;->c(Ljava/lang/Class;)Ljava/lang/Object;

    move-result-object v2

    return-object v2
.end method

.method public static test12()Z
    .locals 3

    .line 76
    new-instance v0, LClassClassTest;

    invoke-direct {v0}, LClassClassTest;-><init>()V

    .line 77
    .local v0, "ct":LClassClassTest;
    const-class v1, LClassClassTest;

    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/Object;->equals(Ljava/lang/Object;)Z

    move-result v1

    return v1
.end method

.method public static test13()Ljava/lang/Object;
    .locals 3

    .line 81
    const-class v0, Ljava/lang/Object;

    .line 82
    .local v0, "c":Ljava/lang/Class;
    new-instance v1, LClassClassTest;

    invoke-direct {v1}, LClassClassTest;-><init>()V

    .line 83
    .local v1, "ct":LClassClassTest;
    invoke-direct {v1, v0}, LClassClassTest;->a(Ljava/lang/Class;)Ljava/lang/Object;

    move-result-object v2

    return-object v2
.end method

.method public static test14()Ljava/lang/Object;
    .locals 2

    .line 87
    const-class v0, Ljava/lang/Object;

    .line 88
    .local v0, "c":Ljava/lang/Class;
    invoke-static {v0}, LClassClassTest;->b(Ljava/lang/Class;)Ljava/lang/Object;

    move-result-object v1

    return-object v1
.end method

.method public static test15()Ljava/lang/Object;
    .locals 3

    .line 92
    const-class v0, Ljava/lang/Object;

    .line 93
    .local v0, "c":Ljava/lang/Class;
    new-instance v1, LClassClassTest;

    invoke-direct {v1}, LClassClassTest;-><init>()V

    .line 94
    .local v1, "ct":LClassClassTest;
    invoke-virtual {v1, v0}, LClassClassTest;->c(Ljava/lang/Class;)Ljava/lang/Object;

    move-result-object v2

    return-object v2
.end method

.method public static test16()Ljava/lang/Object;
    .locals 3

    .line 98
    const-class v0, Ljava/lang/Object;

    .line 99
    .local v0, "c":Ljava/lang/Class;
    new-instance v1, LClassClassTest;

    invoke-direct {v1}, LClassClassTest;-><init>()V

    .line 100
    .local v1, "ct":LClassClassTest;
    invoke-virtual {v1, v0}, LClassClassTest;->c(Ljava/lang/Class;)Ljava/lang/Object;

    move-result-object v2

    return-object v2
.end method

.method public static test17()V
    .locals 1

    .line 104
    const-class v0, [I

    .line 105
    .local v0, "cls":Ljava/lang/Class;
    return-void
.end method

.method public static test18()V
    .locals 1

    .line 108
    const-class v0, [Ljava/lang/Object;

    .line 109
    .local v0, "cls":Ljava/lang/Class;
    return-void
.end method

.method public static test19()V
    .locals 1

    .line 112
    const-class v0, [LClassClassTest;

    .line 113
    .local v0, "cls":Ljava/lang/Class;
    return-void
.end method

.method public static test2()V
    .locals 1

    .line 17
    new-instance v0, Ljava/util/LinkedList;

    invoke-direct {v0}, Ljava/util/LinkedList;-><init>()V

    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v0

    .line 18
    .local v0, "cls":Ljava/lang/Class;
    return-void
.end method

.method public static test20()Ljava/lang/String;
    .locals 1

    .line 116
    const-class v0, [I

    invoke-virtual {v0}, Ljava/lang/Class;->getName()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static test21()Ljava/lang/String;
    .locals 1

    .line 120
    const-class v0, [LClassClassTest;

    invoke-virtual {v0}, Ljava/lang/Class;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static test22()V
    .locals 1

    .line 124
    const-class v0, [Ljava/lang/Object;

    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v0

    .line 125
    .local v0, "cls":Ljava/lang/Class;
    return-void
.end method

.method public static test2_1()V
    .locals 1

    .line 21
    const-class v0, Ljava/lang/Class;

    .line 22
    .local v0, "cls":Ljava/lang/Class;
    return-void
.end method

.method public static test3()V
    .locals 1

    .line 25
    const-class v0, LClassClassTest;

    .line 26
    .local v0, "cls":Ljava/lang/Class;
    return-void
.end method

.method public static test4()V
    .locals 2

    .line 29
    new-instance v0, LClassClassTest;

    invoke-direct {v0}, LClassClassTest;-><init>()V

    .line 30
    .local v0, "c":LClassClassTest;
    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v1

    .line 31
    .local v1, "cls":Ljava/lang/Class;
    return-void
.end method

.method public static test4_1()Ljava/lang/String;
    .locals 2

    .line 34
    const-class v0, Ljava/util/LinkedList;

    .line 35
    .local v0, "cls":Ljava/lang/Class;
    invoke-virtual {v0}, Ljava/lang/Class;->toString()Ljava/lang/String;

    move-result-object v1

    return-object v1
.end method

.method public static test5()V
    .locals 1

    .line 39
    const-class v0, Ljava/lang/Enum;

    .line 40
    .local v0, "cls":Ljava/lang/Class;
    return-void
.end method

.method public static test6()V
    .locals 1

    .line 44
    const-class v0, Ljava/nio/file/AccessMode;

    .line 45
    .local v0, "cls":Ljava/lang/Class;
    return-void
.end method

.method public static test7()V
    .locals 1

    .line 49
    sget-object v0, Ljava/nio/file/AccessMode;->EXECUTE:Ljava/nio/file/AccessMode;

    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v0

    .line 50
    .local v0, "cls":Ljava/lang/Class;
    return-void
.end method

.method public static test8()Ljava/lang/Object;
    .locals 3

    .line 53
    const-class v0, LClassClassTest;

    .line 54
    .local v0, "c":Ljava/lang/Class;
    new-instance v1, LClassClassTest;

    invoke-direct {v1}, LClassClassTest;-><init>()V

    .line 55
    .local v1, "ct":LClassClassTest;
    invoke-direct {v1, v0}, LClassClassTest;->a(Ljava/lang/Class;)Ljava/lang/Object;

    move-result-object v2

    return-object v2
.end method

.method public static test9()Ljava/lang/Object;
    .locals 2

    .line 59
    const-class v0, LClassClassTest;

    .line 60
    .local v0, "c":Ljava/lang/Class;
    invoke-static {v0}, LClassClassTest;->b(Ljava/lang/Class;)Ljava/lang/Object;

    move-result-object v1

    return-object v1
.end method


# virtual methods
.method public c(Ljava/lang/Class;)Ljava/lang/Object;
    .locals 0
    .param p1, "c"    # Ljava/lang/Class;

    .line 136
    return-object p1
.end method

.method public d(Ljava/lang/Class;)Ljava/lang/Object;
    .locals 1
    .param p1, "c"    # Ljava/lang/Class;

    .line 140
    invoke-super {p0, p1}, LClassClassParent;->c(Ljava/lang/Class;)Ljava/lang/Object;

    move-result-object v0

    return-object v0
.end method
