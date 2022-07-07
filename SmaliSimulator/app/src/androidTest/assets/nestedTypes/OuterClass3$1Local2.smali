.class LnestedTypes/OuterClass3$1Local2;
.super Ljava/lang/Object;
.source "OuterClass3.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = LnestedTypes/OuterClass3;->test2()I
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = "Local2"
.end annotation


# direct methods
.method constructor <init>()V
    .locals 0

    .line 18
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method testInner()I
    .locals 1

    .line 20
    invoke-static {}, LnestedTypes/OuterClass3;->access$000()I

    move-result v0

    add-int/lit8 v0, v0, 0xa

    return v0
.end method
