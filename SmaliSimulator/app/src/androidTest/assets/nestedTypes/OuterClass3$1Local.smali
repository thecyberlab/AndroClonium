.class LnestedTypes/OuterClass3$1Local;
.super Ljava/lang/Object;
.source "OuterClass3.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = LnestedTypes/OuterClass3;->test()I
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = "Local"
.end annotation


# instance fields
.field final synthetic this$0:LnestedTypes/OuterClass3;


# direct methods
.method constructor <init>(LnestedTypes/OuterClass3;)V
    .locals 0
    .param p1, "this$0"    # LnestedTypes/OuterClass3;
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x8010
        }
        names = {
            "this$0"
        }
    .end annotation

    .line 8
    iput-object p1, p0, LnestedTypes/OuterClass3$1Local;->this$0:LnestedTypes/OuterClass3;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method testInner()I
    .locals 2

    invoke-static {}, LnestedTypes/OuterClass3;->access$000()I

    move-result v0

    iget-object v1, p0, LnestedTypes/OuterClass3$1Local;->this$0:LnestedTypes/OuterClass3;

    invoke-static {v1}, LnestedTypes/OuterClass3;->access$100(LnestedTypes/OuterClass3;)I

    move-result v1

    add-int/2addr v0, v1

    return v0
.end method
