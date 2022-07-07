.class public LcyclicFields1/CircularReference1;
.super Ljava/lang/Object;
.source "CircularReference1.java"


# static fields
.field public static c2:LcyclicFields1/CircularReference2;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 4
    new-instance v0, LcyclicFields1/CircularReference2;

    invoke-direct {v0}, LcyclicFields1/CircularReference2;-><init>()V

    sput-object v0, LcyclicFields1/CircularReference1;->c2:LcyclicFields1/CircularReference2;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method
