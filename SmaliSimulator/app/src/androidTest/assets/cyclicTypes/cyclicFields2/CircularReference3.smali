.class public LcyclicFields2/CircularReference3;
.super Ljava/lang/Object;
.source "CircularReference3.java"


# static fields
.field public static c1:LcyclicFields2/CircularReference1;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 4
    const/4 v0, 0x0

    sput-object v0, LcyclicFields2/CircularReference3;->c1:LcyclicFields2/CircularReference1;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method
