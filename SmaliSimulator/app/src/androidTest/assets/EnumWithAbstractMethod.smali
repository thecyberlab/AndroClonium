.class public abstract enum LEnumWithAbstractMethod;
.super Ljava/lang/Enum;
.source "EnumWithAbstractMethod.java"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "LEnumWithAbstractMethod;",
        ">;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[LEnumWithAbstractMethod;

.field public static final enum A:LEnumWithAbstractMethod;

.field public static final enum B:LEnumWithAbstractMethod;

.field public static final enum C:LEnumWithAbstractMethod;


# direct methods
.method static constructor <clinit>()V
    .locals 7

    new-instance v0, LEnumWithAbstractMethod$1;

    const-string v1, "A"

    const/4 v2, 0x0

    invoke-direct {v0, v1, v2}, LEnumWithAbstractMethod$1;-><init>(Ljava/lang/String;I)V

    sput-object v0, LEnumWithAbstractMethod;->A:LEnumWithAbstractMethod;

    new-instance v1, LEnumWithAbstractMethod$2;

    const-string v3, "B"

    const/4 v4, 0x1

    invoke-direct {v1, v3, v4}, LEnumWithAbstractMethod$2;-><init>(Ljava/lang/String;I)V

    sput-object v1, LEnumWithAbstractMethod;->B:LEnumWithAbstractMethod;

    new-instance v3, LEnumWithAbstractMethod$3;

    const-string v5, "C"

    const/4 v6, 0x2

    invoke-direct {v3, v5, v6}, LEnumWithAbstractMethod$3;-><init>(Ljava/lang/String;I)V

    sput-object v3, LEnumWithAbstractMethod;->C:LEnumWithAbstractMethod;

    const/4 v5, 0x3

    new-array v5, v5, [LEnumWithAbstractMethod;

    aput-object v0, v5, v2

    aput-object v1, v5, v4

    aput-object v3, v5, v6

    sput-object v5, LEnumWithAbstractMethod;->$VALUES:[LEnumWithAbstractMethod;

    return-void
.end method

.method private constructor <init>(Ljava/lang/String;I)V
    .locals 0

    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    return-void
.end method

.method synthetic constructor <init>(Ljava/lang/String;ILEnumWithAbstractMethod$1;)V
    .locals 0

    invoke-direct {p0, p1, p2}, LEnumWithAbstractMethod;-><init>(Ljava/lang/String;I)V

    return-void
.end method

.method public static valueOf(Ljava/lang/String;)LEnumWithAbstractMethod;
    .locals 1

    const-class v0, LEnumWithAbstractMethod;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object v0

    check-cast v0, LEnumWithAbstractMethod;

    return-object v0
.end method

.method public static values()[LEnumWithAbstractMethod;
    .locals 1

    sget-object v0, LEnumWithAbstractMethod;->$VALUES:[LEnumWithAbstractMethod;

    invoke-virtual {v0}, [LEnumWithAbstractMethod;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [LEnumWithAbstractMethod;

    return-object v0
.end method


# virtual methods
.method public abstract test()Ljava/lang/String;
.end method
