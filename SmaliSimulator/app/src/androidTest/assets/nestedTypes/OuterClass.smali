.class public LnestedTypes/OuterClass;
.super Ljava/lang/Object;
.source "OuterClass.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        LnestedTypes/OuterClass$NestedClass;
    }
.end annotation


# instance fields
.field a:I


# direct methods
.method public constructor <init>()V
    .locals 1

    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    const/16 v0, 0xa

    iput v0, p0, LnestedTypes/OuterClass;->a:I

    return-void
.end method
