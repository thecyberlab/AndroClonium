.class public final enum LEnumImplementingInterface;
.super Ljava/lang/Enum;
.source "EnumImplementingInterface.java"

# interfaces
.implements LInterfaceForEnumImplementingInterface;


# static fields
.field private static final synthetic $VALUES:[LEnumImplementingInterface;

.field public static final enum A:LEnumImplementingInterface;

.field public static final enum B:LEnumImplementingInterface;

.field public static final enum C:LEnumImplementingInterface;


# direct methods
.method static constructor <clinit>()V
    .locals 7

    new-instance v0, LEnumImplementingInterface;

    const-string v1, "A"

    const/4 v2, 0x0

    invoke-direct {v0, v1, v2}, LEnumImplementingInterface;-><init>(Ljava/lang/String;I)V

    sput-object v0, LEnumImplementingInterface;->A:LEnumImplementingInterface;

    new-instance v1, LEnumImplementingInterface;

    const-string v3, "B"

    const/4 v4, 0x1

    invoke-direct {v1, v3, v4}, LEnumImplementingInterface;-><init>(Ljava/lang/String;I)V

    sput-object v1, LEnumImplementingInterface;->B:LEnumImplementingInterface;

    new-instance v3, LEnumImplementingInterface;

    const-string v5, "C"

    const/4 v6, 0x2

    invoke-direct {v3, v5, v6}, LEnumImplementingInterface;-><init>(Ljava/lang/String;I)V

    sput-object v3, LEnumImplementingInterface;->C:LEnumImplementingInterface;

    const/4 v5, 0x3

    new-array v5, v5, [LEnumImplementingInterface;

    aput-object v0, v5, v2

    aput-object v1, v5, v4

    aput-object v3, v5, v6

    sput-object v5, LEnumImplementingInterface;->$VALUES:[LEnumImplementingInterface;

    return-void
.end method

.method private constructor <init>(Ljava/lang/String;I)V
    .locals 0

    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    return-void
.end method

.method public static valueOf(Ljava/lang/String;)LEnumImplementingInterface;
    .locals 1

    const-class v0, LEnumImplementingInterface;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object v0

    check-cast v0, LEnumImplementingInterface;

    return-object v0
.end method

.method public static values()[LEnumImplementingInterface;
    .locals 1

    sget-object v0, LEnumImplementingInterface;->$VALUES:[LEnumImplementingInterface;

    invoke-virtual {v0}, [LEnumImplementingInterface;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [LEnumImplementingInterface;

    return-object v0
.end method


# virtual methods
.method public t()Ljava/lang/String;
    .locals 1

    const-string v0, "this is LEnumImplementingInterface;->t()V"

    return-object v0
.end method
