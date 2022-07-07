.class public LTestSmaliClassParent;
.super Ljava/lang/Object;
.source "TestSmaliClassParent.java"

# interfaces
.implements Ljava/lang/Comparable;


# static fields
.field protected static i2:I

.field protected static ip2:I

.field protected static sp2:Ljava/lang/String;


# instance fields
.field protected ip1:I

.field protected sp1:Ljava/lang/String;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 5
    const/4 v0, 0x4

    sput v0, LTestSmaliClassParent;->i2:I

    .line 6
    sput v0, LTestSmaliClassParent;->ip2:I

    .line 8
    const-string v0, "Hello from parent"

    sput-object v0, LTestSmaliClassParent;->sp2:Ljava/lang/String;

    return-void
    .end method

.method public constructor <init>()V
    .locals 0

    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


.method public compareTo(Ljava/lang/Object;)I
    .locals 1

    const/4 v0, 0x0

    return v0

.end method