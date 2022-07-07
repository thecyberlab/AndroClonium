.class public LnestedTypes/OuterClass3;
.super Ljava/lang/Object;
.source "OuterClass3.java"


# static fields
.field private static a:I


# instance fields
.field private b:I


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 4
    const/16 v0, 0xa

    sput v0, LnestedTypes/OuterClass3;->a:I

    return-void
.end method

.method public constructor <init>()V
    .locals 1

    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 5
    const/16 v0, 0x14

    iput v0, p0, LnestedTypes/OuterClass3;->b:I

    return-void
.end method

.method static synthetic access$000()I
    .locals 1

    .line 3
    sget v0, LnestedTypes/OuterClass3;->a:I

    return v0
.end method

.method static synthetic access$100(LnestedTypes/OuterClass3;)I
    .locals 1
    .param p0, "x0"    # LnestedTypes/OuterClass3;

    .line 3
    iget v0, p0, LnestedTypes/OuterClass3;->b:I

    return v0
.end method

.method static test2()I
    .locals 2

    .line 23
    new-instance v0, LnestedTypes/OuterClass3$1Local2;

    invoke-direct {v0}, LnestedTypes/OuterClass3$1Local2;-><init>()V

    invoke-virtual {v0}, LnestedTypes/OuterClass3$1Local2;->testInner()I

    move-result v1

    return v1
.end method


# virtual methods
.method test()I
    .locals 2

    new-instance v0, LnestedTypes/OuterClass3$1Local;

    invoke-direct {v0, p0}, LnestedTypes/OuterClass3$1Local;-><init>(LnestedTypes/OuterClass3;)V

    invoke-virtual {v0}, LnestedTypes/OuterClass3$1Local;->testInner()I

    move-result v1

    return v1
.end method
