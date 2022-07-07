.class public LcyclicInheritanceInInterfaces/TestCyclicInheritanceInInterfaces;
.super Ljava/lang/Object;
.source "TestCyclicInheritanceInInterfaces.java"

# interfaces
.implements LcyclicInheritanceInInterfaces/CyclicInheritanceInInterfaces2;


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public test1(LcyclicInheritanceInInterfaces/CyclicInheritanceInInterfaces2;)I
    .locals 1

    const/16 v0, 0xb

    return v0
.end method

.method public test2(LcyclicInheritanceInInterfaces/CyclicInheritanceInInterfaces1;)I
    .locals 1

    const/16 v0, 0x16

    return v0
.end method
