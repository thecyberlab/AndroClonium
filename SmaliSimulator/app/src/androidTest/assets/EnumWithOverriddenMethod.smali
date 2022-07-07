.class public enum LEnumWithOverriddenMethod;
.super Ljava/lang/Enum;
.source "EnumWithOverriddenMethod.java"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "LEnumWithOverriddenMethod;",
        ">;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[LEnumWithOverriddenMethod;

.field public static final enum A:LEnumWithOverriddenMethod;

.field public static final enum B:LEnumWithOverriddenMethod;

.field public static final enum C:LEnumWithOverriddenMethod;


# direct methods
.method static constructor <clinit>()V
    .locals 7

    .line 4
    new-instance v0, LEnumWithOverriddenMethod;

    const-string v1, "A"

    const/4 v2, 0x0

    invoke-direct {v0, v1, v2}, LEnumWithOverriddenMethod;-><init>(Ljava/lang/String;I)V

    sput-object v0, LEnumWithOverriddenMethod;->A:LEnumWithOverriddenMethod;

    .line 5
    new-instance v1, LEnumWithOverriddenMethod$1;

    const-string v3, "B"

    const/4 v4, 0x1

    invoke-direct {v1, v3, v4}, LEnumWithOverriddenMethod$1;-><init>(Ljava/lang/String;I)V

    sput-object v1, LEnumWithOverriddenMethod;->B:LEnumWithOverriddenMethod;

    .line 11
    new-instance v3, LEnumWithOverriddenMethod;

    const-string v5, "C"

    const/4 v6, 0x2

    invoke-direct {v3, v5, v6}, LEnumWithOverriddenMethod;-><init>(Ljava/lang/String;I)V

    sput-object v3, LEnumWithOverriddenMethod;->C:LEnumWithOverriddenMethod;

    .line 3
    const/4 v5, 0x3

    new-array v5, v5, [LEnumWithOverriddenMethod;

    aput-object v0, v5, v2

    aput-object v1, v5, v4

    aput-object v3, v5, v6

    sput-object v5, LEnumWithOverriddenMethod;->$VALUES:[LEnumWithOverriddenMethod;

    return-void
.end method

.method private constructor <init>(Ljava/lang/String;I)V
    .locals 0
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x1000,
            0x1000
        }
        names = {
            "$enum$name",
            "$enum$ordinal"
        }
    .end annotation

    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 3
    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    return-void
.end method

.method synthetic constructor <init>(Ljava/lang/String;ILEnumWithOverriddenMethod$1;)V
    .locals 0
    .param p1, "x0"    # Ljava/lang/String;
    .param p2, "x1"    # I
    .param p3, "x2"    # LEnumWithOverriddenMethod$1;

    .line 3
    invoke-direct {p0, p1, p2}, LEnumWithOverriddenMethod;-><init>(Ljava/lang/String;I)V

    return-void
.end method

.method public static valueOf(Ljava/lang/String;)LEnumWithOverriddenMethod;
    .locals 1
    .param p0, "name"    # Ljava/lang/String;
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x8000
        }
        names = {
            "name"
        }
    .end annotation

    .line 3
    const-class v0, LEnumWithOverriddenMethod;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object v0

    check-cast v0, LEnumWithOverriddenMethod;

    return-object v0
.end method

.method public static values()[LEnumWithOverriddenMethod;
    .locals 1

    .line 3
    sget-object v0, LEnumWithOverriddenMethod;->$VALUES:[LEnumWithOverriddenMethod;

    invoke-virtual {v0}, [LEnumWithOverriddenMethod;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [LEnumWithOverriddenMethod;

    return-object v0
.end method


# virtual methods
.method public test()Ljava/lang/String;
    .locals 1

    .line 14
    const-string v0, "default"

    return-object v0
.end method
