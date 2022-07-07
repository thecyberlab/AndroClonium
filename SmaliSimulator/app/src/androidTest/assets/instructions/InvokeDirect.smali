.class public LInvokeDirect;
.super Ljava/lang/Object;
.source "InvokeDirect.java"


# instance fields
.field testField:I


# direct methods
.method public constructor <init>()V
    .locals 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/16 v0, 0xa

    iput v0, p0, LInvokeDirect;->testField:I

    invoke-direct {p0}, LInvokeDirect;->incr()V

    return-void
.end method

.method public constructor <init>(I)V
    .locals 0
    .param p1, "i"    # I

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput p1, p0, LInvokeDirect;->testField:I

    return-void
.end method

.method private incr()V
    .locals 1

    iget v0, p0, LInvokeDirect;->testField:I

    add-int/lit8 v0, v0, 0x1

    iput v0, p0, LInvokeDirect;->testField:I

    return-void
.end method

.method public static test00()Ljava/lang/Object;
    .locals 1
    new-instance v0, Ljava/lang/Object;

    invoke-direct {v0}, Ljava/lang/Object;-><init>()V

    return-object v0
.end method

.method public static test0()Ljava/lang/Object;
    .locals 1
    new-instance v0, LInvokeDirect;

    invoke-direct {v0}, LInvokeDirect;-><init>()V

    return-object v0
.end method

.method public static test1()I
    .locals 2

    new-instance v0, LInvokeDirect;

    invoke-direct {v0}, LInvokeDirect;-><init>()V

    iget v1, v0, LInvokeDirect;->testField:I

    return v1
.end method

.method public static test2()I
    .locals 2

    new-instance v0, LInvokeDirect;

    const/16 v1, 0x50

    invoke-direct {v0, v1}, LInvokeDirect;-><init>(I)V

    iget v1, v0, LInvokeDirect;->testField:I

    return v1
.end method

.method public static test3()V
    .locals 1

    new-instance v0, LInvokeDirect;

    invoke-direct {v0}, LInvokeDirect;-><init>()V

    invoke-direct {v0}, LInvokeDirect;->throwException()V

    return-void
.end method

.method public static test4()V
    .locals 1

    new-instance v0, LInvokeDirect;

    invoke-direct {v0}, LInvokeDirect;-><init>()V

    invoke-direct {v0}, LInvokeDirect;->throwException2()V

    return-void
.end method

.method public static test5()V
    .locals 1

    const/4 v0, 0x0

    invoke-direct {v0}, LInvokeDirect;->incr()V

    return-void
.end method

.method private throwException()V
    .locals 1

    new-instance v0, Ljava/lang/Exception;

    invoke-direct {v0}, Ljava/lang/Exception;-><init>()V

    throw v0
.end method

.method private throwException2()V
    .locals 2

    new-instance v0, Ljava/util/ArrayList;

    const/4 v1, -0x1

    invoke-direct {v0, v1}, Ljava/util/ArrayList;-><init>(I)V

    return-void
.end method


.method private pairRegisterArgumentTest(IJIJI)J
    .locals 4

    int-to-long v0, p1

    add-long/2addr v0, p2

    int-to-long v2, p4

    add-long/2addr v0, v2

    add-long/2addr v0, p5

    int-to-long v2, p7

    add-long/2addr v0, v2

    return-wide v0
.end method