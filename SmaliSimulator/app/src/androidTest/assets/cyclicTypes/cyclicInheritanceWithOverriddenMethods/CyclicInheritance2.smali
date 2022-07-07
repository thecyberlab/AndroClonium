.class public LcyclicInheritanceWithOverriddenMethods/CyclicInheritance2;
.super LcyclicInheritanceWithOverriddenMethods/CyclicInheritance1;
.source "CyclicInheritance2.java"


# instance fields
.field c2_1:LcyclicInheritanceWithOverriddenMethods/CyclicInheritance1;

# direct methods
.method public constructor <init>()V
    .locals 0

    .line 3
    invoke-direct {p0}, LcyclicInheritanceWithOverriddenMethods/CyclicInheritance1;-><init>()V

    return-void
.end method


.method public test(I)I
    .locals 1

    const/4 v0, 0x0

    invoke-super {p0, v0}, LcyclicInheritanceWithOverriddenMethods/CyclicInheritance1;->test(I)I

    move-result v0

    add-int/lit8 v0, v0, 0x1

    return v0
.end method