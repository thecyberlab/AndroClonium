.class public LAmbiguousValueInstanceOf;
.super Ljava/lang/Object;
.source "AmbiguousValueInstanceOf.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public static test1(Ljava/lang/Object;)Z
    .locals 1

    instance-of v0, p0, [Ljava/lang/Object;

    return v0
.end method

.method public static test2(Ljava/lang/Object;)Z
    .locals 1

    instance-of v0, p0, [I

    return v0
.end method
