.class public LnestedTypes/realWorldSamples/GridCage;
.super Ljava/lang/Object;
.source "GridCage.java"


# instance fields
.field public mCells:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList<",
            "LnestedTypes/realWorldSamples/GridCell;",
            ">;"
        }
    .end annotation
.end field

.field public mContext:LnestedTypes/realWorldSamples/GridView;


# direct methods
.method public constructor <init>(LnestedTypes/realWorldSamples/GridView;I)V
    .locals 1
    .param p1, "context"    # LnestedTypes/realWorldSamples/GridView;
    .param p2, "type"    # I
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0,
            0x0
        }
        names = {
            "context",
            "type"
        }
    .end annotation

    .line 9
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 10
    iput-object p1, p0, LnestedTypes/realWorldSamples/GridCage;->mContext:LnestedTypes/realWorldSamples/GridView;

    .line 11
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, LnestedTypes/realWorldSamples/GridCage;->mCells:Ljava/util/ArrayList;

    .line 12
    return-void
.end method
