.class public final enum LcyclicEnum2/EnumWithCyclicConstructor;
.super Ljava/lang/Enum;
.source "EnumWithCyclicConstructor.java"


# static fields
.field private static final synthetic $VALUES:[LcyclicEnum2/EnumWithCyclicConstructor;

.field public static final enum State1:LcyclicEnum2/EnumWithCyclicConstructor;

.field public static final enum State2:LcyclicEnum2/EnumWithCyclicConstructor;

.field public static final enum State3:LcyclicEnum2/EnumWithCyclicConstructor;


# instance fields
.field public companions:[LcyclicEnum2/EnumWithCyclicConstructorCompanion;


# direct methods
.method static constructor <clinit>()V
    .locals 8

    new-instance v0, LcyclicEnum2/EnumWithCyclicConstructor;

    const/4 v1, 0x5

    new-array v1, v1, [LcyclicEnum2/EnumWithCyclicConstructorCompanion;

    const-string v2, "State1"

    const/4 v3, 0x0

    invoke-direct {v0, v2, v3, v1}, LcyclicEnum2/EnumWithCyclicConstructor;-><init>(Ljava/lang/String;I[LcyclicEnum2/EnumWithCyclicConstructorCompanion;)V

    sput-object v0, LcyclicEnum2/EnumWithCyclicConstructor;->State1:LcyclicEnum2/EnumWithCyclicConstructor;

    new-instance v1, LcyclicEnum2/EnumWithCyclicConstructor;

    const/16 v2, 0xa

    new-array v2, v2, [LcyclicEnum2/EnumWithCyclicConstructorCompanion;

    const-string v4, "State2"

    const/4 v5, 0x1

    invoke-direct {v1, v4, v5, v2}, LcyclicEnum2/EnumWithCyclicConstructor;-><init>(Ljava/lang/String;I[LcyclicEnum2/EnumWithCyclicConstructorCompanion;)V

    sput-object v1, LcyclicEnum2/EnumWithCyclicConstructor;->State2:LcyclicEnum2/EnumWithCyclicConstructor;

    new-instance v2, LcyclicEnum2/EnumWithCyclicConstructor;

    const/16 v4, 0xf

    new-array v4, v4, [LcyclicEnum2/EnumWithCyclicConstructorCompanion;

    const-string v6, "State3"

    const/4 v7, 0x2

    invoke-direct {v2, v6, v7, v4}, LcyclicEnum2/EnumWithCyclicConstructor;-><init>(Ljava/lang/String;I[LcyclicEnum2/EnumWithCyclicConstructorCompanion;)V

    sput-object v2, LcyclicEnum2/EnumWithCyclicConstructor;->State3:LcyclicEnum2/EnumWithCyclicConstructor;

    const/4 v4, 0x3

    new-array v4, v4, [LcyclicEnum2/EnumWithCyclicConstructor;

    aput-object v0, v4, v3

    aput-object v1, v4, v5

    aput-object v2, v4, v7

    sput-object v4, LcyclicEnum2/EnumWithCyclicConstructor;->$VALUES:[LcyclicEnum2/EnumWithCyclicConstructor;

    return-void
.end method

.method private constructor <init>(Ljava/lang/String;I[LcyclicEnum2/EnumWithCyclicConstructorCompanion;)V
    .locals 0

    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    iput-object p3, p0, LcyclicEnum2/EnumWithCyclicConstructor;->companions:[LcyclicEnum2/EnumWithCyclicConstructorCompanion;

    return-void
.end method

.method public static valueOf(Ljava/lang/String;)LcyclicEnum2/EnumWithCyclicConstructor;
    .locals 1

    const-class v0, LcyclicEnum2/EnumWithCyclicConstructor;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object v0

    check-cast v0, LcyclicEnum2/EnumWithCyclicConstructor;

    return-object v0
.end method

.method public static values()[LcyclicEnum2/EnumWithCyclicConstructor;
    .locals 1

    sget-object v0, LcyclicEnum2/EnumWithCyclicConstructor;->$VALUES:[LcyclicEnum2/EnumWithCyclicConstructor;

    invoke-virtual {v0}, [LcyclicEnum2/EnumWithCyclicConstructor;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [LcyclicEnum2/EnumWithCyclicConstructor;

    return-object v0
.end method
