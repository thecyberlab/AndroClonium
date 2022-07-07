.class enum LEnumWithConstantClassBodies;
.super Ljava/lang/Enum;
.source "EnumWithConstantClassBodies.java"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "LEnumWithConstantClassBodies;",
        ">;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[LEnumWithConstantClassBodies;

.field public static final enum STATE1:LEnumWithConstantClassBodies;

.field public static final enum STATE2:LEnumWithConstantClassBodies;

.field public static final enum STATE3:LEnumWithConstantClassBodies;


# direct methods
.method static constructor <clinit>()V
    .locals 6

    .line 4
    new-instance v0, LEnumWithConstantClassBodies;

    const/4 v1, 0x0

    const-string v2, "STATE1"

    invoke-direct {v0, v2, v1}, LEnumWithConstantClassBodies;-><init>(Ljava/lang/String;I)V

    sput-object v0, LEnumWithConstantClassBodies;->STATE1:LEnumWithConstantClassBodies;

    .line 5
    new-instance v0, LEnumWithConstantClassBodies$1;

    const/4 v2, 0x1

    const-string v3, "STATE2"

    invoke-direct {v0, v3, v2}, LEnumWithConstantClassBodies$1;-><init>(Ljava/lang/String;I)V

    sput-object v0, LEnumWithConstantClassBodies;->STATE2:LEnumWithConstantClassBodies;

    .line 6
    new-instance v0, LEnumWithConstantClassBodies$2;

    const/4 v3, 0x2

    const-string v4, "STATE3"

    invoke-direct {v0, v4, v3}, LEnumWithConstantClassBodies$2;-><init>(Ljava/lang/String;I)V

    sput-object v0, LEnumWithConstantClassBodies;->STATE3:LEnumWithConstantClassBodies;

    .line 3
    const/4 v4, 0x3

    new-array v4, v4, [LEnumWithConstantClassBodies;

    sget-object v5, LEnumWithConstantClassBodies;->STATE1:LEnumWithConstantClassBodies;

    aput-object v5, v4, v1

    sget-object v1, LEnumWithConstantClassBodies;->STATE2:LEnumWithConstantClassBodies;

    aput-object v1, v4, v2

    aput-object v0, v4, v3

    sput-object v4, LEnumWithConstantClassBodies;->$VALUES:[LEnumWithConstantClassBodies;

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

.method synthetic constructor <init>(Ljava/lang/String;ILEnumWithConstantClassBodies$1;)V
    .locals 0
    .param p1, "x0"    # Ljava/lang/String;
    .param p2, "x1"    # I
    .param p3, "x2"    # LEnumWithConstantClassBodies$1;

    .line 3
    invoke-direct {p0, p1, p2}, LEnumWithConstantClassBodies;-><init>(Ljava/lang/String;I)V

    return-void
.end method

.method public static valueOf(Ljava/lang/String;)LEnumWithConstantClassBodies;
    .locals 1
    .param p0, "name"    # Ljava/lang/String;

    .line 3
    const-class v0, LEnumWithConstantClassBodies;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object v0

    check-cast v0, LEnumWithConstantClassBodies;

    return-object v0
.end method

.method public static values()[LEnumWithConstantClassBodies;
    .locals 1

    .line 3
    sget-object v0, LEnumWithConstantClassBodies;->$VALUES:[LEnumWithConstantClassBodies;

    invoke-virtual {v0}, [LEnumWithConstantClassBodies;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [LEnumWithConstantClassBodies;

    return-object v0
.end method


# virtual methods
.method public testMethod()D
    .locals 2

    .line 8
    const-wide/high16 v0, 0x3ff0000000000000L    # 1.0

    return-wide v0
.end method
