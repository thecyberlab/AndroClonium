.class public LSimpleClass;
.super Ljava/lang/Object;
.source "SimpleClass.java"


# static fields
.field public static staticFieldTest:I


# instance fields
.field public instanceFieldTest:I


# direct methods
.method constructor <init>()V
    .locals 0

    .line 7
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method constructor <init>(I)V
    .locals 0
    .param p1, "i"    # I

    .line 9
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 10
    iput p1, p0, LSimpleClass;->instanceFieldTest:I

    .line 11
    return-void
.end method

.method public static staticMethodTest()V
    .locals 1

    .line 14
    sget v0, LSimpleClass;->staticFieldTest:I

    add-int/lit8 v0, v0, 0x1

    sput v0, LSimpleClass;->staticFieldTest:I

    .line 15
    return-void
.end method


# virtual methods
.method public instanceMethodTest()V
    .locals 1

    .line 18
    iget v0, p0, LSimpleClass;->instanceFieldTest:I

    add-int/lit8 v0, v0, 0x1

    iput v0, p0, LSimpleClass;->instanceFieldTest:I

    .line 19
    return-void
.end method
