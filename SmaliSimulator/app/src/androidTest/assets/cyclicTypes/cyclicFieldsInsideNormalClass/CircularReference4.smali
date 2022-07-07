.class public LcyclicFieldsInsideNormalClass/CircularReference4;
.super Ljava/lang/Object;
.source "CircularReference4.java"


# static fields
.field public static c1:LcyclicFieldsInsideNormalClass/CircularReference1;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 4
    new-instance v0, LcyclicFieldsInsideNormalClass/CircularReference1;

    invoke-direct {v0}, LcyclicFieldsInsideNormalClass/CircularReference1;-><init>()V

    sput-object v0, LcyclicFieldsInsideNormalClass/CircularReference4;->c1:LcyclicFieldsInsideNormalClass/CircularReference1;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method
