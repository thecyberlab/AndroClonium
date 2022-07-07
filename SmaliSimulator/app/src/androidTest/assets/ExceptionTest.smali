.class public LExceptionTest;
.super Ljava/lang/Object;
.source "ExceptionTest.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static exceptionTestMethod1(I)I
    .locals 1
    .param p0, "a"    # I

    .line 6
    const/16 v0, 0x10

    div-int/2addr v0, p0

    return v0
.end method

.method public static exceptionTestMethod2(I)I
    .locals 2
    .param p0, "a"    # I
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .line 11
    const/16 v0, 0x10

    :try_start_0
    div-int/2addr v0, p0
    :try_end_0
    .catch Ljava/lang/ArithmeticException; {:try_start_0 .. :try_end_0} :catch_0

    return v0

    .line 12
    :catch_0
    move-exception v0

    .line 13
    .local v0, "e":Ljava/lang/ArithmeticException;
    new-instance v1, Ljava/lang/Exception;

    invoke-direct {v1}, Ljava/lang/Exception;-><init>()V

    throw v1
.end method

.method public static exceptionTestMethod3(I)I
    .locals 2
    .param p0, "a"    # I

    .line 21
    const/4 v0, -0x1

    if-eq p0, v0, :cond_1

    .line 24
    const/16 v0, 0x10

    :try_start_0
    div-int/2addr v0, p0

    .line 25
    .local v0, "b":I
    const/4 v1, 0x1

    if-eq v0, v1, :cond_0

    .line 32
    goto :goto_2

    .line 26
    :cond_0
    new-instance v1, Ljava/lang/UnsupportedOperationException;

    invoke-direct {v1}, Ljava/lang/UnsupportedOperationException;-><init>()V

    .end local p0    # "a":I
    throw v1

    .line 30
    .end local v0    # "b":I
    .restart local p0    # "a":I
    :catch_0
    move-exception v0

    goto :goto_0

    .line 28
    :catch_1
    move-exception v0

    goto :goto_1

    .line 22
    :cond_1
    new-instance v0, Ljava/lang/IllegalArgumentException;

    invoke-direct {v0}, Ljava/lang/IllegalArgumentException;-><init>()V

    .end local p0    # "a":I
    throw v0
    :try_end_0
    .catch Ljava/lang/ArithmeticException; {:try_start_0 .. :try_end_0} :catch_1
    .catch Ljava/lang/IllegalArgumentException; {:try_start_0 .. :try_end_0} :catch_0

    .line 31
    .local v0, "e":Ljava/lang/IllegalArgumentException;
    .restart local p0    # "a":I
    :goto_0
    const/16 v1, 0xc8

    move v0, v1

    .local v1, "b":I
    goto :goto_3

    .line 29
    .end local v1    # "b":I
    .local v0, "e":Ljava/lang/ArithmeticException;
    :goto_1
    const/16 v0, 0x64

    .line 32
    .local v0, "b":I
    :goto_2
    nop

    .line 33
    :goto_3
    return v0
.end method

.method public static exceptionTestMethod4(I)I
    .locals 2
    .param p0, "a"    # I
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .line 39
    const/16 v0, 0x10

    :try_start_0
    div-int/2addr v0, p0
    :try_end_0
    .catch Ljava/lang/ArithmeticException; {:try_start_0 .. :try_end_0} :catch_1
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    return v0

    .line 43
    :catch_0
    move-exception v0

    .line 44
    .local v0, "e":Ljava/lang/Exception;
    const/4 v1, -0x1

    return v1

    .line 40
    .end local v0    # "e":Ljava/lang/Exception;
    :catch_1
    move-exception v0

    .line 41
    .local v0, "e":Ljava/lang/ArithmeticException;
    const/16 v1, 0xc

    return v1
.end method

.method public static exceptionTestMethod5(II)I
    .locals 3
    .param p0, "a"    # I
    .param p1, "b"    # I
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .line 50
    const/16 v0, 0x10

    :try_start_0
    div-int/2addr v0, p0
    :try_end_0
    .catch Ljava/lang/ArithmeticException; {:try_start_0 .. :try_end_0} :catch_0

    return v0

    .line 51
    :catch_0
    move-exception v1

    .line 53
    .local v1, "e":Ljava/lang/ArithmeticException;
    :try_start_1
    div-int/2addr v0, p1
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_1

    return v0

    .line 54
    :catch_1
    move-exception v0

    .line 55
    .local v0, "e2":Ljava/lang/Exception;
    new-instance v2, Ljava/lang/IllegalArgumentException;

    invoke-direct {v2}, Ljava/lang/IllegalArgumentException;-><init>()V

    throw v2
.end method

.method public static exceptionTestMethod6(I)I
    .locals 2
    .param p0, "a"    # I

    .line 62
    const/4 v0, 0x0

    .line 64
    .local v0, "b":I
    const/4 v1, -0x1

    if-eq p0, v1, :cond_1

    .line 67
    const/16 v1, 0x8

    :try_start_0
    div-int/2addr v1, p0

    move v0, v1

    .line 68
    const/4 v1, 0x1

    if-eq v0, v1, :cond_0

    .line 78
    goto :goto_3

    .line 69
    :cond_0
    new-instance v1, Ljava/lang/UnsupportedOperationException;

    invoke-direct {v1}, Ljava/lang/UnsupportedOperationException;-><init>()V

    .end local v0    # "b":I
    .end local p0    # "a":I
    throw v1

    .line 78
    .restart local v0    # "b":I
    .restart local p0    # "a":I
    :catchall_0
    move-exception v1

    goto :goto_0

    .line 74
    :catch_0
    move-exception v1

    goto :goto_1

    .line 71
    :catch_1
    move-exception v1

    goto :goto_2

    .line 65
    :cond_1
    new-instance v1, Ljava/lang/IllegalArgumentException;

    invoke-direct {v1}, Ljava/lang/IllegalArgumentException;-><init>()V

    .end local v0    # "b":I
    .end local p0    # "a":I
    throw v1
    :try_end_0
    .catch Ljava/lang/ArithmeticException; {:try_start_0 .. :try_end_0} :catch_1
    .catch Ljava/lang/IllegalArgumentException; {:try_start_0 .. :try_end_0} :catch_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 78
    .restart local v0    # "b":I
    .restart local p0    # "a":I
    :goto_0
    add-int/lit8 v0, v0, 0xa

    .line 79
    throw v1

    .line 75
    .local v1, "e":Ljava/lang/IllegalArgumentException;
    :goto_1
    const/4 v0, 0x6

    .end local v1    # "e":Ljava/lang/IllegalArgumentException;
    goto :goto_3

    .line 72
    .local v1, "e":Ljava/lang/ArithmeticException;
    :goto_2
    const/4 v0, 0x3

    .line 78
    .end local v1    # "e":Ljava/lang/ArithmeticException;
    :goto_3
    add-int/lit8 v0, v0, 0xa

    .line 79
    nop

    .line 80
    return v0
.end method

.method public static exceptionTestMethod7(I)I
    .locals 4
    .param p0, "a"    # I

    .line 84
    const/4 v0, 0x0

    .line 86
    .local v0, "b":I
    const/16 v1, 0x12c

    :try_start_0
    new-instance v2, Ljava/lang/UnsupportedOperationException;

    invoke-direct {v2}, Ljava/lang/UnsupportedOperationException;-><init>()V

    .end local v0    # "b":I
    .end local p0    # "a":I
    throw v2
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 97
    .restart local v0    # "b":I
    .restart local p0    # "a":I
    :catchall_0
    move-exception v2

    goto :goto_0

    .line 88
    :catch_0
    move-exception v2

    .line 90
    .local v2, "e":Ljava/lang/Exception;
    const/16 v3, 0x8

    :try_start_1
    div-int/2addr v3, p0
    :try_end_1
    .catch Ljava/lang/ArithmeticException; {:try_start_1 .. :try_end_1} :catch_2
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    move v0, v3

    .line 94
    goto :goto_2

    .line 98
    .end local v2    # "e":Ljava/lang/Exception;
    :goto_0
    add-int/lit8 v3, v0, -0x1

    :try_start_2
    div-int/2addr v1, v3
    :try_end_2
    .catch Ljava/lang/Exception; {:try_start_2 .. :try_end_2} :catch_1

    .line 99
    .local v1, "c":I
    add-int/2addr v0, v1

    .line 102
    .end local v1    # "c":I
    goto :goto_1

    .line 100
    :catch_1
    move-exception v1

    .line 101
    .local v1, "e":Ljava/lang/Exception;
    add-int/lit16 v0, v0, 0x3e8

    .line 103
    .end local v1    # "e":Ljava/lang/Exception;
    :goto_1
    throw v2

    .line 92
    .restart local v2    # "e":Ljava/lang/Exception;
    :catch_2
    move-exception v3

    .line 93
    .local v3, "e2":Ljava/lang/ArithmeticException;
    const/4 v0, 0x3

    .line 98
    .end local v2    # "e":Ljava/lang/Exception;
    .end local v3    # "e2":Ljava/lang/ArithmeticException;
    :goto_2
    add-int/lit8 v2, v0, -0x1

    :try_start_3
    div-int/2addr v1, v2
    :try_end_3
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_3} :catch_3

    .line 99
    .local v1, "c":I
    add-int/2addr v0, v1

    .line 102
    .end local v1    # "c":I
    goto :goto_3

    .line 100
    :catch_3
    move-exception v1

    .line 101
    .local v1, "e":Ljava/lang/Exception;
    add-int/lit16 v0, v0, 0x3e8

    .line 103
    .end local v1    # "e":Ljava/lang/Exception;
    nop

    .line 104
    :goto_3
    return v0
.end method

.method public static exceptionTestMethod8(I)I
    .locals 2
    .param p0, "a"    # I

    .line 110
    const/16 v0, 0x8

    :try_start_0
    div-int/2addr v0, p0
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 114
    .local v0, "b":I
    new-instance v1, Ljava/lang/UnsupportedOperationException;

    invoke-direct {v1}, Ljava/lang/UnsupportedOperationException;-><init>()V

    throw v1

    .end local v0    # "b":I
    :catchall_0
    move-exception v0

    new-instance v0, Ljava/lang/UnsupportedOperationException;

    invoke-direct {v0}, Ljava/lang/UnsupportedOperationException;-><init>()V

    throw v0

    .line 111
    :catch_0
    move-exception v0

    .line 112
    .local v0, "e":Ljava/lang/Exception;
    nop

    .line 114
    new-instance v1, Ljava/lang/UnsupportedOperationException;

    invoke-direct {v1}, Ljava/lang/UnsupportedOperationException;-><init>()V

    throw v1
.end method


.method public static customExceptionTest()V
    .locals 1
    .annotation system Ldalvik/annotation/Throws;
        value = {
            LCustomException;
        }
    .end annotation

    .line 119
    new-instance v0, LCustomException;

    invoke-direct {v0}, LCustomException;-><init>()V

    throw v0
.end method

.method public static customExceptionTest2()V
    .locals 0
    .annotation system Ldalvik/annotation/Throws;
        value = {
            LCustomException;
        }
    .end annotation

    .line 123
    invoke-static {}, LExceptionTest;->customExceptionTest()V

    .line 124
    return-void
.end method