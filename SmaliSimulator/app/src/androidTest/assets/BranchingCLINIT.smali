.class public LBranchingCLINIT;
.super Ljava/lang/Object;
.source "BranchingCLINIT.java"


# static fields
.field public static final testValue:Z


# direct methods
.method static constructor <clinit>()V
    .locals 2

    new-instance v0, Ljava/io/File;

    const-string v1, "test"

    invoke-direct {v0, v1}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result v1

    if-eqz v1, :cond_0

    const/4 v1, 0x1

    sput-boolean v1, LBranchingCLINIT;->testValue:Z

    goto :goto_0

    :cond_0
    const/4 v1, 0x0

    sput-boolean v1, LBranchingCLINIT;->testValue:Z

    :goto_0
    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 5
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method
