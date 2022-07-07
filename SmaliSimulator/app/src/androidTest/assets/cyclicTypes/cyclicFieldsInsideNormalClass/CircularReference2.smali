.class public LcyclicFieldsInsideNormalClass/CircularReference2;
.super Ljava/lang/Object;
.source "CircularReference2.java"


# static fields
.field public static c3:LcyclicFieldsInsideNormalClass/CircularReference3;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 4
    new-instance v0, LcyclicFieldsInsideNormalClass/CircularReference3;

    invoke-direct {v0}, LcyclicFieldsInsideNormalClass/CircularReference3;-><init>()V

    sput-object v0, LcyclicFieldsInsideNormalClass/CircularReference2;->c3:LcyclicFieldsInsideNormalClass/CircularReference3;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method
