.class public final enum LSimpleEnum;
.super Ljava/lang/Enum;
.source "SimpleEnum.java"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "LSimpleEnum;",
        ">;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[LSimpleEnum;

.field public static final enum STATE1:LSimpleEnum;

.field public static final enum STATE2:LSimpleEnum;

.field public static final enum STATE3:LSimpleEnum;


# direct methods
.method static constructor <clinit>()V
    .locals 6

    .line 4
    new-instance v0, LSimpleEnum;

    const/4 v1, 0x0

    const-string v2, "STATE1"

    invoke-direct {v0, v2, v1}, LSimpleEnum;-><init>(Ljava/lang/String;I)V

    sput-object v0, LSimpleEnum;->STATE1:LSimpleEnum;

    .line 5
    new-instance v0, LSimpleEnum;

    const/4 v2, 0x1

    const-string v3, "STATE2"

    invoke-direct {v0, v3, v2}, LSimpleEnum;-><init>(Ljava/lang/String;I)V

    sput-object v0, LSimpleEnum;->STATE2:LSimpleEnum;

    .line 6
    new-instance v0, LSimpleEnum;

    const/4 v3, 0x2

    const-string v4, "STATE3"

    invoke-direct {v0, v4, v3}, LSimpleEnum;-><init>(Ljava/lang/String;I)V

    sput-object v0, LSimpleEnum;->STATE3:LSimpleEnum;

    .line 3
    const/4 v4, 0x3

    new-array v4, v4, [LSimpleEnum;

    sget-object v5, LSimpleEnum;->STATE1:LSimpleEnum;

    aput-object v5, v4, v1

    sget-object v1, LSimpleEnum;->STATE2:LSimpleEnum;

    aput-object v1, v4, v2

    aput-object v0, v4, v3

    sput-object v4, LSimpleEnum;->$VALUES:[LSimpleEnum;

    return-void
.end method

.method private constructor <init>(Ljava/lang/String;I)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 3
    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    return-void
.end method

.method public static valueOf(Ljava/lang/String;)LSimpleEnum;
    .locals 1
    .param p0, "name"    # Ljava/lang/String;

    .line 3
    const-class v0, LSimpleEnum;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object v0

    check-cast v0, LSimpleEnum;

    return-object v0
.end method

.method public static values()[LSimpleEnum;
    .locals 1

    .line 3
    sget-object v0, LSimpleEnum;->$VALUES:[LSimpleEnum;

    invoke-virtual {v0}, [LSimpleEnum;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [LSimpleEnum;

    return-object v0
.end method
