.class public LSelfReferencingConstructor;
.super Ljava/util/ArrayList;
.source "SelfReferencingConstructor.java"


# instance fields
.field maxSize:I


# direct methods
.method public constructor <init>(I)V
    .locals 1

    const/16 v0, 0x66

    invoke-direct {p0, v0, p1}, LSelfReferencingConstructor;-><init>(II)V

    return-void
.end method

.method public constructor <init>(II)V
    .locals 0

    invoke-direct {p0, p2}, Ljava/util/ArrayList;-><init>(I)V

    iput p1, p0, LSelfReferencingConstructor;->maxSize:I

    return-void
.end method
