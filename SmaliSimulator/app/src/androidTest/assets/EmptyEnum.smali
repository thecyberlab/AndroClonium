.class public final enum LEmptyEnum;
.super Ljava/lang/Enum;
.source "EmptyEnum.java"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "LEmptyEnum;",
        ">;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[LEmptyEnum;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 3
    const/4 v0, 0x0

    new-array v0, v0, [LEmptyEnum;

    sput-object v0, LEmptyEnum;->$VALUES:[LEmptyEnum;

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

.method public static valueOf(Ljava/lang/String;)LEmptyEnum;
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
    const-class v0, LEmptyEnum;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object v0

    check-cast v0, LEmptyEnum;

    return-object v0
.end method

.method public static values()[LEmptyEnum;
    .locals 1

    .line 3
    sget-object v0, LEmptyEnum;->$VALUES:[LEmptyEnum;

    invoke-virtual {v0}, [LEmptyEnum;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [LEmptyEnum;

    return-object v0
.end method
