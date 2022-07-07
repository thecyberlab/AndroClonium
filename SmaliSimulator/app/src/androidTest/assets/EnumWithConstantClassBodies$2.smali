.class final enum LEnumWithConstantClassBodies$2;
.super LEnumWithConstantClassBodies;
.source "EnumWithConstantClassBodies.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = LEnumWithConstantClassBodies;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4008
    name = null
.end annotation


# direct methods
.method constructor <init>(Ljava/lang/String;I)V
    .locals 1

    .line 6
    const/4 v0, 0x0

    invoke-direct {p0, p1, p2, v0}, LEnumWithConstantClassBodies;-><init>(Ljava/lang/String;ILEnumWithConstantClassBodies$1;)V

    return-void
.end method


# virtual methods
.method public testMethod()D
    .locals 2

    .line 6
    const-wide v0, 0x3fe999999999999aL    # 0.8

    return-wide v0
.end method
