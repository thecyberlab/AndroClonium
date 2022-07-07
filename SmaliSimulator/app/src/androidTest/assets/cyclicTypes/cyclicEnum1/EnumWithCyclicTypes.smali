.class public final enum LcyclicEnum1/EnumWithCyclicTypes;
.super Ljava/lang/Enum;
.source "EnumWithCyclicTypes.java"


# static fields
.field private static final synthetic $VALUES:[LcyclicEnum1/EnumWithCyclicTypes;

.field public static final enum State1:LcyclicEnum1/EnumWithCyclicTypes;

.field public static final enum State2:LcyclicEnum1/EnumWithCyclicTypes;

.field public static final enum State3:LcyclicEnum1/EnumWithCyclicTypes;


# direct methods
.method static constructor <clinit>()V
    .locals 7

    .line 4
    new-instance v0, LcyclicEnum1/EnumWithCyclicTypes;

    const-string v1, "State1"

    const/4 v2, 0x0

    invoke-direct {v0, v1, v2}, LcyclicEnum1/EnumWithCyclicTypes;-><init>(Ljava/lang/String;I)V

    sput-object v0, LcyclicEnum1/EnumWithCyclicTypes;->State1:LcyclicEnum1/EnumWithCyclicTypes;

    .line 5
    new-instance v1, LcyclicEnum1/EnumWithCyclicTypes;

    const-string v3, "State2"

    const/4 v4, 0x1

    invoke-direct {v1, v3, v4}, LcyclicEnum1/EnumWithCyclicTypes;-><init>(Ljava/lang/String;I)V

    sput-object v1, LcyclicEnum1/EnumWithCyclicTypes;->State2:LcyclicEnum1/EnumWithCyclicTypes;

    .line 6
    new-instance v3, LcyclicEnum1/EnumWithCyclicTypes;

    const-string v5, "State3"

    const/4 v6, 0x2

    invoke-direct {v3, v5, v6}, LcyclicEnum1/EnumWithCyclicTypes;-><init>(Ljava/lang/String;I)V

    sput-object v3, LcyclicEnum1/EnumWithCyclicTypes;->State3:LcyclicEnum1/EnumWithCyclicTypes;

    .line 3
    const/4 v5, 0x3

    new-array v5, v5, [LcyclicEnum1/EnumWithCyclicTypes;

    aput-object v0, v5, v2

    aput-object v1, v5, v4

    aput-object v3, v5, v6

    sput-object v5, LcyclicEnum1/EnumWithCyclicTypes;->$VALUES:[LcyclicEnum1/EnumWithCyclicTypes;

    return-void
.end method

.method private constructor <init>(Ljava/lang/String;I)V
    .locals 0

    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    return-void
.end method

.method public static valueOf(Ljava/lang/String;)LcyclicEnum1/EnumWithCyclicTypes;
    .locals 1

    .line 3
    const-class v0, LcyclicEnum1/EnumWithCyclicTypes;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object v0

    check-cast v0, LcyclicEnum1/EnumWithCyclicTypes;

    return-object v0
.end method

.method public static values()[LcyclicEnum1/EnumWithCyclicTypes;
    .locals 1

    .line 3
    sget-object v0, LcyclicEnum1/EnumWithCyclicTypes;->$VALUES:[LcyclicEnum1/EnumWithCyclicTypes;

    invoke-virtual {v0}, [LcyclicEnum1/EnumWithCyclicTypes;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [LcyclicEnum1/EnumWithCyclicTypes;

    return-object v0
.end method


# virtual methods
.method public test(LcyclicEnum1/EnumWithCyclicTypesCompanion;)V
    .locals 0

    return-void
.end method
