.class public LnestedTypes/OuterClass$NestedClass;
.super Ljava/lang/Object;
.source "OuterClass.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = LnestedTypes/OuterClass;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1
    name = "NestedClass"
.end annotation


# instance fields
.field b:I

.field final synthetic this$0:LnestedTypes/OuterClass;


# direct methods
.method public constructor <init>(LnestedTypes/OuterClass;)V
    .locals 1
    .param p1, "this$0"    # LnestedTypes/OuterClass;
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x8010
        }
        names = {
            "this$0"
        }
    .end annotation

    .line 5
    iput-object p1, p0, LnestedTypes/OuterClass$NestedClass;->this$0:LnestedTypes/OuterClass;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 6
    const/16 v0, 0xb

    iput v0, p0, LnestedTypes/OuterClass$NestedClass;->b:I

    return-void
.end method
