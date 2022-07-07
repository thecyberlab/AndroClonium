.class public LnestedTypes/OuterClass2$StaticNestedClass;
.super Ljava/lang/Object;
.source "OuterClass2.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = LnestedTypes/OuterClass2;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x9
    name = "StaticNestedClass"
.end annotation


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 7
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static synthetic access$100(LnestedTypes/OuterClass2$StaticNestedClass;)I
    .locals 1
    .param p0, "x0"    # LnestedTypes/OuterClass2$StaticNestedClass;

    .line 7
    invoke-direct {p0}, LnestedTypes/OuterClass2$StaticNestedClass;->testInner()I

    move-result v0

    return v0
.end method

.method private testInner()I
    .locals 1

    .line 10
    invoke-static {}, LnestedTypes/OuterClass2;->access$000()I

    move-result v0

    add-int/lit8 v0, v0, 0x4

    return v0
.end method
