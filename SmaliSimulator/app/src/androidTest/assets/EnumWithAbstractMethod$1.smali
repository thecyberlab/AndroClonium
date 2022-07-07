.class final enum LEnumWithAbstractMethod$1;
.super LEnumWithAbstractMethod;
.source "EnumWithAbstractMethod.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = LEnumWithAbstractMethod;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4008
    name = null
.end annotation


# direct methods
.method constructor <init>(Ljava/lang/String;I)V
    .locals 1

    const/4 v0, 0x0

    invoke-direct {p0, p1, p2, v0}, LEnumWithAbstractMethod;-><init>(Ljava/lang/String;ILEnumWithAbstractMethod$1;)V

    return-void
.end method


# virtual methods
.method public test()Ljava/lang/String;
    .locals 1

    const-string v0, "A"

    return-object v0
.end method
