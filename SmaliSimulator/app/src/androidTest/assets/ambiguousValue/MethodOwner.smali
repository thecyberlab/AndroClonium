.class public LMethodOwner;
.super Ljava/lang/Object;

.field public testIntValue:I

.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public constructor <init>(I)V
    .locals 0

    .line 6
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput p1, p0, LMethodOwner;->testIntValue:I

    return-void
.end method

.method public static staticMethod1(I)I
    .locals 1

    const/4 v0, 0x5

    add-int/2addr v0, p0

    return v0
.end method


.method public instanceMethod(I)I
    .locals 1

    const/4 v0, 0x5

    add-int/2addr v0, p1

    return v0
.end method

.method public wrapperOnPrivateInstanceMethod(I)I
    .locals 1

    invoke-direct {p0, p1}, LMethodOwner;->privateInstanceMethod(I)I

    move-result-object v0

    return v0
.end method

.method private privateInstanceMethod(I)I
    .locals 1

    const/4 v0, 0x5

    add-int/2addr v0, p1

    return v0
.end method

.method public static isIntZero(I)Z
    .locals 1

    const/4 v0, 0x0

    if-eqz p0, :end

    const/4 v0, 0x1

    :end
    return v0
.end method

.method public isIntOne(I)Z
    .locals 1

    const/4 v0, 0x1

    if-eq p1, v0, :end

    const/4 v0, 0x0

    :end
    return v0
.end method
