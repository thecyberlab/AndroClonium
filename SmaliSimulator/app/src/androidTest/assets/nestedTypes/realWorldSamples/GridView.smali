.class public LnestedTypes/realWorldSamples/GridView;
.super Landroid/view/View;
.source "GridView.java"

# interfaces
.implements Landroid/view/View$OnTouchListener;


# instance fields
.field public mCages:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList<",
            "LnestedTypes/realWorldSamples/GridCage;",
            ">;"
        }
    .end annotation
.end field

.field public mSelectedCell:LnestedTypes/realWorldSamples/GridCell;

.field private mSolvedListener:LnestedTypes/realWorldSamples/GridView$OnSolvedListener;

.field public mTouchedListener:LnestedTypes/realWorldSamples/GridView$OnGridTouchListener;


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 0
    .line 22
    invoke-direct {p0, p1}, Landroid/view/View;-><init>(Landroid/content/Context;)V

    .line 23
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 0

    .line 26
    invoke-direct {p0, p1, p2}, Landroid/view/View;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    .line 27
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V
    .locals 0

    .line 30
    invoke-direct {p0, p1, p2, p3}, Landroid/view/View;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    .line 31
    return-void
.end method

.method private CoordToCell(FF)LnestedTypes/realWorldSamples/GridCell;
    .locals 1

    .line 45
    const/4 v0, 0x0

    return-object v0
.end method


# virtual methods
.method public cheatedHighlighted()Ljava/util/ArrayList;
    .locals 1

    .line 78
    const/4 v0, 0x0

    return-object v0
.end method

.method public getCellAt(II)LnestedTypes/realWorldSamples/GridCell;
    .locals 1
    .line 40
    const/4 v0, 0x0

    return-object v0
.end method

.method public getNumValueInCol(LnestedTypes/realWorldSamples/GridCell;)I
    .locals 1

    .line 55
    const/4 v0, 0x0

    return v0
.end method

.method public getNumValueInRow(LnestedTypes/realWorldSamples/GridCell;)I
    .locals 1

    .line 50
    const/4 v0, 0x0

    return v0
.end method

.method public getPossiblesInRowCol(LnestedTypes/realWorldSamples/GridCell;)Ljava/util/ArrayList;
    .locals 1
    .line 60
    const/4 v0, 0x0

    return-object v0
.end method

.method public getSinglePossibles()Ljava/util/ArrayList;
    .locals 1
    .line 65
    const/4 v0, 0x0

    return-object v0
.end method

.method public getvalidCages(LnestedTypes/realWorldSamples/GridCell;)Ljava/util/ArrayList;
    .locals 1

    .line 36
    const/4 v0, 0x0

    return-object v0
.end method

.method public invalidsHighlighted()Ljava/util/ArrayList;
    .locals 1
    .line 72
    const/4 v0, 0x0

    return-object v0
.end method

.method public onTouch(Landroid/view/View;Landroid/view/MotionEvent;)Z
    .locals 1

    .line 87
    const/4 v0, 0x0

    return v0
.end method

.method public setOnGridTouchListener(LnestedTypes/realWorldSamples/GridView$OnGridTouchListener;)V
    .locals 0
    .line 95
    return-void
.end method

.method public setSolvedHandler(LnestedTypes/realWorldSamples/GridView$OnSolvedListener;)V
    .locals 0
    .line 82
    iput-object p1, p0, LnestedTypes/realWorldSamples/GridView;->mSolvedListener:LnestedTypes/realWorldSamples/GridView$OnSolvedListener;
    .line 83
    return-void
.end method
