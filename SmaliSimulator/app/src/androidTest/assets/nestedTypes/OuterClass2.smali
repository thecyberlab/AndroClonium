.class public LnestedTypes/OuterClass2;
.super Ljava/lang/Object;
.source "OuterClass2.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        LnestedTypes/OuterClass2$StaticNestedClass;
    }
.end annotation


# static fields
.field private static x:I


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 5
    const/4 v0, 0x1

    sput v0, LnestedTypes/OuterClass2;->x:I

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static synthetic access$000()I
    .locals 1

    .line 3
    sget v0, LnestedTypes/OuterClass2;->x:I

    return v0
.end method


# virtual methods
.method public static test()I
    .locals 2

    new-instance v0, LnestedTypes/OuterClass2$StaticNestedClass;

    invoke-direct {v0}, LnestedTypes/OuterClass2$StaticNestedClass;-><init>()V

    invoke-static {v0}, LnestedTypes/OuterClass2$StaticNestedClass;->access$100(LnestedTypes/OuterClass2$StaticNestedClass;)I

    move-result v1

    return v1
.end method
