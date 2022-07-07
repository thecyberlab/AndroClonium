.class public LcyclicFields4/CircularReference3;
.super Ljava/lang/Object;
.source "CircularReference3.java"


# static fields
.field public static c4:LcyclicFields4/CircularReference4;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 4
    new-instance v0, LcyclicFields4/CircularReference4;

    invoke-direct {v0}, LcyclicFields4/CircularReference4;-><init>()V

    sput-object v0, LcyclicFields4/CircularReference3;->c4:LcyclicFields4/CircularReference4;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method
