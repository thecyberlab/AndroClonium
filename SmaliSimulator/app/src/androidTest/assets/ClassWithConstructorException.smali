.class public LClassWithConstructorException;
.super Ljava/lang/Object;
.source "ClassWithConstructorException.java"


# direct methods
.method public constructor <init>()V
    .locals 2

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    new-instance v0, Ljava/lang/IllegalStateException;

    const-string v1, "Exception in my constructor!!"

    invoke-direct {v0, v1}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw v0
.end method

.method public constructor <init>(I)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public constructor <init>(II)V
    .locals 1

    div-int v0, p1, p2

    invoke-direct {p0, v0}, LClassWithConstructorException;-><init>(I)V

    return-void
.end method

.method public static test()LClassWithConstructorException;
    .locals 1

    new-instance v0, LClassWithConstructorException;

    invoke-direct {v0}, LClassWithConstructorException;-><init>()V

    return-object v0
.end method

.method public static test2()LClassWithConstructorException;
    .locals 3

    new-instance v0, LClassWithConstructorException;

    const/4 v1, 0x1

    const/4 v2, 0x0

    invoke-direct {v0, v1, v2}, LClassWithConstructorException;-><init>(II)V

    return-object v0
.end method
