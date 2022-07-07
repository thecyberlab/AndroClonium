.class public LAmbiguousValueCheckCast;
.super Ljava/lang/Object;
.source "AmbiguousValueCheckCast.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public static test1(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 1

    move-object v0, p0

    check-cast v0, [Ljava/lang/Object;

    return-object v0
.end method

.method public static test2(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 1

    move-object v0, p0

    check-cast v0, [I

    return-object v0
.end method
